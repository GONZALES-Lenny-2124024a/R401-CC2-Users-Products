package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.domain.Product;

import java.util.ArrayList;

/**
 * The product repository interface (use to reverse the dependency)
 */
public interface ProductRepositoryInterface {
    /**
     * Method closing the repository where product information is stored
     */
    void close();

    /**
     * Method which return all the products
     * @return a list with all the products
     */
    ArrayList<Product> getAllProducts();

    /**
     * Method which return a Product by its name
     * @param id the product's id
     * @return the Product corresponding to this name
     */
    Product getProductById(int id);

    /**
     * Method which return a Product by a Product object
     * @param product Product object
     * @return a Product object
     */
    Product getProduct(Product product);

    /**
     * Method which update a product
     * @param product the new product object
     * @return the product if the update worked, or null if not
     */
    Product updateProduct(Product product);

    /**
     * Method which create a product
     * @param product the product to create
     * @return the product or null if the creation doesn't worked
     */
    Product createProduct(Product product);

    /**
     * Method which remove a product
     * @param id the product's id
     * @return if the deletion worked or not
     */
    boolean removeProduct(int id);
}
