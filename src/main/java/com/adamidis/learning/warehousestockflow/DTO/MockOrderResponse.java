package com.adamidis.learning.warehousestockflow.DTO;

import com.adamidis.learning.warehousestockflow.Enum.StockStatus;

import java.math.BigDecimal;

public record MockOrderResponse(
        Long productId,
        String productName,
        Integer orderedQuantity,
        Integer quantityBefore,
        Integer quantityAfter,
        BigDecimal unitPrice,
        BigDecimal totalCost,
        StockStatus derivedStatus

) {
}
