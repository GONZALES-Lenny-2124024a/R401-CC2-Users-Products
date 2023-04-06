package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.data.ProductRepositoryMariadb;
import fr.univamu.iut.users_products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        ProductRepositoryInterface productRepository = new ProductRepositoryMariadb("jdbc:mariadb://mysql-gonzalesl.alwaysdata.net/gonzalesl_cc2", "gonzalesl_cc2", "e9rXXKmTfcQb3kV");
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetProductByIdJSON() {
        String productJson = productService.getProductByIdJSON(-1);
        assertEquals(productJson, Errors.RESOURCE_NOT_EXISTS.getDescription());
    }
}
