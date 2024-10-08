package com.nqt.spring_boot_espada_store.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "customer_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDetail {

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

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    User user;
}
