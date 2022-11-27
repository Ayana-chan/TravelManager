package dao.expection;

public class TargetNotFoundException extends RuntimeException{
    public TargetNotFoundException(){
        super("Can't Find Specified Target");
    }
}
