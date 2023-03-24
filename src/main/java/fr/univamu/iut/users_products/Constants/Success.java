package fr.univamu.iut.users_products.Constants;

public enum Success {
    // Generic success
            DONE_SUCCESSFULLY("The request was done successfully");

    private String description;
    Success(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
