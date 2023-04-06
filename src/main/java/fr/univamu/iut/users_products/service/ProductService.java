package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.Constants.Success;
import fr.univamu.iut.users_products.domain.Product;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ProductService {
    /**
     * Object to access the repository where product information is stored
     */
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructor for injecting access to data
     * @param productRepo object implementing the data access interface
     */
    public ProductService( ProductRepositoryInterface productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Get all the products on JSON format
     * @return json string
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

    /**
     * Get a product on JSON format
     * @param id the id of the wanted product
     * @return json string or a Errors description
     */
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


    /**
     * Create a product on JSON format
     * @param name the product name
     * @param description the product description
     * @param price the product price
     * @param unit the product unit
     * @param quantity the product quantity
     * @param quantityAvailable the product quantityAvailable
     * @return json string or a Errors description
     */
    public String createProduct(String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        Product product = new Product(name, description, price, unit, quantity,quantityAvailable);

        Product productAlreadyExist = productRepo.getProduct(product);
        if(productAlreadyExist != null) {
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

    /**
     * Method which update the entire Product tuple and return the Product on JSON format
     * @param id the product's id
     * @param name the product's name
     * @param description the product's description
     * @param price the product's price
     * @param unit the product's unit
     * @param quantity the product's quantity
     * @param quantityAvailable the product's quantityAvailable
     * @return the json of the Product object or others specific status
     */
    public String updateProductJSON(int id, String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        Product product = productRepo.getProductById(id);
        if(product == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        product = new Product(id,name, description, price, unit, quantity, quantityAvailable);
        boolean status = productRepo.updateProduct(product);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        String productJson = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            productJson = jsonb.toJson(product);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return productJson;
    }
}
