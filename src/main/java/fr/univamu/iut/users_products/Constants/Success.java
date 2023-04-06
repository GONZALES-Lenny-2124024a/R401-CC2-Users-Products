package fr.univamu.iut.users_products.Constants;

/**
 * Representing all the success with a detailed description
 */
public enum Success {
    // Generic success
            DONE_SUCCESSFULLY("The request was done successfully");

    private String description;
    Success(String description) {
        this.description = description;
    }

    /**
     * Get the description of the success
     * @return the description of the success
     */
    public String getDescription() {
        return this.description;
    }
}
