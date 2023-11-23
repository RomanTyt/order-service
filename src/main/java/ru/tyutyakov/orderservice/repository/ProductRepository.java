package ru.tyutyakov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.model.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByOrderAndPositionNumber(Order order, Integer positionNumber);

    boolean existsByOrderAndPositionNumber(Order order, Integer positionNumber);

    @Query("SELECT p FROM Product p JOIN FETCH p.order WHERE p.order.orderId = :orderId")
    List<Product> findByIdWithProducts(@Param("orderId") Integer orderId);
}
