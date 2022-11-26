package object;

public class Reservation{
    private int resvKey;
    private String custName;
    private int resvType;
    private String resvObject;

    //插入
    public Reservation(String custName, int resvType, String resvObject) {
        this.custName = custName;
        this.resvType = resvType;
        this.resvObject = resvObject;
    }
    //读取
    public Reservation(int resvKey, String custName, int resvType, String resvObject) {
        this.resvKey = resvKey;
        this.custName = custName;
        this.resvType = resvType;
        this.resvObject = resvObject;
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "resvKey=" + resvKey +
                ", custName='" + custName + '\'' +
                ", resvType='" + resvType + '\'' +
                ", resvObject='" + resvObject + '\'' +
                '}';
    }

    //get

    public int getResvKey() {
        return resvKey;
    }

    public String getCustName() {
        return custName;
    }

    public int getResvType() {
        return resvType;
    }

    public String getResvObject() {
        return resvObject;
    }

    //set

    public void setResvKey(int resvKey) {
        this.resvKey = resvKey;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setResvType(int resvType) {
        this.resvType = resvType;
    }

    public void setResvObject(String resvObject) {
        this.resvObject = resvObject;
    }
}
