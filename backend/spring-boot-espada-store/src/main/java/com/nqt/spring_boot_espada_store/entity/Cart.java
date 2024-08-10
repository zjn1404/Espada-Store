package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @EmbeddedId
    private CartId id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    User user;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity")
    int quantity;

    public Cart(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.id = new CartId(user.getId(), product.getId());
    }
}

