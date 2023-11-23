package ru.tyutyakov.orderservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    @NotNull(message = "Порядковый номер не может быть пустым")
    @Min(value = 0, message = "Порядковый номер должен быть не меньше 0")
    private Integer positionNumber;

    @NotEmpty(message = "Серийный номер продукта не может быть пустым")
    private String productSerialNumber;

    @NotEmpty(message = "Наименование продукта не может быть пустым")
    private String productName;

    @Min(value = 0, message = "Количество товаров должно быть не меньше 0")
    private int quantity;
}
