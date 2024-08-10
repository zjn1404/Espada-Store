package com.nqt.spring_boot_espada_store.service.cart;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addItemToCart(String productId, int quantity);

    CartResponse updateQuantityItemInCart(String productId, int quantity);

    void removeItemFromCart(String productId);

    List<CartResponse> getMyCart();

}
