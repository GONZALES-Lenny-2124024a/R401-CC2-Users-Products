package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.Constants.Success;
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
     * @param id user's id
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getUserJSON( int id ){
        String result = null;
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Method which return the json of the User
     * @param email user's email
     * @param password user's password
     * @return the User object or null if it doesn't exist
     */
    public String authenticateJSON(String email, String password){
        String result = null;
        User user = userRepo.getUser(email, password);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // création du json et conversion du livre
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }


    /**
     * Method to register an user and return the json of the user or an error
     * @param email user's email
     * @param password user's password
     * @return Json of the user object
     */
    public String registerUserJSON(String email, String password) {
        // Verify if the resource already exists
        User user = userRepo.getUser(email);
        if( user != null) {
            return Errors.ALREADY_EXISTS.getDescription();
        }

        user = userRepo.registerUser(email, password);
        if(user == null) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        String userJson = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            userJson = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return userJson;
    }

    /**
     * Method to remove an user and return the status
     * @param id the user's id
     * @return the description status
     */
    public String removeUser(int id) {
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        boolean status = userRepo.removeUser(id);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        return Success.DONE_SUCCESSFULLY.getDescription();
    }

    /**
     * Method to update the user's password
     * @param id the user's id
     * @param password the user's password
     * @return the json of the User object or others specific status
     */
    public String updatePasswordJSON(int id, String password) {
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        user = userRepo.updatePassword(id, password);
        if(user == null) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        String userJson = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            userJson = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return userJson;
    }
}
