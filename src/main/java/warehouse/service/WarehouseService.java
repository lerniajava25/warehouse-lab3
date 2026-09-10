package warehouse.service;

import warehouse.domain.Product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        Product product = products.get(id);

        if (product == null) {
            return null;
        }

        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setQuantity(updateProduct.getQuantity());
        product.setCategory(updateProduct.getCategory());

        return product;
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
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(n)
                .toList();
    }
}
