package object;

public class Bus implements ObjectAbleToRegister{
    private String location;
    private int price;
    private int numBus;
    private int numAvail;

    //插入
    public Bus(String location, int price, int numBus) {
        this.location = location;
        this.price = price;
        this.numBus = numBus;
        this.numAvail = numBus;
    }
    //读取
    public Bus(String location, int price, int numBus, int numAvail) {
        this.location = location;
        this.price = price;
        this.numBus = numBus;
        this.numAvail = numAvail;
    }



    @Override
    public String toString() {
        return "Bus{" +
                "location='" + location + '\'' +
                ", price=" + price +
                ", numBus=" + numBus +
                ", numAvail=" + numAvail +
                '}';
    }

    //get

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public int getNumBus() {
        return numBus;
    }

    public int getNumAvail() {
        return numAvail;
    }

    //set

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNumBus(int numBus) {
        this.numBus = numBus;
    }

    public void setNumAvail(int numAvail) {
        this.numAvail = numAvail;
    }
}
