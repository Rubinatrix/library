package utils;

public class OnlineLibraryException extends Exception{

    public OnlineLibraryException(String name){
        super(name);
    }

    public OnlineLibraryException(OnlineLibraryErrorType errorType){
        super(errorType.getName());
    }

}
