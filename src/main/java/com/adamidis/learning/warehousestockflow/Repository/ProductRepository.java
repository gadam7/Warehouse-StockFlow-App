package com.adamidis.learning.warehousestockflow.Repository;

import com.adamidis.learning.warehousestockflow.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long> {
    boolean existsBySku(String sku);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    List<Product> findByQuantityStockBetween(Integer min, Integer max);

    List<Product> findByQuantityStockEquals(Integer q);
}
