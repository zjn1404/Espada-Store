package com.nqt.spring_boot_espada_store.configuration.payment;

import com.nqt.spring_boot_espada_store.utils.VNPayUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPAYConfig {

    @Value("${payment.vnpay.payment-url}")
    String vnp_PayUrl;

    @Value("${payment.vnpay.return-url}")
    String vnp_ReturnUrl;

    @Value("${payment.vnpay.tmn-code}")
    String vnp_TmnCode;

    @Value("${payment.vnpay.secret-key}")
    String secretKey;

    @Value("${payment.vnpay.version}")
    String vnp_Version;

    @Value("${payment.vnpay.vnp_command}")
    String vnp_Command;

    @Value("${payment.vnpay.orderType}")
    String orderType;

    public Map<String, String> getVNPAYConfigs() {
        Map<String, String> vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_OrderType", orderType);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnp_Params;
    }
}
