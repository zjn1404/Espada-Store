package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.order.OrderUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.VNPAYResponse;
import com.nqt.spring_boot_espada_store.service.order.OrderService;
import com.nqt.spring_boot_espada_store.service.payment.VNPAYServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/payment/vnpay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPAYController {

    VNPAYServiceImp vnpayServiceImp;
    OrderService orderService;

    @NonFinal
    @Value("${payment.vnpay.success-code}")
    String VNPAY_SUCCESS_CODE;

    String CLIENT_ORDER_PAGE = "http://localhost:3000/my-order";

    @GetMapping("/create-payment")
    public ApiResponse<?> createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        VNPAYResponse vnpayResponse = vnpayServiceImp.createVNPAYPayment(req);

        return new ApiResponse<>(vnpayResponse);
    }

    @GetMapping("/vnpay-return")
    public void vnpayReturn(@RequestParam("vnp_ResponseCode") String responseCode, @RequestParam("vnp_TxnRef") String orderId,
                            HttpServletResponse response) throws IOException {
        if (responseCode.equals(VNPAY_SUCCESS_CODE)) {
            orderService.updateOrder(orderId, OrderUpdateRequest.builder()
                    .paymentState(true)
                    .build());
            response.sendRedirect(CLIENT_ORDER_PAGE);
        }
    }

}
