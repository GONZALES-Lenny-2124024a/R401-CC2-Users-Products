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
     * Method which create an user
     * @param email the user's email
     * @param password the user's password
     * @return User the User returned
     */
    User registerUser(String email, String password);
}
