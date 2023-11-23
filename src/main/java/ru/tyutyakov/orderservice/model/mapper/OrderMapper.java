package ru.tyutyakov.orderservice.model.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.tyutyakov.orderservice.model.dto.OrderDTO;
import ru.tyutyakov.orderservice.model.entity.Order;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {
    OrderDTO orderMapToOrderDTO(Order order);

    Order orderDTOMapToOrder(OrderDTO orderDTO);

    void update(@MappingTarget Order order, OrderDTO orderDTO);
}
