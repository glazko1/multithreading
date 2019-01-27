package by.epam.javaweb.glazko.multithreading.exception;

public class FileReadingException extends Throwable {

    static final long serialVersionUID = 2354653126886513536L;

    public FileReadingException() {
    }

    public FileReadingException(String message) {
        super(message);
    }

    public FileReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileReadingException(Throwable cause) {
        super(cause);
    }

    public FileReadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
