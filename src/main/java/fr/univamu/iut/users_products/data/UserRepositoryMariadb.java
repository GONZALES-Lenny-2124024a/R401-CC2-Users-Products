package fr.univamu.iut.users_products.data;


import fr.univamu.iut.users_products.domain.User;
import fr.univamu.iut.users_products.service.UserRepositoryInterface;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe permettant d'accèder aux utilisateurs stockés dans une base de données Mariadb
 */
public class UserRepositoryMariadb implements UserRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection ;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public UserRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUsers ;

        String query = "SELECT * FROM Users";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listUsers = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                String email = result.getString("email");
                String password = result.getString("password");

                // création du livre courant
                User currentBook = new User(email, password);

                listUsers.add(currentBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }

    @Override
    public User getUser(String email) {

        User selectedUser = null;

        String query = "SELECT * FROM Users WHERE email=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du livre est valide)
            if( result.next() )
            {
                String password = result.getString("password");

                // création et initialisation de l'objet Book
                selectedUser = new User(email,password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }


    /**
     * Method which create an user
     * @param email the user's email
     * @param password the user's password
     * @return User the User returned
     */
    @Override
    public User registerUser(String email, String password) {
        String query = "INSERT INTO Users (email, password) VALUES (?,?)";

        User user = null;
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);
            ps.setString(2,password);

            ps.executeUpdate();
            user = new User(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    /**
     * Method which remove an user
     * @param email the user's email
     * @return if the deletion succeed
     */
    public boolean removeUser(String email) {
        String query = "DELETE FROM Users WHERE EMAIL=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);
            return (ps.executeUpdate() > 0);    // The deletion suceed

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
