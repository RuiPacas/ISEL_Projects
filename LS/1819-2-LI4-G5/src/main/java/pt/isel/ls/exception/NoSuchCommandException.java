package pt.isel.ls.exception;

/**
 * exception used when a user enters an invalid command
 */
public class NoSuchCommandException extends RuntimeException {

    public NoSuchCommandException() {
        super();
    }

    public NoSuchCommandException(String message) {
        super(message);
    }
}
