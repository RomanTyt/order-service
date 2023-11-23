package ru.tyutyakov.orderservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@IdClass(Product.ProductId.class)
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Product {
    @Id
    @Column(name = "position_number")
    private Integer positionNumber;

    @Column(name = "product_serial_number")
    private String productSerialNumber;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class ProductId implements Serializable {
        private Order order;
        private Integer positionNumber;
    }
}