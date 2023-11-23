package ru.tyutyakov.orderservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tyutyakov.orderservice.exception.BusinessException;
import ru.tyutyakov.orderservice.model.dto.OrderDTO;
import ru.tyutyakov.orderservice.model.dto.ProductDTO;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.model.entity.Product;
import ru.tyutyakov.orderservice.model.mapper.OrderMapper;
import ru.tyutyakov.orderservice.model.mapper.OrderMapperImpl;
import ru.tyutyakov.orderservice.model.mapper.ProductMapperImpl;
import ru.tyutyakov.orderservice.repository.OrderRepository;
import ru.tyutyakov.orderservice.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({
        MockitoExtension.class
})

class ProductServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    private ProductService sut;
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final List<Product> productList = new ArrayList<>();
    private final List<ProductDTO> productDTOList = new ArrayList<>();
    private final Integer orderId = 1;
    private final Order order = new Order(orderId, "Василий Уткин", "г.Москва, ул.Пушкина, д.15", 10, localDateTime, productList);
    private final Product product0 = new Product(0, "123456789", "УШМ Bosch", 2, order);
    private final Product product1 = new Product(1, "987654321", "УШМ Makita", 4, order);
    private final ProductDTO product0DTO = new ProductDTO(0, "123456789", "УШМ Bosch", 2);
    private final ProductDTO product1DTO = new ProductDTO(1, "987654321", "УШМ Makita", 4);
    private final Integer product0PositionNumber = product0.getPositionNumber();

    @BeforeEach
    void setUp() {
        productList.add(product0);
        productList.add(product1);
        productDTOList.add(product0DTO);
        productDTOList.add(product1DTO);
        sut = new ProductService(orderRepository, productRepository, new ProductMapperImpl());

    }

    @Test
    @DisplayName("Добавление продукта в заказ - успешный случай")
    void addProductInOrder() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Mockito.doReturn(product0).when(productRepository).save(product0);
        Integer result = sut.addProductInOrder(orderId, product0DTO);
        assertEquals(product0PositionNumber, result);
    }
    @Test
    @DisplayName("Добавление продукта в заказ - исключение, заказ не найден")
    void addProductInOrderExceptionOrderNotFound() {
        Mockito.doReturn(Optional.empty()).when(orderRepository).findById(orderId);
        assertThrows(BusinessException.class, () -> sut.addProductInOrder(orderId, product0DTO));
    }

    @Test
    @DisplayName("Добавление продукта в заказ - исключение, продукт уже существует")
    void addProductInOrderExceptionProductExist() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Mockito.doReturn(true).when(productRepository).existsByOrderAndPositionNumber(order, product0PositionNumber);
        assertThrows(BusinessException.class, () -> sut.addProductInOrder(orderId, product0DTO));

    }

    @Test
    @DisplayName("Получение списка продуктов по идентификатору заказа")
    void getProductsByOrderId() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Mockito.doReturn(productList).when(productRepository).findByIdWithProducts(orderId);
        List<ProductDTO> result = sut.getProductsByOrderId(orderId);
        assertEquals(productDTOList, result);
    }

    @Test
    @DisplayName("Обновление продукта по идентификатору заказа и позиции")
    void updateProductById() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Mockito.doReturn(Optional.of(product0)).when(productRepository).findByOrderAndPositionNumber(order, product0PositionNumber);
        sut.updateProductById(orderId, product0PositionNumber, product0DTO);
        Mockito.verify(productRepository, Mockito.times(1)).save(product0);
    }

    @Test
    @DisplayName("Удаление продукта по идентификатору заказа и позиции")
    void deleteProduct() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Mockito.doReturn(Optional.of(product0)).when(productRepository).findByOrderAndPositionNumber(order, product0PositionNumber);
        sut.deleteProduct(orderId, product0PositionNumber);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product0);
    }

    @Test
    @DisplayName("Поиск продукта - успешный случай")
    void findProduct() {
        Mockito.doReturn(Optional.of(product0)).when(productRepository).findByOrderAndPositionNumber(order, product0PositionNumber);
        Product result = sut.findProduct(order, product0PositionNumber);
        assertEquals(product0, result);
    }

    @Test
    @DisplayName("Поиск продукта - исключение, продукт не найден")
    void findProductExceptionProductNotFound() {
        Mockito.doReturn(Optional.empty()).when(productRepository).findByOrderAndPositionNumber(order, product0PositionNumber);
        assertThrows(BusinessException.class, () -> sut.findProduct(order, product0PositionNumber));

    }

    @Test
    @DisplayName("Поиск заказа по идентификатору - успешный случай")
    void findOrderById() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        Order result = sut.findOrderById(orderId);
        assertEquals(order, result);
    }

    @Test
    @DisplayName("Поиск заказа по идентификатору - исключение, заказ не найден")
    void findOrderByIdException() {
        Mockito.doReturn(Optional.empty()).when(orderRepository).findById(orderId);
        assertThrows(BusinessException.class, () -> sut.findOrderById(orderId));
    }

}