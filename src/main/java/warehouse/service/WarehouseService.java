package warehouse.service;

import warehouse.domain.Product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Service
public class WarehouseService {

    // generates a unique id for newly created products
    public String createId() {
        return UUID.randomUUID().toString();
    }

    // thread safe in memory storage for products
    private final ConcurrentHashMap<String, Product> products =
            new ConcurrentHashMap<>();


    public WarehouseService() {
        Product keyboard = new Product("A1", "Keyboard", 499.99, 20, "Electronics");
        products.put(keyboard.getId(), keyboard);

        Product mouse = new Product("A2", "Mouse", 249.99, 15, "Electronics");
        products.put(mouse.getId(), mouse);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public Product addProduct(Product product) {
        Product newProduct = new Product(
                createId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );

        products.put(newProduct.getId(), newProduct);

        return newProduct;
    }

    public Product updateProduct(String id, Product updateProduct) {
        return products.computeIfPresent(id, (key, current) ->
                new Product(
                        current.getId(),
                        updateProduct.getName(),
                        updateProduct.getPrice(),
                        updateProduct.getQuantity(),
                        updateProduct.getCategory()
                )
        );
    }

    public Product deleteProduct(String id) {
        return products.remove(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public List<Product> getProductsBelowStock(int threshold) {
        return products.values().stream()
                .filter(product -> product.getQuantity() < threshold)
                .toList();
    }

    public double calculateTotalWarehouseValue() {
        return products.values().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    public double getAveragePriceByCategory(String category) {
        return products.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }

    public List<Product> getTopExpensiveProducts(int n) {
        return products.values().stream()
                .sorted((product1, product2) ->
                        Double.compare(product2.getPrice(), product1.getPrice()))
                .limit(n)
                .toList();
    }
}
