package warehouse;

import org.junit.jupiter.api.Test;
import warehouse.controller.WarehouseController;
import warehouse.domain.Product;
import warehouse.service.WarehouseService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseControllerTest {

    @Test
    void getProductByIdShouldReturnProductWhenProductExists() {

        // Arrange
        WarehouseService warehouseService = mock(WarehouseService.class);

        WarehouseController controller =
                new WarehouseController(warehouseService);

        Product product = new Product(
                "A1",
                "Keyboard",
                499.99,
                20,
                "Electronics"
        );

        when(warehouseService.getProductById("A1"))
                .thenReturn(product);

        // Act
        var response = controller.getProductById("A1");

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());

        verify(warehouseService).getProductById("A1");
    }
    @Test
    void getProductByIdShouldReturn404WhenProductDoesNotExist() {

        // Arrange
        WarehouseService warehouseService = mock(WarehouseService.class);

        WarehouseController controller =
                new WarehouseController(warehouseService);

        when(warehouseService.getProductById("UNKNOWN"))
                .thenReturn(null);

        // Act
        var response = controller.getProductById("UNKNOWN");

        // Assert
        assertEquals(404, response.getStatusCode().value());

        verify(warehouseService).getProductById("UNKNOWN");
    }
}