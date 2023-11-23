package ru.tyutyakov.orderservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tyutyakov.orderservice.model.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.productList")
    List<Order> findAllOrdersWithProducts(Pageable pageable);
}
