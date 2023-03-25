package fr.univamu.iut.users_products.Constants;

public enum Errors {
    // Generic errors
            ALREADY_EXISTS("The resource already exists"),
            INTERNAL_ERROR("Internal error when doing the request"),
            RESOURCE_NOT_EXISTS("The resource wanted doesn't exists"),

    // Products errors
            NOT_ENOUGH_QUANTITY("There is no enough quantity");


    private String description;

    Errors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
