package ru.tyutyakov.orderservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.tyutyakov.orderservice.exception.BusinessException;
import ru.tyutyakov.orderservice.model.dto.OrderDTO;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.model.entity.Product;
import ru.tyutyakov.orderservice.model.mapper.OrderMapperImpl;
import ru.tyutyakov.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith({
        MockitoExtension.class
})
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    private OrderService sut;

    private final LocalDateTime localDateTime = LocalDateTime.now();

    private final List<Product> productList = new ArrayList<>();
    private final List<Order> orderList = new ArrayList<>();
    private final Integer orderId = 1;

    private final Order order = new Order(orderId, "Василий Уткин", "г.Москва, ул.Пушкина, д.15", 10, localDateTime, productList);

    private final OrderDTO orderDTO = new OrderDTO("Василий Уткин", "г.Москва, ул.Пушкина, д.15", 10, localDateTime);

    private final Product product0 = new Product(0, "123456789", "УШМ Bosch", 2, order);
    private final Product product1 = new Product(1, "987654321", "УШМ Makita", 4, order);

    @BeforeEach
    void setUp() {
        productList.add(product0);
        productList.add(product1);
        orderList.add(order);
        sut = new OrderService(orderRepository, new OrderMapperImpl());
    }

    @Test
    @DisplayName("Добавление заказа")
    void addOrder() {
        Mockito.doReturn(order).when(orderRepository).save(any());
        Integer result = sut.addOrder(orderDTO);
        assertEquals(order.getOrderId(), result);
    }

    @Test
    @DisplayName("Получение заказа по идентификатору")
    void getOrder() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        OrderDTO result = sut.getOrder(orderId);
        assertEquals(orderDTO, result);
    }
    @Test
    @DisplayName("Получение всех заказов и товаров - успешный случай")
    void getAllOrdersWithProducts() {
        Mockito.doReturn(orderList).when(orderRepository).findAllOrdersWithProducts(PageRequest.of(0,10));
        List<Order> result = sut.getAllOrdersWithProducts(PageRequest.of(0,10));
        assertEquals(orderList, result);
    }

    @Test
    @DisplayName("Обновление заказа")
    void updateOrder() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        sut.updateOrder(orderId, orderDTO);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);

    }

    @Test
    @DisplayName("Удаление заказа и продуктов")
    void deleteOrderAndProducts() {
        Mockito.doReturn(Optional.of(order)).when(orderRepository).findById(orderId);
        sut.deleteOrderAndProducts(orderId);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(orderId);
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