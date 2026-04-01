package com.adamidis.learning.warehousestockflow.Service.Implementation;

import com.adamidis.learning.warehousestockflow.Model.*;
import com.adamidis.learning.warehousestockflow.Repository.CategoryRepository;
import com.adamidis.learning.warehousestockflow.Repository.ProductRepository;
import com.adamidis.learning.warehousestockflow.Rowmapper.StatsRowMapper;
import com.adamidis.learning.warehousestockflow.Service.CategoryService;
import com.adamidis.learning.warehousestockflow.Service.SkuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.adamidis.learning.warehousestockflow.Query.CategoryQuery.STATS_QUERY;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final SkuService skuService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Category createCategory(Category category) {
        category.setCreatedAt(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> getCategories(int page, int size) {
        return categoryRepository.findAll(of(page, size));
    }

    @Override
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Page<Category> searchCategories(String name, int page, int size) {
        return categoryRepository.findByNameContaining(name, of(page, size));
    }

    @Override
    public Product createProduct(Product product) {
        // If frontend already provided a SKU (from preview), you can keep it,
        // but still enforce uniqueness:
        if (product.getSku() == null || product.getSku().isBlank()) {
            for (int i = 0; i < 10; i++) {
                String sku = skuService.generateSku(product.getName());
                if (!productRepository.existsBySku(sku)) {
                    product.setSku(sku);
                    return productRepository.save(product); // save once
                }
            }
            throw new IllegalStateException("Could not generate unique SKU");
        }

        // If SKU was provided by client, validate uniqueness
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("SKU already exists: " + product.getSku());
        }

        System.out.println("Incoming SKU: " + product.getSku());
        log.info("Incoming SKU from client: {}", product.getSku());
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        return productRepository.findAll(of(page, size));
    }

    @Override
    public void addProductToCategory(Long id, Product product) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        product.setCategory(category);

        // Keep incoming SKU from frontend preview; if missing generate one (random)
        if (product.getSku() == null || product.getSku().isBlank()) {
            for (int i = 0; i < 10; i++) {
                String sku = skuService.generateSku(product.getName());
                if (!productRepository.existsBySku(sku)) {
                    product.setSku(sku);
                    break;
                }
            }
            if (product.getSku() == null || product.getSku().isBlank()) {
                throw new IllegalStateException("Could not generate unique SKU");
            }
        } else {
            // If client provided SKU, validate uniqueness
            if (productRepository.existsBySku(product.getSku())) {
                throw new IllegalArgumentException("SKU already exists: " + product.getSku());
            }
        }

        productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> searchProducts(String name, int page, int size) {
        return productRepository.findByNameContaining(name, of(page, size));
    }

    @Override
    public Statistics getStats() {
        return jdbc.queryForObject(STATS_QUERY, Map.of(), new StatsRowMapper());
    }
}
