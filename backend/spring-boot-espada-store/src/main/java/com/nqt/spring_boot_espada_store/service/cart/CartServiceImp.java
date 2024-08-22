package com.nqt.spring_boot_espada_store.service.cart;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.entity.*;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.CartMapper;
import com.nqt.spring_boot_espada_store.repository.CartDetailRepository;
import com.nqt.spring_boot_espada_store.repository.CartRepository;
import com.nqt.spring_boot_espada_store.repository.ProductRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImp implements CartService {

    CartRepository cartRepository;
    CartDetailRepository cartDetailRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    CartMapper cartMapper;

    @Override
    public CartResponse addItemToCart(String productId, int quantity, String size) {
        User user = getUser();
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        if (quantity > product.getStock()) {
            throw new AppException(ErrorCode.QUANTITY_GREATER_THAN_STOCK);
        }
        Cart cart = cartRepository
                .findCartByUserIdAndProductId(user.getId(), productId)
                .orElse(new Cart(user, product));
        if (cart.getCartDetails() == null) {
            cart.setCartDetails(new HashSet<>());
        }

        CartDetail cartDetail = cartDetailRepository
                .findById(new CartDetailId(cart.getId(), size))
                .orElse(new CartDetail(new CartDetailId(cart.getId(), size)));
        int currentQuantity = cartDetail.getQuantity();

        cartDetail.setQuantity(currentQuantity + quantity);
        cartDetail.setCart(cart);

        cart.getCartDetails().remove(cartDetail);
        cart.getCartDetails().add(cartDetail);

        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse updateItemInCart(String productId, int quantity, String size) {
        User user = getUser();

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        if (quantity > product.getStock()) {
            throw new AppException(ErrorCode.QUANTITY_GREATER_THAN_STOCK);
        }

        Cart cart = cartRepository
                .findCartByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        CartDetailId cartDetailId = new CartDetailId(cart.getId(), size);
        for (CartDetail cartDetail : cart.getCartDetails()) {
            if (cartDetail.getCartDetailId().equals(cartDetailId)) {
                cartDetail.setQuantity(quantity);
                cartDetailRepository.saveAndFlush(cartDetail);
                break;
            }
        }

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public void removeItemFromCart(String productId, String size) {
        User user = getUser();
        Cart cart = cartRepository
                .findCartByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        CartDetailId cartDetailId = new CartDetailId(cart.getId(), size);
        cartDetailRepository.deleteById(cartDetailId);

        if (cart.getCartDetails() == null || cart.getCartDetails().isEmpty()) {
            cartRepository.deleteById(cart.getId());
        }
    }

    @Transactional
    @Override
    public void deleteAllItemsFromCart() {
        User user = getUser();
        cartRepository.deleteAllByUser(user);
    }

    @Override
    public List<CartResponse> getMyCart() {

        User user = getUser();

        return cartRepository.findCartResponsesByUser(user).stream()
                .map(cartMapper::toCartResponse)
                .toList();
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
