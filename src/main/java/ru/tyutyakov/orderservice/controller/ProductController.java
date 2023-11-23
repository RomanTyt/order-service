package ru.tyutyakov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.tyutyakov.orderservice.model.dto.ProductDTO;
import ru.tyutyakov.orderservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/{orderId}/products")
    @Operation(summary = "Добавить один товар в заказ")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addProductInOrder(@PathVariable Integer orderId, @Valid @RequestBody ProductDTO productDTO){
        return service.addProductInOrder(orderId, productDTO);
    }

    @GetMapping("/{orderId}/products")
    @Operation(summary = "Получить все товары из заказа по orderId")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getProductsByOrderId(@PathVariable Integer orderId){
        return service.getProductsByOrderId(orderId);
    }

    @PutMapping("/{orderId}/products/{positionNumber}")
    @Operation(summary = "Изменить один товар в заказе")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductById(@PathVariable Integer orderId, @PathVariable Integer positionNumber, @Valid @RequestBody ProductDTO productDTO){
        service.updateProductById(orderId, positionNumber, productDTO);
    }

    @DeleteMapping("/{orderId}/products/{positionNumber}")
    @Operation(summary = "Удалить один товар из заказа")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer orderId, @PathVariable Integer positionNumber){
        service.deleteProduct(orderId, positionNumber);
    }

}
