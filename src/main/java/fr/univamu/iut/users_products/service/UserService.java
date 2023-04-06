package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.Constants.Success;
import fr.univamu.iut.users_products.domain.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class UserService {
    /**
     * Object to access the repository where user information is stored
     */
    protected UserRepositoryInterface userRepo ;

    /**
     * Constructor for injecting access to data
     * @param userRepo object implementing the data access interface
     */
    public UserService( UserRepositoryInterface userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Get all the users on JSON format
     * @return json string
     */
    public String getAllUsersJSON(){

        ArrayList<User> allUsers = userRepo.getAllUsers();

        // Remove the password for all the users
        for( User currentUser : allUsers ){
            currentUser.setPassword("");
        }

        // Get the list of User on JSON format
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
     * Get a user on JSON format
     * @param id the id of the wanted user
     * @return Response status
     */
    public String getUserJSON( int id ){
        String result = null;

        // Verify if the user exists
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // Get the User on JSON format
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
     * @return the User object on JSON format or an Errors description
     */
    public String authenticateJSON(String email, String password){
        String result = null;

        // Verify if the user exists
        User user = userRepo.getUser(email, password);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // Get the list of User on JSON format
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
     * @return Json of the user object or an Errors description
     */
    public String registerUserJSON(String email, String password) {
        // Verify if the resource already exists
        User user = userRepo.getUser(email);
        if( user != null) {
            return Errors.ALREADY_EXISTS.getDescription();
        }

        // Create the user and verify if the creation worked
        user = userRepo.registerUser(email, password);
        if(user == null) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        // Get the User on JSON format
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
        // Verify if the user exists
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // Remove the user and verify if the deletion worked
        boolean status = userRepo.removeUser(id);
        if(!status) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        return Success.DONE_SUCCESSFULLY.getDescription();
    }

    /**
     * Method update the entire User tuple on JSON format
     * @param id the user's id
     * @param email the user's email
     * @param password the user's password
     * @return the json of the User object or others specific status
     */
    public String updateUserJSON(int id, String email, String password) {
        // Verify if the user exists
        User user = userRepo.getUser(id);
        if(user == null) {
            return Errors.RESOURCE_NOT_EXISTS.getDescription();
        }

        // Update the user and verify if the update worked
        user = userRepo.updateUser(id, email, password);
        if(user == null) {
            return Errors.INTERNAL_ERROR.getDescription();
        }

        // Get the User on JSON format
        String userJson = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            userJson = jsonb.toJson(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return userJson;
    }
}
