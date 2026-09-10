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
        return List.of();
    }

    public List<Product> getProductsBelowStock(int threshold) {
        return List.of();
    }

    public double calculateTotalWarehouseValue() {
        return 0.0;
    }

    public double getAveragePriceByCategory(String category) {
        return 0.0;
    }

    public List<Product> getTopExpensiveProducts(int n) {
        return List.of();
    }
}
