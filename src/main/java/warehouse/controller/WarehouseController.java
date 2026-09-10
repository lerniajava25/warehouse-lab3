package warehouse.controller;

import warehouse.domain.Product;
import warehouse.response.ApiError;
import warehouse.response.AveragePriceResponse;
import warehouse.response.InventoryValueResponse;
import warehouse.service.WarehouseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return warehouseService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product product = warehouseService.getProductById(id);

        if (product == null) {
            ApiError error = new ApiError(
                    404,
                    "Not Found",
                    "Product not found: " + id
            );

            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = warehouseService.addProduct(product);

        return ResponseEntity
                .status(201)
                .body(createdProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestBody Product product
    ) {
        Product updatedProduct = warehouseService.updateProduct(id, product);

        if (updatedProduct == null) {
            ApiError error = new ApiError(
                    404,
                    "Not Found",
                    "Product not found: " + id
            );

            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Product deletedProduct = warehouseService.deleteProduct(id);

        if (deletedProduct == null) {
            ApiError error = new ApiError(
                    404,
                    "Not Found",
                    "Product not found: " + id
            );

            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return warehouseService.getProductsByCategory(category);
    }

    @GetMapping("/products/low-stock")
    public ResponseEntity<?> getLowStockProducts(@RequestParam int threshold) {
        if (threshold < 0) {
            ApiError error = new ApiError(
                    400,
                    "Bad Request",
                    "Threshold cannot be negative"
            );

            return ResponseEntity.status(400).body(error);
        }

        return ResponseEntity.ok(
                warehouseService.getProductsBelowStock(threshold)
        );
    }

    @GetMapping("/products/value")
    public InventoryValueResponse getTotalWarehouseValue() {
        double totalValue = warehouseService.calculateTotalWarehouseValue();

        return new InventoryValueResponse(totalValue);
    }

    @GetMapping("/products/category/{category}/average-price")
    public AveragePriceResponse getAveragePriceByCategory(@PathVariable String category) {
        double averagePrice = warehouseService.getAveragePriceByCategory(category);

        return new AveragePriceResponse(category, averagePrice);
    }

    @GetMapping("/products/top")
    public ResponseEntity<?> getTopExpensiveProducts(@RequestParam int n) {
        if (n <= 0) {
            ApiError error = new ApiError(
                    400,
                    "Bad Request",
                    "n must be greater than 0"
            );

            return ResponseEntity.status(400).body(error);
        }

        return ResponseEntity.ok(
                warehouseService.getTopExpensiveProducts(n)
        );
    }

}