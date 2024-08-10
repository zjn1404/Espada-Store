package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "checkout")
public class CheckOut {

    @Id
    @Column(name = "checkout_id")
    String checkOutId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "checkout_date")
    Date checkOutDate;
}
