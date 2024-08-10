package com.nqt.spring_boot_espada_store.service.cart;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.entity.Cart;
import com.nqt.spring_boot_espada_store.entity.CartId;
import com.nqt.spring_boot_espada_store.entity.Product;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.CartMapper;
import com.nqt.spring_boot_espada_store.repository.CartRepository;
import com.nqt.spring_boot_espada_store.repository.ProductRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImp implements CartService{

    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    CartMapper cartMapper;

    @Override
    public CartResponse addItemToCart(String productId, int quantity) {
        User user = getUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        Cart cart = new Cart(user, product, quantity);
        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse updateQuantityItemInCart(String productId, int quantity) {
        User user = getUser();

        Cart cart = cartRepository.findById(new CartId(user.getId(), productId))
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        cart.setQuantity(quantity);
        cartRepository.saveAndFlush(cart);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public void removeItemFromCart(String productId) {
        User user = getUser();
        CartId cartId = new CartId(user.getId(), productId);
        cartRepository.deleteById(cartId);
    }

    @Override
    public List<CartResponse> getMyCart() {

        User user = getUser();

        return cartRepository.findCartResponsesByUser(user);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
