package fr.univamu.iut.users_products.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Product product;

    @BeforeEach
    public void beforeEach() {
        product = new Product(1, "name", "description", 10.0f, "kg", 5, 5);
    }

    @Test
    public void testGetId() {
        assertEquals(1, product.getId());
    }

    @Test
    public void testSetId() {
        product.setId(2);
        assertEquals(2, product.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("name", product.getName());
    }

    @Test
    public void testSetName() {
        product.setName("name2");
        assertEquals("name2", product.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("description", product.getDescription());
    }

    @Test
    public void testSetDescription() {
        product.setDescription("description2");
        assertEquals("description2", product.getDescription());
    }

    @Test
    public void testGetPrice() {
        assertEquals(10.0f, product.getPrice(), 0.0);
    }

    @Test
    public void testSetPrice() {
        product.setPrice(20.0f);
        assertEquals(20.0f, product.getPrice(), 0.0);
    }

    @Test
    public void testGetUnit() {
        assertEquals("kg", product.getUnit());
    }

    @Test
    public void testSetUnit() {
        product.setUnit("g");
        assertEquals("g", product.getUnit());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(5, product.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        product.setQuantity(10);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testGetQuantityAvailable() {
        assertEquals(5, product.getQuantityAvailable());
    }

    @Test
    public void testSetQuantityAvailable() {
        product.setQuantityAvailable(10);
        assertEquals(10, product.getQuantityAvailable());
    }
}
