package com.nqt.spring_boot_espada_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity(name = "cart_detail")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartDetail {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "cartId", column = @Column(name = "cart_id")),
            @AttributeOverride(name = "size", column = @Column(name = "size"))
    })
    CartDetailId cartDetailId;

    @Column(name = "quantity")
    int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    Cart cart;

    public CartDetail(CartDetailId cartDetailId) {
        this.cartDetailId = cartDetailId;
        this.quantity = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDetail that)) return false;
        return Objects.equals(getCartDetailId(), that.getCartDetailId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCartDetailId());
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "cartDetailId=" + cartDetailId +
                ", quantity=" + quantity +
                ", cart=" + cart +
                '}';
    }
}
