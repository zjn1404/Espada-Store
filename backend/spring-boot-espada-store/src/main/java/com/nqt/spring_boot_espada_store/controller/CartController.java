package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cart Controller")
public class CartController {

    @NonFinal
    @Value("${success-request-code}")
    int SUCCESS_REQUEST_CODE;

    CartService cartService;

    @GetMapping("/{productId}/{size}/{quantity}")
    @Operation(summary = "Add product to cart", description = "API creates new cart if there's not existing cart and add product into it.")
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
    @Operation(summary = "Get all items in customer's cart", description = "API gets all items in logged in customer's cart.")
    public ApiResponse<List<CartResponse>> getAllCarts() {
        List<CartResponse> cartResponses = cartService.getMyCart();

        return ApiResponse.<List<CartResponse>>builder()
                .code(SUCCESS_REQUEST_CODE)
                .result(cartResponses)
                .build();
    }

    @PutMapping("/{productId}/{size}/{quantity}")
    @Operation(summary = "Update item's quantity in logged-in customer's cart", description = "API updates item's quantity in logged-in customer's cart.")
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
    @Operation(summary = "Delete item in logged-in customer's cart by ID and Size", description = "API deletes item in logged-in customer's cart by ID and Size.")
    public ApiResponse<Object> removeItemFromCart(@PathVariable(name = "productId") String productId,
                                                  @PathVariable(name = "size") String size) {
        cartService.removeItemFromCart(productId, size);

        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Delete product successfully")
                .build();
    }

    @DeleteMapping("/delete-all")
    @Operation(summary = "Delete all items in logged-in customer's cart", description = "API deletes all items in logged-in customer's cart.")
    public ApiResponse<Object> deleteAllCarts() {
        cartService.deleteAllItemsFromCart();

        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Delete all products successfully")
                .build();
    }
}
