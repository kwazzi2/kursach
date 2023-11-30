package by.bsuir.webapp.service.security.exception;

public class NonUniqueUsernameException extends Exception {
    public NonUniqueUsernameException() {
    }

    public NonUniqueUsernameException(String message) {
        super(message);
    }
}
