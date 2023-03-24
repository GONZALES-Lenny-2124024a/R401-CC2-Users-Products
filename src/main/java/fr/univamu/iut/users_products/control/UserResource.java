package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.service.UserRepositoryInterface;
import fr.univamu.iut.users_products.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

/**
 * Ressource associée aux livres
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
     * @param email email de l'utilisateur demandée
     * @return les informations du livre recherché au format JSON
     */
    @GET
    @Path("{email}")
    @Produces("application/json")
    public String getUser( @PathParam("email") String email){

        String result = service.getUserJSON(email);

        // si le livre n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

}
