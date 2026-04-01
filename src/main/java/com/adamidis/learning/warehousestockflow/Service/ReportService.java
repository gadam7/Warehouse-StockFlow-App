package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;

    @Cacheable(cacheNames = "lowStockProducts")
    public List<Product> getLowStockProducts() {
        // low stock: 1..20 (includes 20, excludes 0)
        return productRepository.findByQuantityStockBetween(1, 20);
    }

    @Cacheable(cacheNames = "outOfStockProducts")
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByQuantityStockEquals(0);
    }

    @CacheEvict(cacheNames = {"lowStockProducts", "outOfStockProducts"}, allEntries = true)
    @Scheduled(cron = "0 */5 * * * *", zone = "Europe/Athens") // every 5 minutes
    public void refreshReportCaches() {

    }
}
