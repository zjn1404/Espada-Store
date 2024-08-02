package com.nqt.spring_boot_espada_store.service.authentication;

import com.nqt.spring_boot_espada_store.dto.request.security.AuthenticationRequest;
import com.nqt.spring_boot_espada_store.dto.request.security.IntrospectRequest;
import com.nqt.spring_boot_espada_store.dto.request.security.LogoutRequest;
import com.nqt.spring_boot_espada_store.dto.request.security.RefreshTokenRequest;
import com.nqt.spring_boot_espada_store.dto.response.AuthenticationResponse;
import com.nqt.spring_boot_espada_store.dto.response.IntrospectResponse;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

}
