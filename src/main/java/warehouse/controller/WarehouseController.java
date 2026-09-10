package warehouse.controller;

import warehouse.domain.Product;
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
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = warehouseService.getProductById(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return warehouseService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Product product
    ) {
        Product updatedProduct = warehouseService.updateProduct(id, product);

        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        Product deletedProduct = warehouseService.deleteProduct(id);

        if (deletedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedProduct);
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return warehouseService.getProductsByCategory(category);
    }

    @GetMapping("/products/low-stocks")
    public List<Product> getLowStockProducts(@RequestParam int threshold) {
        return warehouseService.getProductsBelowStock(threshold);
    }

    @GetMapping("/products/value")
    public double getProductValue() {
        return warehouseService.calculateTotalWarehouseValue();
    }

    @GetMapping("products/category/{category}/average-price")
    public double getProductValueByCategory(@PathVariable String category) {
        return warehouseService.getAveragePriceByCategory(category);
    }

    @GetMapping("/products/top")
    public List<Product> getTopExpensiveProducts(@RequestParam int n) {
        return warehouseService.getTopExpensiveProducts(n);
    }


}