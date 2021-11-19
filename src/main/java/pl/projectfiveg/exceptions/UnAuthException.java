package pl.projectfiveg.exceptions;

public class UnAuthException extends Exception {

    public UnAuthException(String message) {
        super(message);
    }

    public UnAuthException() {
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "UnAuthException: auth failed";
    }

}
