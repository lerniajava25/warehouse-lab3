package warehouse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

import java.util.List;

@Service
public class WarehouseService {

    public String createId() {
        return UUID.randomUUID().toString();
    }

    private final List<Product> products = new ArrayList<>();


    public WarehouseService() {
        products.add(new Product("A1", "Keyboard", 499.99, 20));
        products.add(new Product("B1", "Mouse", 199.99, 25));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product createProduct(Product product) {
        Product newProduct = new Product(
                createId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );

        products.add(newProduct);

        return newProduct;
    }
}
