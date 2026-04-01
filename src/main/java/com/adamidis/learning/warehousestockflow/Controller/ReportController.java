package com.adamidis.learning.warehousestockflow.Controller;

import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping(path = "/low-stock")
    public List<Product> getLowStockProducts() {
        return reportService.getLowStockProducts();
    }

    @GetMapping(path = "/out-of-stock")
    public List<Product> getOutOfStockProducts() {
        return reportService.getOutOfStockProducts();
    }
}
