package com.adamidis.learning.warehousestockflow.Seed;

import com.adamidis.learning.warehousestockflow.Model.Category;
import com.adamidis.learning.warehousestockflow.Model.Product;
import com.adamidis.learning.warehousestockflow.Repository.CategoryRepository;
import com.adamidis.learning.warehousestockflow.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Seeding data... (profile=dev)");

        // Categories
        Category beverages = categoryRepository.findByName("Beverages").orElseGet(() -> categoryRepository.save(Category.builder()
                .name("Beverages")
                .description("Drinks, juices, water, coffee")
                .imageUrl("https://images.unsplash.com/photo-1551024709-8f23befc6f87?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YmV2ZXJhZ2VzfGVufDB8fDB8fHww")
                .createdAt(new Date())
                .build()));

        Category electronics = categoryRepository.findByName("Electronics").orElseGet(() -> categoryRepository.save(Category.builder()
                .name("Electronics")
                .description("Devices, accessories, peripherals")
                .imageUrl("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZWxlY3Ryb25pY3N8ZW58MHx8MHx8fHww")
                .createdAt(new Date())
                .build()));

        Category cleaning = categoryRepository.findByName("Cleaning Supplies").orElseGet(() -> categoryRepository.save(Category.builder()
                .name("Cleaning Supplies")
                .description("Cleaning and hygiene products")
                .imageUrl("https://plus.unsplash.com/premium_photo-1679920025550-75324e59680f?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8Y2xlYW5pbmclMjBzdXBwbGllc3xlbnwwfHwwfHx8MA%3D%3D")
                .createdAt(new Date())
                .build()));

        // Products
        seedProduct("BEVE0001", "Sparkling Water 500ml", "Carbonated water bottle", new BigDecimal("0.89"), 120, "IN_STOCK", beverages,
                "https://media.istockphoto.com/id/2162966245/photo/close-up-of-sparkling-water-in-a-glass.webp?a=1&b=1&s=612x612&w=0&k=20&c=65bG6r9SPahwKw_7osasc7moOrHT-sznMgjSp09oFcc=",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("BEVE0002", "Espresso Beans 1kg", "Coffee beans (medium roast)", new BigDecimal("18.90"), 12, "LOW_IN_STOCK", beverages,
                "https://media.istockphoto.com/id/2214598463/photo/coffee-beans-spilling-from-metal-scoop-next-to-black-bag-on-wooden-table.webp?a=1&b=1&s=612x612&w=0&k=20&c=0f5Nm5FEk6r5hMnWnZBLoghggScjOUwicNtQcfDxEDg=",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("BEVE0003", "Coca Cola 500ml", "Carbonated soft drink in glass bottle", new BigDecimal("1.25"), 25, "IN_STOCK", beverages,
                "https://images.unsplash.com/photo-1648569883125-d01072540b4c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Q29jYSUyMGNvbGElMjA1MDBtbHxlbnwwfHwwfHx8MA%3D%3D",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("ELEC0001", "USB-C Cable 1m", "Durable braided USB-C cable", new BigDecimal("6.50"), 80, "IN_STOCK", electronics,
                "https://plus.unsplash.com/premium_photo-1761494494617-196f8f7dbe4a?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8VVNCLUMlMjBDYWJsZSUyMDFtfGVufDB8fDB8fHww",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("ELEC0002", "Wireless Mouse", "2.4GHz wireless mouse", new BigDecimal("14.99"), 0, "OUT_OF_STOCK", electronics,
                "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8V2lyZWxlc3MlMjBNb3VzZXxlbnwwfHwwfHx8MA%3D%3D",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("ELEC0003", "Bluetooth Speaker", "Portable Bluetooth speaker", new BigDecimal("29.99"), 5, "LOW_IN_STOCK", electronics,
                "https://images.unsplash.com/photo-1589256469067-ea99122bbdc4?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Ymx1ZXRvb3RoJTIwc3BlYWtlcnxlbnwwfHwwfHx8MA%3D%3D",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("CLNR0001", "All‑Purpose Cleaner 1L", "Multi-surface cleaner", new BigDecimal("3.99"), 25, "IN_STOCK", cleaning,
                "https://plus.unsplash.com/premium_photo-1765302374564-ac07db6524d7?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8QWxsJUUyJTgwJTkxUHVycG9zZSUyMENsZWFuZXIlMjAxTHxlbnwwfHwwfHx8MA%3D%3D",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("CLNR0002", "Disinfectant Spray 500ml", "Kills 99.9% of germs", new BigDecimal("5.49"), 10, "LOW_IN_STOCK", cleaning,
                "https://images.unsplash.com/photo-1611762820957-d81b7aaa7d42?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8RGlzaW5mZWN0YW50JTIwU3ByYXklMjA1MDBtbHxlbnwwfHwwfHx8MA%3D%3D",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));

        seedProduct("CLNR0003", "Laundry Detergent 2L", "High-efficiency laundry detergent", new BigDecimal("12.99"), 0, "OUT_OF_STOCK", cleaning,
                "https://images.unsplash.com/photo-1624372635282-b324bcdd4907?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8TGF1bmRyeSUyMERldGVyZ2VudCUyMDJMfGVufDB8fDB8fHww",
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365));
    }

    private void seedProduct(String sku, String name, String description, BigDecimal unitPrice, int quantityStock, String status, Category category, String imageUrl, Date expirationDate) {
        if (productRepository.existsBySku(sku)) return;

        productRepository.save(Product.builder()
                .sku(sku)
                .name(name)
                .description(description)
                .unitPrice(unitPrice)
                .quantityStock(quantityStock)
                .status(status)
                .category(category)
                .imageUrl(imageUrl)
                .expirationDate(expirationDate)
                .build());
    }
}
