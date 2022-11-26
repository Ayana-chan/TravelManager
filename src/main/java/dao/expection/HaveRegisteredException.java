package dao.expection;

public class HaveRegisteredException extends RuntimeException {
    public HaveRegisteredException(){
        super("This Object Has Been Registered.");
    }
}
