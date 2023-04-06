package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.service.ProductRepositoryInterface;
import fr.univamu.iut.users_products.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Resource associated with the products
 * API access point
 */
@Path("/products")
@ApplicationScoped
public class ProductResource {
    private static final String AUTHORIZATION_TOKEN = "d8a0973dd27f506e0eafa1189dc181b6fcce7e1286a413398d39abcfb67341ee938f4a5f8e811836aa3fc363d5e268b3edc96594d936e9479d2f3f9449144e3f";

    /**
     * Service used to access product data and retrieve/modify their information
     */
    private ProductService service;

    /**
     * Default constructor
     */
    public ProductResource(){}

    /**
     * Constructor to initialize the service with a data access interface
     * @param productRepo object implementing the product repository interface
     */
    public @Inject ProductResource( ProductRepositoryInterface productRepo ){
        this.service = new ProductService( productRepo) ;
    }

    /**
     * Constructor to initialize the product access service
     * @param service the product service
     */
    public ProductResource( ProductService service ){
        this.service = service;
    }

    /**
     * Method to know if the token provided is valid
     * @param tokenReceived the token
     * @return if the token is valid
     */
    public boolean isValidToken(String tokenReceived) {
        if((tokenReceived == null) || !(tokenReceived.startsWith("Bearer "))) {
            return false;
        }
        String token = tokenReceived.substring("Bearer ".length()).trim();  // Extract the token without 'Bearer '

        return token.equals(AUTHORIZATION_TOKEN);
    }

    /**
     * Endpoint to get all the products on JSON format
     * @param authHeader the header containing the authentication token
     * @return Response status
     */
    @GET
    @Produces("application/json")
    public Response getAllProducts(@HeaderParam("Authorization") String authHeader) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(service.getAllProductsJSON()).build();
    }

    /**
     * Endpoint to get a product on JSON format
     * @param authHeader the header containing the authentication token
     * @param id the id of the wanted product
     * @return Response status
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProductById(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.getProductByIdJSON(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint to create a product and return the product on JSON format
     * @param authHeader the header containing the authentication token
     * @param name the product name
     * @param description the product description
     * @param price the product price
     * @param unit the product unit
     * @param quantity the product quantity
     * @param quantityAvailable the product quantityAvailable
     * @return Response status
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response createProduct(@HeaderParam("Authorization") String authHeader, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") float price, @FormParam("unit") String unit, @FormParam("quantity") int quantity, @FormParam("quantityAvailable") int quantityAvailable) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.createProduct(name, description, price, unit, quantity,quantityAvailable);

        if(result.equals(Errors.ALREADY_EXISTS.getDescription())) {     // If the product already exists
            return Response.status( Response.Status.CONFLICT ).build();
        }

        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint to remove a product by its id
     * @param authHeader the header containing the authentication token
     * @param id the product id
     * @return Response status
     */
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response removeProduct(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.removeProduct(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {    // If the product doesn't exists
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Endpoint to update a product
     * @param authHeader the header containing the authentication token
     * @param id the product id
     * @param name the product name
     * @param description the product description
     * @param price the product price
     * @param unit the product unit
     * @param quantity the product quantity
     * @param quantityAvailable the product quantityAvailable
     * @return Response status
     */
    @PUT
    @Consumes("application/x-www-form-urlencoded")
    public Response updateProduct(@HeaderParam("Authorization") String authHeader, @FormParam("id") int id, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") float price, @FormParam("unit") String unit, @FormParam("quantity") int quantity, @FormParam("quantityAvailable") int quantityAvailable) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.updateProductJSON(id, name, description, price, unit, quantity, quantityAvailable);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {    // If the product doesn't exists
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }
}
