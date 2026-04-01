package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Enum.StockStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockStatusService {

    // configurable threshold (default is 20)
    @Value("${app.stock.low-threshold:20}")
    private int lowThreshold;

    public StockStatus getStatus(Integer quantityStock) {
        int q = (quantityStock == null) ? 0 : quantityStock;
        if (q <= 0) return StockStatus.OUT_OF_STOCK;
        if (q <= lowThreshold) return StockStatus.LOW_IN_STOCK;
        return StockStatus.IN_STOCK;
    }

    public int getLowThreshold() {
        return lowThreshold;
    }
}
