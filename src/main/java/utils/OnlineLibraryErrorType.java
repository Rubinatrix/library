package utils;

public enum OnlineLibraryErrorType {

    USER_EXISTS("An account for that username already exists. Please enter a different username!"),
    USERNAME_NOT_ALLOWED("This name isn't allowed. Please enter a different username!"),
    LACK_OF_CONTENT("Book content isn't available yet!"),
    OPERATION_NOT_ALLOWED("This operation isn't allowed!");

    private String name;

    OnlineLibraryErrorType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
