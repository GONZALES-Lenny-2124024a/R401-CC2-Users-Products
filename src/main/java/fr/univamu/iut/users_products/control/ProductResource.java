package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.service.ProductRepositoryInterface;
import fr.univamu.iut.users_products.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/products")
@ApplicationScoped
public class ProductResource {
    private static final String AUTHORIZATION_TOKEN = "d8a0973dd27f506e0eafa1189dc181b6fcce7e1286a413398d39abcfb67341ee938f4a5f8e811836aa3fc363d5e268b3edc96594d936e9479d2f3f9449144e3f";

    /**
     * Service utilisé pour accéder aux données des utilisateurs et récupérer/modifier leurs informations
     */
    private ProductService service;

    /**
     * Constructeur par défaut
     */
    public ProductResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param productRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject ProductResource( ProductRepositoryInterface productRepo ){
        this.service = new ProductService( productRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public ProductResource( ProductService service ){
        this.service = service;
    }

    public boolean isValidToken(String tokenReceived) {
        if((tokenReceived == null) || !(tokenReceived.startsWith("Bearer "))) {
            return false;
        }
        String token = tokenReceived.substring("Bearer ".length()).trim();

        return token.equals(AUTHORIZATION_TOKEN);
    }

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des utilisateurs (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public Response getAllProducts(@HeaderParam("Authorization") String authHeader) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(service.getAllProductsJSON()).build();
    }

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

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response createProduct(@HeaderParam("Authorization") String authHeader, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") float price, @FormParam("unit") String unit, @FormParam("quantity") int quantity, @FormParam("quantityAvailable") int quantityAvailable) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.createProduct(name, description, price, unit, quantity,quantityAvailable);

        if(result.equals(Errors.ALREADY_EXISTS.getDescription())) {
            return Response.status( Response.Status.CONFLICT ).build();
        }

        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response removeProduct(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.removeProduct(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Consumes("application/x-www-form-urlencoded")
    public Response updateProduct(@HeaderParam("Authorization") String authHeader, @FormParam("id") int id, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") float price, @FormParam("unit") String unit, @FormParam("quantity") int quantity, @FormParam("quantityAvailable") int quantityAvailable) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.updateProductJSON(id, name, description, price, unit, quantity, quantityAvailable);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }
}
