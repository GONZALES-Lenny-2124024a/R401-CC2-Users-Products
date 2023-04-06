package fr.univamu.iut.users_products;

import fr.univamu.iut.users_products.control.UserResource;
import fr.univamu.iut.users_products.data.UserRepositoryMariadb;
import fr.univamu.iut.users_products.service.UserRepositoryInterface;
import fr.univamu.iut.users_products.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class UserApplication  extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        // Création de la connection à la base de données et initialisation du service associé
        UserService service = null ;
        try{
            UserRepositoryMariadb db = new UserRepositoryMariadb("jdbc:mariadb://mysql-gonzalesl.alwaysdata.net/gonzalesl_cc2", "gonzalesl_cc2", "e9rXXKmTfcQb3kV");
            service = new UserService(db);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

        // Création de la ressource en lui passant paramètre les services à exécuter en fonction
        // des différents endpoints proposés (i.e. requêtes HTTP acceptées)
        set.add(new UserResource(service));

        return set;
    }

    /**
     * Method called by the CDI API to inject the connection to the database at resource creation time
     * @return an object implementing the UserRepositoryInterface used to access or modify product data
     */
    @Produces
    private UserRepositoryInterface openDbConnection(){
        UserRepositoryMariadb db = null;

        try{
            db = new UserRepositoryMariadb("jdbc:mariadb://mysql-gonzalesl.alwaysdata.net/gonzalesl_cc2", "gonzalesl_cc2", "e9rXXKmTfcQb3kV");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Method to close the database connection when the application is stopped
     * @param userRepo the database connection instantiated in the @openDbConnection method
     */
    private void closeDbConnection(@Disposes UserRepositoryInterface userRepo ) {
        userRepo.close();
    }
}
