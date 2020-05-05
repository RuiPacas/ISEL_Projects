package pt.isel.ls.exception;


/**
 * exception used when a user enters an invalid Method in a command
 */

public class NoSuchMethodException extends RuntimeException {

    public NoSuchMethodException() {
        super();
    }

    public NoSuchMethodException(String message) {
        super(message);
    }
}
