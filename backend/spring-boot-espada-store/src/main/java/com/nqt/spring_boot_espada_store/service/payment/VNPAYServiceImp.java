package com.nqt.spring_boot_espada_store.service.payment;

import com.nqt.spring_boot_espada_store.configuration.payment.VNPAYConfig;
import com.nqt.spring_boot_espada_store.dto.response.VNPAYResponse;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import com.nqt.spring_boot_espada_store.utils.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPAYServiceImp {

    VNPAYConfig vnpayConfig;

    UserRepository userRepository;

    public VNPAYResponse createVNPAYPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        User user = getUser();

        long amount = Integer.parseInt(req.getParameter("amount"))* 100L;
        String bankCode = req.getParameter("bankCode");
        String vnp_IpAddr = VNPayUtils.getIpAddress(req);
        String vnp_TxnRef = (user.getId() + "-" + (new Date()).toString().replaceAll(" ", "").trim());

        Map<String, String> vnp_Params = vnpayConfig.getVNPAYConfigs();
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        String queryUrl = VNPayUtils.buildUrl(vnp_Params, vnpayConfig.getSecretKey());

        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPAYResponse.builder()
                .vnpCode("00")
                .vnpMessage("success")
                .paymentUrl(paymentUrl).build();
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(auth.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

}
