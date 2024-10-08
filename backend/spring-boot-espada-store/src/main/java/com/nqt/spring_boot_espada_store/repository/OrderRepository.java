package com.nqt.spring_boot_espada_store.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.Order;
import com.nqt.spring_boot_espada_store.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByUser(User user);

    void deleteOrdersByOrderingDateBefore(Date date);
}
