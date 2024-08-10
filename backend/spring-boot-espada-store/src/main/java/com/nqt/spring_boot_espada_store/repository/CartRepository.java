package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.dto.response.CartResponse;
import com.nqt.spring_boot_espada_store.entity.Cart;
import com.nqt.spring_boot_espada_store.entity.CartId;
import com.nqt.spring_boot_espada_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {

    @Query("SELECT new com.nqt.spring_boot_espada_store.dto.response.CartResponse(c.product, c.quantity) FROM cart c WHERE c.user = :user")
    List<CartResponse> findCartResponsesByUser(@Param("user") User user);

}
