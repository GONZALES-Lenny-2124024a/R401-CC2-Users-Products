package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.domain.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class UserService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected UserRepositoryInterface userRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param userRepo objet implémentant l'interface d'accès aux données
     */
    public UserService( UserRepositoryInterface userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Méthode retournant les informations (sans mail et mot de passe) sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllUsersJSON(){

        ArrayList<User> allUsers = userRepo.getAllUsers();

        // on supprime les informations sur les mots de passe et les mails
        for( User currentUser : allUsers ){
            currentUser.setPassword("");
        }

        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUsers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un utilisateur recherché
     * @param email l'email de l'utilisateur recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getUserJSON( String email ){
        String result = null;
        User myUser = userRepo.getUser(email);

        // si le livre a été trouvé
        if( myUser != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Methods to register an user and return the json of the user or an error
     * @param email user's email
     * @param password user's password
     * @return Json of the user object
     */
    public String registerUserJSON(String email, String password) {
        // Verify if the resource already exists
        User user = userRepo.getUser(email);
        if( user != null) {
            return Errors.ALREADY_EXISTS.getDescription(); // Errors::ALREADY_EXISTS (string)
        }

        user = userRepo.registerUser(email, password);
        if (user == null) {
            return Errors.INTERNAL_ERROR.getDescription(); // Errors::CREATION_FAILED  (string)
        }

        String userJson = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            userJson = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return userJson;    //  Json object (string)
    }
}
