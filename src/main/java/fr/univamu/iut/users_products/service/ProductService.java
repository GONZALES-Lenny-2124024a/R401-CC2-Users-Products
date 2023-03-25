package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.Constants.Success;
import fr.univamu.iut.users_products.domain.Product;
import fr.univamu.iut.users_products.domain.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ProductService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param productRepo objet implémentant l'interface d'accès aux données
     */
    public ProductService( ProductRepositoryInterface productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Méthode retournant les informations (sans mail et mot de passe) sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllProductsJSON(){

        ArrayList<Product> allProducts = productRepo.getAllProducts();

        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProducts);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getProductByIdJSON(int id) {
        Product product = productRepo.getProductById(id);
        if(product == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }
        String productJson = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            productJson = jsonb.toJson(product);
        } catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return productJson;
    }

    public String reduceProductQuantityJSON(int id, int quantity) {
        // Verify if the product exist
        Product product = productRepo.getProductById(id);
        if(product == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // Verify if there is enough quantity
        if(quantity > product.getQuantity()) {
            return Errors.NOT_ENOUGH_QUANTITY.getDescription();
        }

        // Verify if the update worked
        product.setQuantity(product.getQuantity() - quantity);
        boolean status = productRepo.updateProduct(product);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        // Get the json Product
        String productJson = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            productJson = jsonb.toJson(product);
        } catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return productJson;
    }


    public String createProduct(String name, String description, float price, String unit, int quantity) {
        Product product = new Product(name, description, price, unit, quantity);

        Product productAlreadyExist = productRepo.getProduct(product);
        if(productAlreadyExist == null) {
            return Errors.ALREADY_EXISTS.getDescription();
        }

        boolean status = productRepo.createProduct(product);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        // Get the json Product
        String productJson = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            productJson = jsonb.toJson(product);
        } catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return productJson;
    }


    /**
     * Method to remove a product and return the status
     * @param id the product's id
     * @return the description status
     */
    public String removeProduct(int id) {
        Product product = productRepo.getProductById(id);
        if(product == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        boolean status = productRepo.removeProduct(id);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        return Success.DONE_SUCCESSFULLY.getDescription();
    }
}
