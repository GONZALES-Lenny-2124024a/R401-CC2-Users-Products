package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.domain.Product;
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

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des utilisateurs (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllProducts() {
        return service.getAllProductsJSON();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getProductById(@PathParam("id") int id) {
        String result = service.getProductByIdJSON(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    @PATCH
    @Path("{id}")
    @Consumes("application/x-www-form-urlencoded")
    public Response reduceProductQuantity(@PathParam("id") int id, @FormParam("quantity") int quantity) {
        String result = service.reduceProductQuantityJSON(id, quantity);

        if (result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.NOT_ENOUGH_QUANTITY.getDescription()) || result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok(result).build();
    }
}
