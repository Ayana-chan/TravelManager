package dao.expection;

public class InsufficientSpaceException extends RuntimeException{
    public InsufficientSpaceException() {
        super("Too Few Available Place");
    }
}
