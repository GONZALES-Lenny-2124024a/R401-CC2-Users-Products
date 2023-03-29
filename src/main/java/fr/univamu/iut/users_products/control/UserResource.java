package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.service.UserRepositoryInterface;
import fr.univamu.iut.users_products.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/users")
@ApplicationScoped
public class UserResource {
    private static final String AUTHORIZATION_TOKEN = "57ac26e6fae7a272cd2bc33b5a56ebb0eabdb2d2aca926a33c8da2ba63a969e790f1e698d51ef5a94fc62ae4f93606c9a44bfd986fad20cf4e8d7d30680a35d1";


    /**
     * Service utilisé pour accéder aux données des utilisateurs et récupérer/modifier leurs informations
     */
    private UserService service;

    /**
     * Constructeur par défaut
     */
    public UserResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param userRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject UserResource( UserRepositoryInterface userRepo ){
        this.service = new UserService( userRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public UserResource( UserService service ){
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
    public Response getAllUsers(@HeaderParam("Authorization") String authHeader) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(service.getAllUsersJSON()).build();
    }

    /**
     * Endpoint permettant de publier les informations d'un livre dont la référence est passée paramètre dans le chemin
     * @param id user's id
     * @return les informations du livre recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUser(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id){
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.getUserJSON(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint permettant de publier les informations d'un livre dont la référence est passée paramètre dans le chemin
     * @param email email de l'utilisateur demandée
     * @return les informations du livre recherché au format JSON
     */
    @POST
    @Path("/authenticate")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticate(@HeaderParam("Authorization") String authHeader, @FormParam("email") String email, @FormParam("password") String password){
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.authenticateJSON(email, password);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUser(@HeaderParam("Authorization") String authHeader, @FormParam("email") String email, @FormParam("password") String password) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.registerUserJSON(email, password);

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
    public Response removeUser(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.removeUser(id);

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
    public Response updateUser(@HeaderParam("Authorization") String authHeader, @FormParam("id") int id, @FormParam("password") String email, @FormParam("password") String password) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.updateUserJSON(id, email, password);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }
}
