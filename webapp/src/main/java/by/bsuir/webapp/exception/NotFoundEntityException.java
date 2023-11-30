package by.bsuir.webapp.exception;

public class NotFoundEntityException extends Exception{

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEntityException(Throwable cause) {
        super(cause);
    }
}
