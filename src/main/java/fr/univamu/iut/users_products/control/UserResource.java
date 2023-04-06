package fr.univamu.iut.users_products.control;

import fr.univamu.iut.users_products.Constants.Errors;
import fr.univamu.iut.users_products.service.UserRepositoryInterface;
import fr.univamu.iut.users_products.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Resource associated with the users
 * API access point
 */
@Path("/users")
@ApplicationScoped
public class UserResource {
    private static final String AUTHORIZATION_TOKEN = "57ac26e6fae7a272cd2bc33b5a56ebb0eabdb2d2aca926a33c8da2ba63a969e790f1e698d51ef5a94fc62ae4f93606c9a44bfd986fad20cf4e8d7d30680a35d1";


    /**
     * Service used to access user data and retrieve/modify their information
     */
    private UserService service;

    /**
     * Default constructor
     */
    public UserResource(){}

    /**
     * Constructor to initialize the service with a data access interface
     * @param userRepo object implementing the user repository interface
     */
    public @Inject UserResource( UserRepositoryInterface userRepo ){
        this.service = new UserService( userRepo) ;
    }

    /**
     * Constructor to initialize the user access service
     * @param service the user service
     */
    public UserResource( UserService service ){
        this.service = service;
    }

    /**
     * Method to know if the token provided is valid
     * @param tokenReceived the token
     * @return if the token is valid
     */
    public boolean isValidToken(String tokenReceived) {
        if((tokenReceived == null) || !(tokenReceived.startsWith("Bearer "))) {
            return false;
        }
        String token = tokenReceived.substring("Bearer ".length()).trim();

        return token.equals(AUTHORIZATION_TOKEN);
    }

    /**
     * Endpoint to get all the users on JSON format
     * @param authHeader the header containing the authentication token
     * @return Response status
     */
    @GET
    @Produces("application/json")
    public Response getAllUsers(@HeaderParam("Authorization") String authHeader) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(service.getAllUsersJSON()).build();
    }

    /**
     * Endpoint to get a user on JSON format
     * @param authHeader the header containing the authentication token
     * @param id the id of the wanted user
     * @return Response status
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUser(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id){
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.getUserJSON(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint to know if the user exists
     * @param authHeader the header containing the authentication token
     * @param email the user email
     * @param password the user password
     * @return Response status
     */
    @POST
    @Path("/authenticate")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticate(@HeaderParam("Authorization") String authHeader, @FormParam("email") String email, @FormParam("password") String password){
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String result = service.authenticateJSON(email, password);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint to create a user
     * @param authHeader the header containing the authentication token
     * @param email the user email
     * @param password the user password
     * @return Response status
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUser(@HeaderParam("Authorization") String authHeader, @FormParam("email") String email, @FormParam("password") String password) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.registerUserJSON(email, password);

        if(result.equals(Errors.ALREADY_EXISTS.getDescription())) {
            return Response.status( Response.Status.CONFLICT ).build();
        }

        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }

    /**
     * Endpoint to remove a user by its id
     * @param authHeader the header containing the authentication token
     * @param id the user id
     * @return Response status
     */
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response removeUser(@HeaderParam("Authorization") String authHeader, @PathParam("id") int id) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.removeUser(id);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Endpoint to update a user
     * @param authHeader the header containing the authentication token
     * @param id the user id
     * @param email the user email
     * @param password the user password
     * @return Response status
     */
    @PUT
    @Consumes("application/x-www-form-urlencoded")
    public Response updateUser(@HeaderParam("Authorization") String authHeader, @FormParam("id") int id, @FormParam("email") String email, @FormParam("password") String password) {
        if (!isValidToken(authHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String result = service.updateUserJSON(id, email, password);

        if(result.equals(Errors.RESOURCE_NOT_EXISTS.getDescription())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(result.equals(Errors.INTERNAL_ERROR.getDescription())) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(result).build();
    }
}
