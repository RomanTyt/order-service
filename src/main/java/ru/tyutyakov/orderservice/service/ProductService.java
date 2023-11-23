package ru.tyutyakov.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tyutyakov.orderservice.exception.BusinessException;
import ru.tyutyakov.orderservice.exception.Error;
import ru.tyutyakov.orderservice.model.dto.ProductDTO;
import ru.tyutyakov.orderservice.model.entity.Order;
import ru.tyutyakov.orderservice.model.entity.Product;
import ru.tyutyakov.orderservice.model.mapper.ProductMapper;
import ru.tyutyakov.orderservice.repository.OrderRepository;
import ru.tyutyakov.orderservice.repository.ProductRepository;

import java.util.List;

/**
 * Сервис для работы с товарами заказа.
 */
@Service
public class ProductService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(OrderRepository orderRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Добавляет новый товар в заказ.
     *
     * @param orderId    Идентификатор заказа.
     * @param productDTO Данные товара в формате DTO.
     * @return Позиция добавленного товара в заказе.
     */
    @Transactional
    public Integer addProductInOrder(Integer orderId, ProductDTO productDTO){
        Order order = findOrderById(orderId);
        if (productRepository.existsByOrderAndPositionNumber(order, productDTO.getPositionNumber())){
            throw new BusinessException(Error.PRODUCT_EXIST_EXCEPTION, "(" + productDTO.getPositionNumber() + ")");
        }
        Product product = productMapper.orderProductDTOMapToProduct(productDTO);
        product.setOrder(order);
        return productRepository.save(product).getPositionNumber();
    }

    /**
     * Получает список товаров по идентификатору заказа.
     *
     * @param orderId Идентификатор заказа.
     * @return Список товаров в формате DTO.
     */
    public List<ProductDTO> getProductsByOrderId(Integer orderId){
        findOrderById(orderId);
        List<Product> products = productRepository.findByIdWithProducts(orderId);
        return productMapper.listProductMapToListProductDTO(products);
    }

    /**
     * Обновляет информацию о товаре по идентификатору заказа и позиции.
     *
     * @param orderId       Идентификатор заказа.
     * @param positionNumber Позиция товара в заказе.
     * @param productDTO    Новые данные о товаре в формате DTO.
     */
    @Transactional
    public void updateProductById(Integer orderId, Integer positionNumber, ProductDTO productDTO){
        Order order = findOrderById(orderId);
        Product product = findProduct(order, positionNumber);
        productMapper.update(product, productDTO);
        productRepository.save(product);
    }

    /**
     * Удаляет товар из заказа по идентификатору заказа и позиции товара.
     *
     * @param orderId       Идентификатор заказа.
     * @param positionNumber Позиция товара в заказе.
     */
    @Transactional
    public void deleteProduct(Integer orderId, Integer positionNumber){
        Order order = findOrderById(orderId);
        Product product = findProduct(order, positionNumber);
        productRepository.delete(product);
    }

    /**
     * Находит товар по идентификатору заказа и позиции товара.
     *
     * @param order         Заказ, к которому привязан товар.
     * @param positionNumber Позиция товара в заказе.
     * @return Товар, если найден.
     * @throws BusinessException Если товар не найден, выбрасывается исключение с кодом ошибки и позицией товара.
     */
    public Product findProduct(Order order, Integer positionNumber){
        return productRepository.findByOrderAndPositionNumber(order, positionNumber)
                .orElseThrow(() -> new BusinessException(Error.PRODUCT_NOT_FOUND_EXCEPTION, positionNumber.toString()));
    }

    /**
     * Находит заказ по его идентификатору.
     *
     * @param orderId Идентификатор заказа.
     * @return Заказ, если найден.
     * @throws BusinessException Если заказ не найден, выбрасывается исключение с кодом ошибки и идентификатором заказа.
     */
    public Order findOrderById(Integer orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(Error.ORDER_NOT_FOUND_EXCEPTION, orderId.toString()));
    }

}
