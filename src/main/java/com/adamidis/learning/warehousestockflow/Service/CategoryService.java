package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Model.Category;
import com.adamidis.learning.warehousestockflow.Model.Statistics;
import org.springframework.data.domain.Page;

public interface CategoryService {
    // Category operations
    Category createCategory(Category category);
    Category updateCategory(Category category);
    Page<Category> getCategories(int page, int size);
    Iterable<Category> getCategories();
    Category getCategory(Long id);
    Page<Category> searchCategories(String name, int page, int size);

    // Product operations
    Product createProduct(Product product);
    Page<Product> getProducts(int page, int size);
    void addProductToCategory(Long id, Product product);
    Product getProduct(Long id);
    Iterable<Product> getProducts();
    Page<Product> searchProducts(String name, int page, int size);

    Statistics getStats();
}
