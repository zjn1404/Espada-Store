package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity(name = "verify_code")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyCode {

    @Id
    @Column(name = "verify_code", length = 6)
    String verifyCode;

    @Column(name = "expiry_date")
    Date expiryDate;

    @OneToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
            @JoinColumn(name = "user_id")
    User user;
}
