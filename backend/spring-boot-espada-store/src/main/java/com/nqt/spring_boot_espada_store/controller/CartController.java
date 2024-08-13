package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.service.cart.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    @NonFinal
    @Value("${success-request-code}")
    int SUCCESS_REQUEST_CODE;

    CartService cartService;

    @GetMapping("/{productId}/{size}/{quantity}")
    public ApiResponse<CartResponse> addToCart(@PathVariable(name = "productId") String productId,
                                               @PathVariable(name = "quantity") int quantity,
                                               @PathVariable(name = "size") String size) {
        CartResponse cartResponse = cartService.addItemToCart(productId, quantity,  size);

        return ApiResponse.<CartResponse>builder()
                .code(SUCCESS_REQUEST_CODE)
                .result(cartResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<CartResponse>> getAllCarts() {
        List<CartResponse> cartResponses = cartService.getMyCart();

        return ApiResponse.<List<CartResponse>>builder()
                .code(SUCCESS_REQUEST_CODE)
                .result(cartResponses)
                .build();
    }

    @PutMapping("/{productId}/{size}/{quantity}")
    public ApiResponse<CartResponse> updateQuantity(@PathVariable(name = "productId") String productId,
                                                    @PathVariable(name = "quantity") int quantity,
                                                    @PathVariable(name = "size") String size) {
        CartResponse cartResponse = cartService.updateItemInCart(productId, quantity, size);

        return ApiResponse.<CartResponse>builder()
                .code(SUCCESS_REQUEST_CODE)
                .result(cartResponse)
                .build();
    }

    @DeleteMapping("/{productId}/{size}")
    public ApiResponse<Object> removeItemFromCart(@PathVariable(name = "productId") String productId,
                                                  @PathVariable(name = "size") String size) {
        cartService.removeItemFromCart(productId, size);

        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Delete product successfully")
                .build();
    }

    @DeleteMapping("/delete-all")
    public ApiResponse<Object> deleteAllCarts() {
        cartService.deleteAllItemsFromCart();

        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Delete all products successfully")
                .build();
    }
}
