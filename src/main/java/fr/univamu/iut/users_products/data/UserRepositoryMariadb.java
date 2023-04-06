package fr.univamu.iut.users_products.data;


import fr.univamu.iut.users_products.domain.User;
import fr.univamu.iut.users_products.service.UserRepositoryInterface;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class for accessing users stored in a Mariadb database
 */
public class UserRepositoryMariadb implements UserRepositoryInterface, Closeable {

    /**
     * Access to the database
     */
    protected Connection dbConnection ;

    /**
     * Constructor
     *
     * @param infoConnection string with login information
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user           string containing the database login
     * @param pwd            character string containing the database password
     */
    public UserRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    /**
     * Method closing the repository where product information is stored
     */
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method which return all the users
     * @return a list with all the users
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUsers ;

        String query = "SELECT * FROM Users";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();

            listUsers = new ArrayList<>();

            while ( result.next() )
            {
                int id = result.getInt("id");
                String email = result.getString("email");
                String password = result.getString("password");

                User user = new User(id, email, password);

                listUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }


    /**
     * Method which return a User by its email
     * @param email the user's email
     * @return the User corresponding to this email
     */
    @Override
    public User getUser(String email) {
        User selectedUser = null;
        String query = "SELECT * FROM Users WHERE email=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);

            ResultSet result = ps.executeQuery();
            if( result.next() ) {
                int id = result.getInt("id");
                String password = result.getString("password");

                selectedUser = new User(id, email,password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }

    /**
     * Method to return the user with its id
     * @param id user's id
     * @return the User object or null
     */
    @Override
    public User getUser(int id) {
        User selectedUser = null;
        String query = "SELECT * FROM Users WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            if( result.next() ) {
                String email = result.getString("email");
                String password = result.getString("password");

                selectedUser = new User(id, email,password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }

    /**
     * Method to return the user with its email and password
     * @param email user's email
     * @param password user's password
     * @return the User object or null
     */
    @Override
    public User getUser(String email, String password) {
        User selectedUser = null;
        String query = "SELECT * FROM Users WHERE email=? AND password=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);
            ps.setString(2,password);

            ResultSet result = ps.executeQuery();
            if( result.next() ) {
                int id = result.getInt("id");

                selectedUser = new User(id, email,password);
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
     * Method which remove a user
     * @param id the user's id
     * @return if the deletion succeed
     */
    public boolean removeUser(int id) {
        String query = "DELETE FROM Users WHERE ID=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);    // The deletion suceed

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method which update the entire tuple except the id
     * @param id the user's id
     * @param email the user's email
     * @param password the user's password
     * @return a User object or null (if the query failed
     */
    public User updateUser(int id, String email, String password) {
        String query = "UPDATE Users SET EMAIL=?, PASSWORD=? WHERE ID=?";

        User user = null;
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3,id);

            ps.executeUpdate();
            user = new User(id, email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
