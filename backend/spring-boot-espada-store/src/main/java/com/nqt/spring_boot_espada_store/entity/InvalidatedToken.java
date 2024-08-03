package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity(name = "invalidated_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {

    @Id
    @Column(name = "ac_id")
    @Setter(AccessLevel.NONE)
    String acId;

    @Column(name = "rf_id")
    @Setter(AccessLevel.NONE)
    String rfId;

    @Column(name = "expiry_time")
    Date expiryTime;
}
