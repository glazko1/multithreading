package by.epam.javaweb.glazko.multithreading.exception;

public class FileWritingException extends Throwable {

    static final long serialVersionUID = 985672653752215794L;

    public FileWritingException() {
    }

    public FileWritingException(String message) {
        super(message);
    }

    public FileWritingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileWritingException(Throwable cause) {
        super(cause);
    }

    public FileWritingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
