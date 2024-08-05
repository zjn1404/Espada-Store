package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @Column(name = "customer_id")
    String customerId;

    @Column(name = "gender")
    String gender;

    @Column(name = "address")
    String address;

    @Column(name = "delivery_address")
    String deliveryAddress;

    @Column(name = "dob")
    Date dob;

    @Column(name = "register_to_get_mail")
    boolean registerToGetMail;


    @OneToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    User user;
}
