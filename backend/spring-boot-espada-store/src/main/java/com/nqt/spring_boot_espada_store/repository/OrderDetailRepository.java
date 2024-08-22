package com.nqt.spring_boot_espada_store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.OrderDetail;
import com.nqt.spring_boot_espada_store.entity.OrderDetailId;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findAllByProductId(String productId);

    List<OrderDetail> findAllByOrderId(String orderId);

    @Query("SELECT od.id.productId FROM order_detail od GROUP BY od.product ORDER BY SUM(od.quantity) DESC")
    Page<String> findTop8BestSellers(Pageable pageable);
}
