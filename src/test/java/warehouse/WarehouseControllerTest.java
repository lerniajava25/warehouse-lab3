package warehouse;

import warehouse.response.ApiError;
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
        WarehouseService warehouseService = mock(WarehouseService.class);
        WarehouseController controller = new WarehouseController(warehouseService);

        when(warehouseService.getProductById("UNKNOWN")).thenReturn(null);

        var response = controller.getProductById("UNKNOWN");

        assertEquals(404, response.getStatusCode().value());

        assertInstanceOf(ApiError.class, response.getBody());

        ApiError error = (ApiError) response.getBody();

        assertEquals(404, error.status());
        assertEquals("Not Found", error.error());
        assertEquals("Product not found: UNKNOWN", error.message());

        verify(warehouseService).getProductById("UNKNOWN");
    }
}