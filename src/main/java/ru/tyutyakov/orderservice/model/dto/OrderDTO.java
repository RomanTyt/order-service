package ru.tyutyakov.orderservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDTO {
    @NotEmpty(message = "Имя клиента не может быть пустым")
    private String customerName;

    @NotEmpty(message = "Адрес клиента не может быть пустым")
    private String customerAddress;

    @Min(value = 0, message = "Общая сумма заказа должна быть не меньше 0")
    private int totalOrderAmount;

    @NotNull(message = "Дата создания не может быть пустой")
    private LocalDateTime dateOfCreation;
}
