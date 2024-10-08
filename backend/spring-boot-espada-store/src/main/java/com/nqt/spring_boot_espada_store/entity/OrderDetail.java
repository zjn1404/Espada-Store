package com.nqt.spring_boot_espada_store.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "orderId", column = @Column(name = "order_id")),
        @AttributeOverride(name = "productId", column = @Column(name = "product_id")),
        @AttributeOverride(name = "size", column = @Column(name = "size"))
    })
    OrderDetailId id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH
            })
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH
            })
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity")
    int quantity;

    public OrderDetail(Order order, Product product, String size, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.id = new OrderDetailId(order.getId(), product.getId(), size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail that)) return false;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getOrder(), that.getOrder())
                && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder(), getProduct());
    }
}
