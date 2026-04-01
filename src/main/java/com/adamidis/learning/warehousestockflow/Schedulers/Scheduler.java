package com.adamidis.learning.warehousestockflow.Schedulers;

import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Repository.ProductRepository;
import com.adamidis.learning.warehousestockflow.Service.StockStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Scheduler {
    private final ProductRepository productRepository;
    private final StockStatusService stockStatusService;

    @Transactional
    @Scheduled(fixedRate = 50_000) // every 50  seconds
    public void processProductsStock() {
        System.out.println("Processing products stock...");

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            Integer current = product.getQuantityStock();
            int stock = (current == null ? 0 : current);
            product.setQuantityStock(stock + 0);
            product.setStatus(stockStatusService.getStatus(product.getQuantityStock()).name());
        }

        productRepository.saveAll(products);
    }
}
