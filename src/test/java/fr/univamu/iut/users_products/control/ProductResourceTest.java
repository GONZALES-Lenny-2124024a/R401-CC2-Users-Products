package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.control.ProductResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductResourceTest {

    private ProductResource productResource;

    private static final String VALID_TOKEN = "Bearer d8a0973dd27f506e0eafa1189dc181b6fcce7e1286a413398d39abcfb67341ee938f4a5f8e811836aa3fc363d5e268b3edc96594d936e9479d2f3f9449144e3f";
    private static final String INVALID_TOKEN_START_WITH_BEARER = "Bearer invalidToken";
    private static final String INVALID_TOKEN_START_WITHOUT_BEARER = "invalidToken";

    public ProductResourceTest() {
        productResource = new ProductResource();
    }

    @Test
    public void shouldTryInValidTokenBeginWithBearer() {
        assertFalse(productResource.isValidToken(INVALID_TOKEN_START_WITH_BEARER));
    }

    @Test
    public void shouldTryInValidTokenBeginWithoutBearer() {
        assertFalse(productResource.isValidToken(INVALID_TOKEN_START_WITHOUT_BEARER));
    }

    @Test
    public void shouldTryValidToken() {
        assertTrue(productResource.isValidToken(VALID_TOKEN));
    }
}
