package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "subtype")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subtype {

    @Id
    @Column(name = "subtype_name", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_name")
    Type type;
}
