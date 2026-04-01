package com.adamidis.learning.warehousestockflow.Controller;

import com.adamidis.learning.warehousestockflow.DTO.UserDTO;
import com.adamidis.learning.warehousestockflow.Model.*;
import com.adamidis.learning.warehousestockflow.Report.ProductReport;
import com.adamidis.learning.warehousestockflow.Service.CategoryService;
import com.adamidis.learning.warehousestockflow.Service.SkuService;
import com.adamidis.learning.warehousestockflow.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final SkuService skuService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getCategories(@AuthenticationPrincipal UserDTO user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("user", userService.getUserByEmail(user.getEmail()),
                                "page", categoryService.getCategories(page.orElse(0), size.orElse(10)),
                                "stats", categoryService.getStats()))
                        .message("Categories retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getCategory(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "category", categoryService.getCategory(id)))
                        .message("Category retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> searchCategory(@AuthenticationPrincipal UserDTO user, Optional<String> name, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", categoryService.searchCategories(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Categories retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/sku/preview")
    public Map<String, String> previewSku(@RequestParam String name) {
        String sku = skuService.generateSku(name);
        return Map.of("sku", sku);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createCategory(@AuthenticationPrincipal UserDTO user, @RequestBody Category category) {
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "categories", categoryService.createCategory(category)))
                                .message("Category created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateCategory(@AuthenticationPrincipal UserDTO user, @RequestBody Category category) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "category", categoryService.updateCategory(category)))
                        .message("Category updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/product/created")
    public ResponseEntity<HttpResponse> createProduct(@AuthenticationPrincipal UserDTO user, @RequestBody Product product) {
        log.info("Incoming SKU from client: {}", product.getSku());
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "product", categoryService.createProduct(product)))
                                .message("Product created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }

    @GetMapping("/product/new")
    public ResponseEntity<HttpResponse> newProduct(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "categories", categoryService.getCategories()))
                        .message("Category list retrieved for product creation")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/product/list")
    public ResponseEntity<HttpResponse> getProducts(@AuthenticationPrincipal UserDTO user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", categoryService.getProducts(page.orElse(0), size.orElse(10))))
                        .message("Products retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/product/get/{id}")
    public ResponseEntity<HttpResponse> getProduct(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id) {
        Product product = categoryService.getProduct(id);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "product", product))
                        .message("Product retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/product/search")
    public ResponseEntity<HttpResponse> searchProduct(@AuthenticationPrincipal UserDTO user, Optional<String> name, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", categoryService.searchProducts(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Products retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/product/addtocategory/{id}")
    public ResponseEntity<HttpResponse> addProductToCategory(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id, @RequestBody Product product) {
        categoryService.addProductToCategory(id, product);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "categories", categoryService.getCategories()))
                        .message(String.format("Product added to category with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("product/download/report")
    public ResponseEntity<Resource> downloadReport() {
        List<Product> products = new ArrayList<>();
        categoryService.getProducts().iterator().forEachRemaining(products::add);
        ProductReport report = new ProductReport(products);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "product-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=product-report.xlsx");

        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers)
                .body(report.export());
    }
}
