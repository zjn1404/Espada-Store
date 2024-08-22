package com.nqt.spring_boot_espada_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.Cart;
import com.nqt.spring_boot_espada_store.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    List<Cart> findCartResponsesByUser(User user);

    Optional<Cart> findCartByUserIdAndProductId(String userId, String productId);

    void deleteAllByUser(User user);
}
