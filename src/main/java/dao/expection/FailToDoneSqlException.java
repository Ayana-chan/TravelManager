package dao.expection;

public class FailToDoneSqlException extends RuntimeException{
    public FailToDoneSqlException(){
        super("Fail To Done Sql.");
    }
    public FailToDoneSqlException(Exception e){
        super(e);
    }
    public FailToDoneSqlException(String s){
        super(s);
    }
}
