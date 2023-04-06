package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.control.UserResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserResourceTest {

    private UserResource userResource;

    private static final String VALID_TOKEN = "Bearer 57ac26e6fae7a272cd2bc33b5a56ebb0eabdb2d2aca926a33c8da2ba63a969e790f1e698d51ef5a94fc62ae4f93606c9a44bfd986fad20cf4e8d7d30680a35d1";
    private static final String INVALID_TOKEN_START_WITH_BEARER = "Bearer invalidToken";
    private static final String INVALID_TOKEN_START_WITHOUT_BEARER = "invalidToken";

    public UserResourceTest() {
        userResource = new UserResource();
    }

    @Test
    public void shouldTryInValidTokenBeginWithBearer() {
        assertFalse(userResource.isValidToken(INVALID_TOKEN_START_WITH_BEARER));
    }

    @Test
    public void shouldTryInValidTokenBeginWithoutBearer() {
        assertFalse(userResource.isValidToken(INVALID_TOKEN_START_WITHOUT_BEARER));
    }

    @Test
    public void shouldTryValidToken() {
        assertTrue(userResource.isValidToken(VALID_TOKEN));
    }
}
