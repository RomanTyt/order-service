package ru.tyutyakov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.tyutyakov.orderservice.model.dto.OrderDTO;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping()
    @Operation(summary = "Добавить заказ")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addOrder(@Valid @RequestBody OrderDTO orderDTO){
        return service.addOrder(orderDTO);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Получить заказ по orderId")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrder(@PathVariable Integer orderId){
        return service.getOrder(orderId);
    }

    @GetMapping("/orders")
    @Operation(summary = "Получить все заказы и товары")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrdersWithProducts(@ParameterObject Pageable pageable){
        return service.getAllOrdersWithProducts(pageable);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Редактировать заказ")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@PathVariable Integer orderId, @Valid @RequestBody OrderDTO orderDTO){
         service.updateOrder(orderId, orderDTO);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Удалить заказ и все его товары")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderAndProducts(@PathVariable Integer orderId){
        service.deleteOrderAndProducts(orderId);
    }
}
