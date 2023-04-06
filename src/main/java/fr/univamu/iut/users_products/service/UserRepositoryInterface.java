package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.domain.User;

import java.util.ArrayList;

/**
 * The user repository interface (use to reverse the dependency)
 */
public interface UserRepositoryInterface {

    /**
     * Method closing the repository where product information is stored
     */
    void close();

    /**
     * Method which return all the users
     * @return a list with all the users
     */
    ArrayList<User> getAllUsers() ;

    /**
     * Method which return a User by its email
     * @param email the user's email
     * @return the User corresponding to this email
     */
    User getUser(String email);

    /**
     * Method to return the user with its id
     * @param id user's id
     * @return the User object or null
     */
    User getUser(int id);

    /**
     * Method to return the user with its email and password
     * @param email user's email
     * @param password user's password
     * @return the User object or null
     */
    User getUser(String email, String password);


    /**
     * Method which create a user
     * @param email the user's email
     * @param password the user's password
     * @return User the User returned
     */
    User registerUser(String email, String password);

    /**
     * Method which remove a user
     * @param id the user's id
     * @return if the deletion succeed
     */
    boolean removeUser(int id);

    /**
     * Method which update the entire tuple except the id
     * @param id the user's id
     * @param email the user's email
     * @param password the user's password
     * @return a User object or null (if the query failed
     */
    User updateUser(int id, String email, String password);
}
