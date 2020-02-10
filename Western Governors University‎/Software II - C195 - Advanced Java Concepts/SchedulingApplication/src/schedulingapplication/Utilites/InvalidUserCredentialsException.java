package schedulingapplication.Utilites;

public class InvalidUserCredentialsException extends Exception {
    
    public InvalidUserCredentialsException() {
        super();
    }

    public InvalidUserCredentialsException(Exception e) {
        super(e);
    }

    public InvalidUserCredentialsException(String message) {
        super(message);
    }

}
