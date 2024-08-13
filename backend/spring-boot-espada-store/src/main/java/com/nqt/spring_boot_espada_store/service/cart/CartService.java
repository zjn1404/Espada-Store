package com.nqt.spring_boot_espada_store.service.cart;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addItemToCart(String productId, int quantity, String size);

    CartResponse updateItemInCart(String productId, int quantity, String size);

    void removeItemFromCart(String productId, String size);

    void deleteAllItemsFromCart();

    List<CartResponse> getMyCart();

}
