package fr.univamu.iut.users_products.data;

import fr.univamu.iut.users_products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryMariaDbTest {
    private ProductRepositoryMariadb productRepositoryMariadb;

    @BeforeEach
    public void beforeEach() throws SQLException, ClassNotFoundException {
        productRepositoryMariadb = new ProductRepositoryMariadb("jdbc:mariadb://mysql-gonzalesl.alwaysdata.net/gonzalesl_cc2", "gonzalesl_cc2", "e9rXXKmTfcQb3kV");
    }

    @Test
    public void shouldGetAllProducts() {
        for(Object obj : productRepositoryMariadb.getAllProducts()) {
            assertInstanceOf(Product.class, obj);
        }
    }

    @Test
    public void shouldGetProductById1() {
        assertInstanceOf(Product.class, productRepositoryMariadb.getProductById(2));
    }

    @Test
    public void shouldGetProductByProductReturnProduct() {
        Product product = new Product("Lait", "du lait 750mL", 1.5f, "mL", 750, 12);
        assertInstanceOf(Product.class, productRepositoryMariadb.getProduct(product));
    }

    @Test
    public void shouldGetProductByProductReturnNull() {
        Product product = new Product("aa", "aa", -1f, "aaa", -1, -1);
        assertNull(productRepositoryMariadb.getProduct(product));
    }

    @Test
    public void shouldUpdateProductByProduct() {
        Product product = new Product(2,"Lait", "du lait 750mL", 1.5f, "mL", 750, 12);
        assertInstanceOf(Product.class, productRepositoryMariadb.updateProduct(product));
    }

    @Test
    public void shouldUpdateProductByProductReturnNull() {
        Product product = new Product(1,"aa", "aa", -1f, "aaa", -1, -1);
        assertNull(productRepositoryMariadb.updateProduct(product));
    }

    @Test
    public void shouldCreateAndRemoveProduct() {
        Product product = new Product("aa", "aa", -1, "mL", -1, -1);
        assertInstanceOf(Product.class, productRepositoryMariadb.createProduct(product));
        assertTrue(productRepositoryMariadb.removeProduct(productRepositoryMariadb.getProduct(product).getId()));
    }
}
