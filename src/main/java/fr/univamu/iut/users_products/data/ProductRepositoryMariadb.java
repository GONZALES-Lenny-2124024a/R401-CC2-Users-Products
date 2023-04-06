package fr.univamu.iut.users_products.data;

import fr.univamu.iut.users_products.domain.Product;
import fr.univamu.iut.users_products.service.ProductRepositoryInterface;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class for accessing products stored in a Mariadb database
 */
public class ProductRepositoryMariadb implements ProductRepositoryInterface, Closeable {

    /**
     * Access to the database
     */
    protected Connection dbConnection;

    /**
     * Constructor
     *
     * @param infoConnection string with login information
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user           string containing the database login
     * @param pwd            character string containing the database password
     */
    public ProductRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Method closing the repository where product information is stored
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

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listProducts = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                float price = result.getFloat("price");
                String unit = result.getString("unit");
                int quantity = result.getInt("quantity");
                int quantityAvailable = result.getInt("quantityAvailable");


                Product product = new Product(id, name, description, price, unit, quantity, quantityAvailable);

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
                int quantityAvailable = result.getInt("quantityAvailable");

                product = new Product(id, name, description,price, unit, quantity,quantityAvailable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Method which return a Product by a Product object
     * @param product Product object
     * @return a Product object
     */
    public Product getProduct(Product product) {
        String query = "SELECT * FROM Products WHERE name=? AND description=? AND price=? AND unit=? AND quantity=? AND quantityAvailable=?";
        Product productSelected = null;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getPrice());
            ps.setString(4, product.getUnit());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getQuantityAvailable());

            ResultSet result = ps.executeQuery();
            if( result.next() ) {
                int id = result.getInt("id");

                productSelected = new Product(id, product.getName(), product.getDescription(), product.getPrice(), product.getUnit(), product.getQuantity(), product.getQuantityAvailable());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productSelected;
    }


    /**
     * Method which update a product
     * @param product the new product object
     * @return if the update worked or not
     */
    public boolean updateProduct(Product product) {
        String query = "UPDATE Products SET name=?, description=?, price=?, quantity=?, unit=?, quantityAvailable=? WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getUnit());
            ps.setInt(6, product.getQuantityAvailable());
            ps.setInt(7,product.getId());

            int result = ps.executeUpdate();
            return (result > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method which create a product
     * @param product the product to create
     * @return if the creation worked
     */
    public boolean createProduct(Product product) {
        String query = "INSERT INTO Products (name, description, price, quantity, unit, quantityAvailable) VALUES (?,?,?,?,?,?)";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getUnit());
            ps.setInt(6, product.getQuantityAvailable());

            int result = ps.executeUpdate();
            return (result > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method which remove a product
     * @param id the product's id
     * @return if the deletion worked or not
     */
    public boolean removeProduct(int id) {
        String query = "DELETE FROM Products WHERE ID=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
