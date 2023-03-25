package fr.univamu.iut.users_products.domain;

public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String unit;
    private int quantityAvailable;

    public Product(int id, String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        this(name,description,price,unit,quantity,quantityAvailable);
        this.id = id;
    }

    public Product(String name, String description, float price, String unit, int quantity, int quantityAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.quantityAvailable = quantityAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
