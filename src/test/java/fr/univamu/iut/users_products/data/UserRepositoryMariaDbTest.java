package fr.univamu.iut.users_products.data;

import fr.univamu.iut.users_products.domain.Product;
import fr.univamu.iut.users_products.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryMariaDbTest {
    private UserRepositoryMariadb userRepositoryMariadb;

    @BeforeEach
    public void beforeEach() throws SQLException, ClassNotFoundException {
        userRepositoryMariadb = new UserRepositoryMariadb("jdbc:mariadb://mysql-gonzalesl.alwaysdata.net/gonzalesl_cc2", "gonzalesl_cc2", "e9rXXKmTfcQb3kV");
    }

    @Test
    public void shouldGetAllUsers() {
        for(Object obj : userRepositoryMariadb.getAllUsers()) {
            assertInstanceOf(User.class, obj);
        }
    }

    @Test
    public void shouldGetUserById1() {
        assertInstanceOf(User.class, userRepositoryMariadb.getUser(6));
    }

    @Test
    public void shouldGetUserByUserReturnNull() {
        User user = new User("aa", "aa");
        assertNull(userRepositoryMariadb.getUser(user.getId()));
    }

    @Test
    public void shouldGetUserByUser() {
        User user = new User("lenny2", "pwd2");
        assertInstanceOf(User.class, userRepositoryMariadb.getUser(user.getEmail(), user.getPassword()));
    }

    @Test
    public void shouldRegisterAndRemoveUser() {
        User user = new User("-1", "-1");
        assertInstanceOf(User.class, userRepositoryMariadb.registerUser(user.getEmail(), user.getPassword()));

        assertTrue(userRepositoryMariadb.removeUser(userRepositoryMariadb.getUser(user.getEmail(), user.getPassword()).getId()));
    }
}
