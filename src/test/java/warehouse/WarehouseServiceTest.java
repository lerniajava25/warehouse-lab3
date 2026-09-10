package warehouse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Product product = new Product(
                "1",
                "Laptop",
                "Electronics",
                10000.0,
                5
        );

        // Act
        warehouseService.addProduct(product);

        // Assert
        assertEquals(1, warehouseService.getProducts().size());
    }

    @Test
    void getProductByIdShouldReturnCorrectProduct() {

        // Arrange
        Product product = new Product(
                "1",
                "Laptop",
                "Electronics",
                10000.0,
                5
        );

        warehouseService.addProduct(product);

        // Act
        Product result = warehouseService.getProductById("1");

        // Assert
        assertEquals(product, result);
    }

    @Test
    void getProductsShouldReturnAllProducts() {

        // Arrange
        Product laptop = new Product(
                "1",
                "Laptop",
                "Electronics",
                10000.0,
                5
        );

        Product mouse = new Product(
                "2",
                "Mouse",
                "Electronics",
                500.0,
                20
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);

        // Act
        int numberOfProducts = warehouseService.getProducts().size();

        // Assert
        assertEquals(2, numberOfProducts);
    }
    @Test
    void getProductsByCategoryShouldReturnOnlyMatchingProducts() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 5
        );

        Product mouse = new Product(
                "2", "Mouse", "Electronics", 500.0, 20
        );

        Product chair = new Product(
                "3", "Chair", "Furniture", 1500.0, 10
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);
        warehouseService.addProduct(chair);

        // Act
        var result =
                warehouseService.getProductsByCategory("Electronics");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(laptop));
        assertTrue(result.contains(mouse));
        assertFalse(result.contains(chair));
    }

    @Test
    void getProductsBelowStockShouldReturnProductsBelowThreshold() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 9
        );

        Product mouse = new Product(
                "2", "Mouse", "Electronics", 500.0, 10
        );

        Product keyboard = new Product(
                "3", "Keyboard", "Electronics", 1000.0, 11
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);
        warehouseService.addProduct(keyboard);

        // Act
        var result = warehouseService.getProductsBelowStock(10);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(laptop));
        assertFalse(result.contains(mouse));
        assertFalse(result.contains(keyboard));
    }
    @Test
    void calculateTotalWarehouseValueShouldReturnCorrectValue() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 2
        );

        Product mouse = new Product(
                "2", "Mouse", "Electronics", 500.0, 10
        );

        Product keyboard = new Product(
                "3", "Keyboard", "Electronics", 1000.0, 3
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);
        warehouseService.addProduct(keyboard);

        // Act
        double result = warehouseService.calculateTotalWarehouseValue();

        // Assert
        assertEquals(28000.0, result);
    }
    @Test
    void getAveragePriceByCategoryShouldReturnCorrectAverage() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 2
        );

        Product mouse = new Product(
                "2", "Mouse", "Electronics", 500.0, 10
        );

        Product keyboard = new Product(
                "3", "Keyboard", "Electronics", 1500.0, 3
        );

        Product chair = new Product(
                "4", "Chair", "Furniture", 2000.0, 4
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);
        warehouseService.addProduct(keyboard);
        warehouseService.addProduct(chair);

        // Act
        double result =
                warehouseService.getAveragePriceByCategory("Electronics");

        // Assert
        assertEquals(4000.0, result, 0.001);
    }
    @Test
    void getTopExpensiveProductsShouldReturnMostExpensiveProductsFirst() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 15000.0, 2
        );

        Product tv = new Product(
                "2", "TV", "Electronics", 10000.0, 4
        );

        Product monitor = new Product(
                "3", "Monitor", "Electronics", 6000.0, 5
        );

        Product keyboard = new Product(
                "4", "Keyboard", "Electronics", 2000.0, 10
        );

        Product mouse = new Product(
                "5", "Mouse", "Electronics", 500.0, 20
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(tv);
        warehouseService.addProduct(monitor);
        warehouseService.addProduct(keyboard);
        warehouseService.addProduct(mouse);

        // Act
        var result = warehouseService.getTopExpensiveProducts(3);

        // Assert
        assertEquals(3, result.size());
        assertEquals(laptop, result.get(0));
        assertEquals(tv, result.get(1));
        assertEquals(monitor, result.get(2));
    }
    @Test
    void updateProductShouldUpdateExistingProduct() {

        // Arrange
        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 5
        );

        warehouseService.addProduct(laptop);

        Product updatedLaptop = new Product(
                "1", "Laptop", "Electronics", 9000.0, 8
        );

        // Act
        warehouseService.updateProduct("1", updatedLaptop);

        // Assert
        Product result = warehouseService.getProductById("1");

        assertEquals(updatedLaptop, result);
    }
    @Test
    void deleteProductShouldOnlyRemoveSelectedProduct() {

        Product laptop = new Product(
                "1", "Laptop", "Electronics", 10000.0, 5
        );

        Product mouse = new Product(
                "2", "Mouse", "Electronics", 500.0, 20
        );

        warehouseService.addProduct(laptop);
        warehouseService.addProduct(mouse);

        warehouseService.deleteProduct("1");

        assertEquals(1, warehouseService.getProducts().size());
        assertTrue(warehouseService.getProducts().contains(mouse));
        assertFalse(warehouseService.getProducts().contains(laptop));
    }
}