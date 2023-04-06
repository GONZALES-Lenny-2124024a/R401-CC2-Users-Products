package fr.univamu.iut.users_products.domain;

/**
 * Class representing a User
 */
public class User {
    private int id;
    private String email;
    private String password;

    /**
     * Constructor with user id
     * @param id the user id
     * @param email the user email
     * @param password the user password
     */
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor without user id
     * @param email the user email
     * @param password the user password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Get the user id
     * @return the user id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the user id
     * @param id the new user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the user email
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email
     * @param email the new user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user password
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user password
     * @param password the new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
