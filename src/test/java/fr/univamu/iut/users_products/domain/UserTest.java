package fr.univamu.iut.users_products.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User(1, "email", "password");

    }
    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void testSetId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("email2");
        assertEquals("email2", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("password2");
        assertEquals("password2", user.getPassword());
    }
}
