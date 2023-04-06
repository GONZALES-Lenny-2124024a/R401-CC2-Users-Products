package fr.univamu.iut.users_products.domain;

/**
 * Class representing a Product
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String unit;
    private int quantityAvailable;

    /**
     * Constructor with id
     * @param id the product's id
     * @param name the product's name
     * @param description the product's description
     * @param price the product's price
     * @param unit the product's unit
     * @param quantity the product's quantity
     * @param quantityAvailable the product's quantityAvailable
     */
    public Product(int id, String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        this(name,description,price,unit,quantity,quantityAvailable);
        this.id = id;
    }

    /**
     * Constructor with id
     * @param name the product's name
     * @param description the product's description
     * @param price the product's price
     * @param unit the product's unit
     * @param quantity the product's quantity
     * @param quantityAvailable the product's quantityAvailable
     */
    public Product(String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.quantityAvailable = quantityAvailable;
    }

    /**
     * Get the product id
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the product id
     * @param id the new product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the product name
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product name
     * @param name the new product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the product description
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the product description
     * @param description the new product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the product price
     * @return the product price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the product price
     * @param price the new product price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Get the product unit
     * @return the product unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the product unit
     * @param unit the new product unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Get the product quantity
     * @return the product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the product quantity
     * @param quantity the new product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the product quantityAvailable
     * @return the product quantityAvailable
     */
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    /**
     * Set the product quantityAvailable
     * @param quantityAvailable the new product quantityAvailable
     */
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
