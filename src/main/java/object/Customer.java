package object;

public class Customer implements ObjectAbleToRegister{
    private String custName;
    private String custID;

    public Customer(String custName, String custID) {
        this.custName = custName;
        this.custID = custID;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "custName='" + custName + '\'' +
                ", custID='" + custID + '\'' +
                '}';
    }

    //get

    public String getCustName() {
        return custName;
    }

    public String getCustID() {
        return custID;
    }

    //set

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }
}
