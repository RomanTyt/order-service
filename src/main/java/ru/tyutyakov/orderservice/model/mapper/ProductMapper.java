package ru.tyutyakov.orderservice.model.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.tyutyakov.orderservice.model.dto.ProductDTO;
import ru.tyutyakov.orderservice.model.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    ProductDTO productMapToProductDTO(Product product);

    Product orderProductDTOMapToProduct(ProductDTO productDTO);

    List<ProductDTO> listProductMapToListProductDTO(List<Product> productList);

    void update(@MappingTarget Product product, ProductDTO productDTO);
}
