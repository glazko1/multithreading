package by.epam.javaweb.glazko.multithreading.exception;

public class WrongActionException extends Throwable {

    static final long serialVersionUID = 2157873107251876376L;

    public WrongActionException() {
    }

    public WrongActionException(String message) {
        super(message);
    }

    public WrongActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongActionException(Throwable cause) {
        super(cause);
    }

    public WrongActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
