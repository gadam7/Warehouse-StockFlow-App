package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.DTO.MockOrderResponse;
import com.adamidis.learning.warehousestockflow.Enum.StockStatus;
import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final StockStatusService stockStatusService;

    @Transactional
    @CacheEvict(cacheNames = {"lowStockProducts", "outOfStockProducts", "reports"}, allEntries = true)
    public MockOrderResponse placeMockOrder(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        int before = (product.getQuantityStock() == null) ? 0 : product.getQuantityStock();
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("Quantity must be >= 1");
        if (before < quantity) throw new IllegalArgumentException("Not enough stock. In stock: " + before);

        int after = before - quantity;
        product.setQuantityStock(after);
        StockStatus status = stockStatusService.getStatus(after);
        product.setStatus(status.name());

        if (status == StockStatus.OUT_OF_STOCK && product.getRemovedDate() == null) {
            product.setRemovedDate(new Date());
        } else if (status != StockStatus.OUT_OF_STOCK) {
            product.setRemovedDate(null);
        }
        productRepository.save(product);

        BigDecimal unit = (product.getUnitPrice() == null) ? BigDecimal.ZERO : product.getUnitPrice();
        BigDecimal total = unit.multiply(BigDecimal.valueOf(quantity));

        return new MockOrderResponse(
                product.getId(),
                product.getName(),
                quantity,
                before,
                after,
                unit,
                total,
                stockStatusService.getStatus(after)
        );
    }
}
