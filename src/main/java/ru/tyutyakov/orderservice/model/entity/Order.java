package ru.tyutyakov.orderservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Integer orderId;

    private String customerName;

    private String customerAddress;

    private int totalOrderAmount;

    private LocalDateTime dateOfCreation;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Product> productList;
}
