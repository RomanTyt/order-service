package ru.tyutyakov.orderservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tyutyakov.orderservice.exception.BusinessException;
import ru.tyutyakov.orderservice.exception.Error;
import ru.tyutyakov.orderservice.model.dto.OrderDTO;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.model.mapper.OrderMapper;
import ru.tyutyakov.orderservice.repository.OrderRepository;

import java.util.List;

/**
 * Сервис для работы с заказами.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Добавляет новый заказ.
     *
     * @param orderDTO Данные заказа в формате DTO.
     * @return Идентификатор добавленного заказа.
     */
    @Transactional
    public Integer addOrder(OrderDTO orderDTO){
        Order order = orderMapper.orderDTOMapToOrder(orderDTO);
        return orderRepository.save(order).getOrderId();
    }

    /**
     * Получает информацию о заказе по его идентификатору.
     *
     * @param orderId Идентификатор заказа.
     * @return Данные о заказе в формате DTO.
     */
    public OrderDTO getOrder(Integer orderId){
        Order order = findOrderById(orderId);
        return orderMapper.orderMapToOrderDTO(order);
    }

    /**
     * Получает список заказов вместе с соответствующими товарами с учетом пагинации.
     *
     * @param pageable   Информация о пагинации для указания номера страницы, размера страницы, сортировки и т. д.
     * @return           Список заказов вместе с соответствующими товарами.
     */
    public List<Order> getAllOrdersWithProducts(Pageable pageable){
        return orderRepository.findAllOrdersWithProducts(pageable);
    }

    /**
     * Обновляет информацию о заказе.
     *
     * @param orderId  Идентификатор заказа.
     * @param orderDTO Новые данные о заказе в формате DTO.
     */
    @Transactional
    public void updateOrder(Integer orderId, OrderDTO orderDTO){
        Order order = findOrderById(orderId);
        orderMapper.update(order, orderDTO);
        orderRepository.save(order);
    }

    /**
     * Удаляет заказ и связанные с ним продукты.
     *
     * @param orderId Идентификатор заказа.
     */
    @Transactional
    public void deleteOrderAndProducts(Integer orderId){
        findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    /**
     * Находит заказ по его идентификатору.
     *
     * @param orderId Идентификатор заказа.
     * @return Заказ, если найден.
     * @throws BusinessException Если заказ не найден, выбрасывается исключение с кодом ошибки и идентификатором заказа.
     */
    public Order findOrderById(Integer orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException(Error.ORDER_NOT_FOUND_EXCEPTION, orderId.toString()));
    }

}
