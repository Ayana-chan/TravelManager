package dao.expection;

public class HaveRegisteredException extends FailToDoneSqlException {
    public HaveRegisteredException(){
        super("This Object Has Been Registered.");
    }
}
