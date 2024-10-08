package com.nqt.spring_boot_espada_store.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @Column(name = "order_id")
    String id;

    @Column(name = "delivery_address")
    String deliveryAddress;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "state")
    String state;

    @Column(name = "payment")
    double payment;

    @Column(name = "payment_state")
    boolean paymentState;

    @Column(name = "ordering_date")
    Date orderingDate;

    @Column(name = "shipping_date")
    Date shippingDate;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH
            })
    @JoinColumn(name = "customer_id")
    User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<OrderDetail> orderDetails;
}
