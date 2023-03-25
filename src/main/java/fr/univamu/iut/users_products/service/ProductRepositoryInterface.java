package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.domain.Product;

import java.util.ArrayList;


public interface ProductRepositoryInterface {
    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les utilisateurs
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
     * @return if the update worked or not
     */
    boolean updateProduct(Product product);

    /**
     * Method which create a product
     * @param product the product to create
     * @return if the creation worked
     */
    boolean createProduct(Product product);

    /**
     * Method which remove a product
     * @param id the product's id
     * @return if the deletion worked or not
     */
    boolean removeProduct(int id);
}
