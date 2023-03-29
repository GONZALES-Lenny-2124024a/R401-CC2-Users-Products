package fr.univamu.iut.users_products.service;

import fr.univamu.iut.users_products.domain.User;

import java.util.ArrayList;

public interface UserRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les utilisateurs
     */
    void close();

    /**
     * Méthode retournant la liste de tous les utilisateurs
     * @return une liste d'objets User
     */
    ArrayList<User> getAllUsers() ;

    /**
     * Méthode retournant l'utilisateur dont l'id est passée en paramètre
     * @param email Email de l'utilisateur
     * @return un objet User représentant l'utilisateur recherché
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
