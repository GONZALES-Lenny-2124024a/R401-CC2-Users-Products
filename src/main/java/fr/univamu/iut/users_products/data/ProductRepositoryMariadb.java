package fr.univamu.iut.users_products.data;

import fr.univamu.iut.users_products.domain.Product;
import fr.univamu.iut.users_products.service.ProductRepositoryInterface;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accèder aux utilisateurs stockés dans une base de données Mariadb
 */
public class ProductRepositoryMariadb implements ProductRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     *
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user           chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd            chaîne de caractères contenant le mot de passe à utiliser
     */
    public ProductRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les utilisateurs
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method which return all the products
     * @return a list with all the products
     */
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> listProducts;

        String query = "SELECT * FROM Products";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listProducts = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                float price = result.getFloat("price");
                String unit = result.getString("unit");
                int quantity = result.getInt("quantity");


                // création du livre courant
                Product product = new Product(id, name, description, price, unit, quantity);

                listProducts.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProducts;
    }

    /**
     * Method which return a Product by its name
     * @param id the product's id
     * @return the Product corresponding to this name
     */
    public Product getProductById(int id) {
        String query = "SELECT * FROM Products WHERE id=?";
        Product product = null;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            if( result.next() ) {
                String name = result.getString("name");
                String description = result.getString("description");
                float price = result.getFloat("price");
                String unit = result.getString("unit");
                int quantity = result.getInt("quantity");

                product = new Product(id, name, description,price, unit, quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Method which update a product
     * @param product the new product object
     * @return if the update worked or not
     */
    public boolean updateProduct(Product product) {
        String query = "UPDATE Products SET name=?, description=?, price=?, unit=?, quantity=? WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getPrice());
            ps.setString(4, product.getUnit());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6,product.getId());

            int result = ps.executeUpdate();
            return (result > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
