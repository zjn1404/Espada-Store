package com.nqt.spring_boot_espada_store.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import com.nqt.spring_boot_espada_store.dto.request.LogoutRequest;
import com.nqt.spring_boot_espada_store.dto.request.RefreshTokenRequest;
import com.nqt.spring_boot_espada_store.entity.InvalidatedToken;
import com.nqt.spring_boot_espada_store.repository.InvalidatedTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nqt.spring_boot_espada_store.dto.request.AuthenticationRequest;
import com.nqt.spring_boot_espada_store.dto.request.IntrospectRequest;
import com.nqt.spring_boot_espada_store.dto.response.AuthenticationResponse;
import com.nqt.spring_boot_espada_store.dto.response.IntrospectResponse;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.refreshSignerKey}")
    String REFRESH_SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request) {

        String token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (Exception ex) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return buildAuthenticationResponse(user);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);

            User user = userRepository.findByUsername(signedJWT.getJWTClaimsSet().getSubject())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            String id = signedJWT.getJWTClaimsSet().getJWTID();
            String acId = signedJWT.getJWTClaimsSet().getClaim("acId").toString();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            invalidatedTokenRepository.save(
                    InvalidatedToken.builder()
                            .acId(acId)
                            .rfId(id)
                            .expiryTime(expiration)
                            .build()
            );

            return buildAuthenticationResponse(user);

        } catch (Exception ex) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    private AuthenticationResponse buildAuthenticationResponse(User user) {
        String actId = UUID.randomUUID().toString();
        String rftId = UUID.randomUUID().toString();
        String accessToken = generateToken(user, VALID_DURATION, actId, rftId, SIGNER_KEY);
        String refreshToken = generateToken(user, REFRESHABLE_DURATION, rftId, actId, REFRESH_SIGNER_KEY);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isSuccess(true).build();
    }

    public void logout(LogoutRequest request) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());

            String acId = signedJWT.getJWTClaimsSet().getJWTID();
            String rfId = signedJWT.getJWTClaimsSet().getClaim("rfId").toString();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            expiration = new Date(expiration.getTime() + (REFRESHABLE_DURATION - VALID_DURATION)*1000);

            invalidatedTokenRepository.save(
                    InvalidatedToken.builder()
                            .acId(acId)
                            .rfId(rfId)
                            .expiryTime(expiration)
                            .build()
            );

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {

        JWSVerifier verifier;

        if (isRefresh) {
            verifier = new MACVerifier(REFRESH_SIGNER_KEY);
        } else {
            verifier = new MACVerifier(SIGNER_KEY);
        }

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        String id = signedJWT.getJWTClaimsSet().getJWTID();
        boolean verified = signedJWT.verify(verifier);

        if (!verified || expiration.before(new Date())) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if (invalidatedTokenRepository.existsById(id) || invalidatedTokenRepository.existsByRfId(id)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return signedJWT;
    }

    private String generateToken(User user, long duration, String id, String otherId, String signerKey) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = null;

        if (signerKey.equals(SIGNER_KEY)) {
            claimsSet = buildAccessTokenClaims(user, duration, id, otherId);
        } else {
            claimsSet = buildRefreshTokenClaims(user, duration, id, otherId);
        }

        Payload payload = claimsSet.toPayload();

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private JWTClaimsSet buildAccessTokenClaims(User user, long duration, String id, String otherId) {
        return  new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .jwtID(id)
                .issuer("nqt.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(duration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("rfId", otherId)
                .claim("scope", buildScope(user))
                .build();
    }

    private JWTClaimsSet buildRefreshTokenClaims(User user, long duration, String id, String otherId) {
        return new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .jwtID(id)
                .issuer("nqt.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(duration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("acId", otherId)
                .claim("scope", buildScope(user))
                .build();
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        user.getRoles().forEach(role -> {
            joiner.add("ROLE_" + role.getName());
            if (!CollectionUtils.isEmpty(role.getPermissions()))
                role.getPermissions().forEach(permission -> joiner.add(permission.getName()));
        });

        return joiner.toString();
    }
}
