/**
 * Custom exception thrown when parameters provided to counting logic are invalid.
 */
public class ParametrosInvalidosException extends Exception {

    public ParametrosInvalidosException(String message) {
        super(message);
    }

    public ParametrosInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }
}
