package warehouse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.domain.Product;
import warehouse.service.WarehouseService;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseServiceTest {

    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() {
        warehouseService = new WarehouseService();
    }

    @Test
    void addProductShouldAddProduct() {

        // Arrange
        int productsBefore = warehouseService.getAllProducts().size();

        Product product = new Product(
                "1",
                "Laptop",
                10000.0,
                5,
                "Computers"
        );

        // Act
        Product addedProduct = warehouseService.addProduct(product);

        // Assert
        assertEquals(
                productsBefore + 1,
                warehouseService.getAllProducts().size()
        );

        assertEquals("Laptop", addedProduct.getName());
        assertEquals(10000.0, addedProduct.getPrice());
        assertEquals(5, addedProduct.getQuantity());
        assertEquals("Computers", addedProduct.getCategory());
    }

    @Test
    void getProductByIdShouldReturnCorrectProduct() {

        // Arrange
        Product product = new Product(
                "1",
                "Laptop",
                10000.0,
                5,
                "Computers"
        );

        Product addedProduct = warehouseService.addProduct(product);

        // Act
        Product result =
                warehouseService.getProductById(addedProduct.getId());

        // Assert
        assertNotNull(result);
        assertEquals(addedProduct.getId(), result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(10000.0, result.getPrice());
        assertEquals(5, result.getQuantity());
        assertEquals("Computers", result.getCategory());
    }

    @Test
    void getProductByIdShouldReturnNullWhenProductDoesNotExist() {

        // Act
        Product result =
                warehouseService.getProductById("does-not-exist");

        // Assert
        assertNull(result);
    }

    @Test
    void getAllProductsShouldReturnAllProducts() {

        // Arrange
        int productsBefore = warehouseService.getAllProducts().size();

        Product laptop = new Product(
                "1", "Laptop", 10000.0, 5, "Computers"
        );

        Product chair = new Product(
                "2", "Chair", 1500.0, 10, "Furniture"
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(chair);

        // Act
        int numberOfProducts =
                warehouseService.getAllProducts().size();

        // Assert
        assertEquals(productsBefore + 2, numberOfProducts);
    }

    @Test
    void getProductsByCategoryShouldReturnOnlyMatchingProducts() {

        // Arrange
        warehouseService.addProduct(
                new Product(
                        "1", "Laptop", 10000.0, 5, "TestCategory"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "2", "Mouse", 500.0, 20, "TestCategory"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "3", "Chair", 1500.0, 10, "Furniture"
                )
        );

        // Act
        var result =
                warehouseService.getProductsByCategory("TestCategory");

        // Assert
        assertEquals(2, result.size());

        assertTrue(
                result.stream()
                        .allMatch(product ->
                                product.getCategory().equals("TestCategory"))
        );
    }

    @Test
    void getProductsBelowStockShouldReturnProductsBelowThreshold() {

        // Arrange
        warehouseService.addProduct(
                new Product(
                        "1", "Laptop", 10000.0, 9, "Computers"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "2", "Monitor", 5000.0, 10, "Computers"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "3", "Printer", 3000.0, 11, "Computers"
                )
        );

        // Act
        var result =
                warehouseService.getProductsBelowStock(10);

        // Assert
        assertTrue(
                result.stream()
                        .anyMatch(product ->
                                product.getName().equals("Laptop"))
        );

        assertFalse(
                result.stream()
                        .anyMatch(product ->
                                product.getName().equals("Monitor"))
        );

        assertFalse(
                result.stream()
                        .anyMatch(product ->
                                product.getName().equals("Printer"))
        );
    }

    @Test
    void calculateTotalWarehouseValueShouldReturnCorrectValue() {

        // Arrange
        double valueBefore =
                warehouseService.calculateTotalWarehouseValue();

        warehouseService.addProduct(
                new Product(
                        "1", "Laptop", 10000.0, 2, "Computers"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "2", "Mouse", 500.0, 10, "Computers"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "3", "Keyboard Pro", 1000.0, 3, "Computers"
                )
        );

        // Act
        double result =
                warehouseService.calculateTotalWarehouseValue();

        // Assert
        assertEquals(
                valueBefore + 28000.0,
                result,
                0.001
        );
    }

    @Test
    void getAveragePriceByCategoryShouldReturnCorrectAverage() {

        // Arrange
        warehouseService.addProduct(
                new Product(
                        "1", "Laptop", 10000.0, 2, "TestCategory"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "2", "Mouse", 500.0, 10, "TestCategory"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "3", "Keyboard Pro", 1500.0, 3, "TestCategory"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "4", "Chair", 2000.0, 4, "Furniture"
                )
        );

        // Act
        double result =
                warehouseService.getAveragePriceByCategory("TestCategory");

        // Assert
        assertEquals(4000.0, result, 0.001);
    }

    @Test
    void getTopExpensiveProductsShouldReturnMostExpensiveProductsFirst() {

        // Arrange
        warehouseService.addProduct(
                new Product(
                        "1", "Laptop", 15000.0, 2, "Computers"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "2", "TV", 10000.0, 4, "Electronics"
                )
        );

        warehouseService.addProduct(
                new Product(
                        "3", "Monitor", 6000.0, 5, "Computers"
                )
        );

        // Act
        var result =
                warehouseService.getTopExpensiveProducts(3);

        // Assert
        assertEquals(3, result.size());

        assertEquals("Laptop", result.get(0).getName());
        assertEquals("TV", result.get(1).getName());
        assertEquals("Monitor", result.get(2).getName());
    }

    @Test
    void updateProductShouldUpdateExistingProduct() {

        // Arrange
        Product original = new Product(
                "1", "Laptop", 10000.0, 5, "Computers"
        );

        Product addedProduct =
                warehouseService.addProduct(original);

        Product updatedLaptop = new Product(
                "ignored",
                "Laptop Pro",
                9000.0,
                8,
                "Computers"
        );

        // Act
        Product updated =
                warehouseService.updateProduct(
                        addedProduct.getId(),
                        updatedLaptop
                );

        // Assert
        assertNotNull(updated);
        assertEquals(addedProduct.getId(), updated.getId());
        assertEquals("Laptop Pro", updated.getName());
        assertEquals(9000.0, updated.getPrice());
        assertEquals(8, updated.getQuantity());
    }

    @Test
    void updateProductShouldReturnNullWhenProductDoesNotExist() {

        Product update = new Product(
                "1",
                "Laptop",
                9000.0,
                8,
                "Computers"
        );

        Product result =
                warehouseService.updateProduct(
                        "does-not-exist",
                        update
                );

        assertNull(result);
    }

    @Test
    void deleteProductShouldOnlyRemoveSelectedProduct() {

        // Arrange
        Product laptop =
                warehouseService.addProduct(
                        new Product(
                                "1",
                                "Laptop",
                                10000.0,
                                5,
                                "Computers"
                        )
                );

        Product chair =
                warehouseService.addProduct(
                        new Product(
                                "2",
                                "Chair",
                                1500.0,
                                10,
                                "Furniture"
                        )
                );

        int productsBefore =
                warehouseService.getAllProducts().size();

        // Act
        Product deleted =
                warehouseService.deleteProduct(laptop.getId());

        // Assert
        assertNotNull(deleted);

        assertEquals(
                productsBefore - 1,
                warehouseService.getAllProducts().size()
        );

        assertNull(
                warehouseService.getProductById(laptop.getId())
        );

        assertNotNull(
                warehouseService.getProductById(chair.getId())
        );
    }

    @Test
    void deleteProductShouldReturnNullWhenProductDoesNotExist() {

        Product result =
                warehouseService.deleteProduct("does-not-exist");

        assertNull(result);
    }
    @Test
    void getProductsByCategoryShouldReturnEmptyListWhenCategoryDoesNotExist() {
        List<Product> result = warehouseService.getProductsByCategory("DoesNotExist");

        assertTrue(result.isEmpty());
    }

    @Test
    void getProductsBelowStockShouldReturnEmptyListWhenNoProductsMatch() {
        List<Product> result = warehouseService.getProductsBelowStock(0);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAveragePriceByCategoryShouldHandleMissingCategory() {
        double result = warehouseService.getAveragePriceByCategory("DoesNotExist");

        assertEquals(0.0, result);
    }

    @Test
    void getTopExpensiveProductsShouldReturnAllProductsWhenNIsLargerThanProductCount() {
        int totalProducts = warehouseService.getAllProducts().size();

        List<Product> result = warehouseService.getTopExpensiveProducts(totalProducts + 10);

        assertEquals(totalProducts, result.size());
    }

    @Test
    void getTopExpensiveProductsShouldReturnEmptyListWhenNIsZero() {
        List<Product> result = warehouseService.getTopExpensiveProducts(0);

        assertTrue(result.isEmpty());
    }
}