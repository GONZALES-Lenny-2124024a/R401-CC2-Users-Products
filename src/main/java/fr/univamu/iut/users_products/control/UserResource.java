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

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des utilisateurs (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un livre dont la référence est passée paramètre dans le chemin
     * @param id user's id
     * @return les informations du livre recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUser( @PathParam("id") int id){
        String result = service.getUserJSON(id);

        // si le livre n'a pas été trouvé
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
    public Response authenticate( @FormParam("email") String email, @FormParam("password") String password){

        String result = service.authenticateJSON(email, password);

        // si le livre n'a pas été trouvé
        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUser(@FormParam("email") String email, @FormParam("password") String password) {
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
    public Response removeUser(@PathParam("id") int id) {
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
    public Response updatePassword(@FormParam("id") int id, @FormParam("password") String password) {
        String result = service.updatePasswordJSON(id, password);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }
}
