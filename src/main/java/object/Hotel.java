package object;

public class Hotel implements ObjectAbleToRegister{
    private String location;
    private int price;
    private int numRooms;
    private int numAvail;

    //插入
    public Hotel(String location, int price, int numRooms) {
        this.location = location;
        this.price = price;
        this.numRooms = numRooms;
    }
    //读取
    public Hotel(String location, int price, int numRooms, int numAvail) {
        this.location = location;
        this.price = price;
        this.numRooms = numRooms;
        this.numAvail = numAvail;
    }



    @Override
    public String toString() {
        return "Hotel{" +
                "location='" + location + '\'' +
                ", price=" + price +
                ", numRooms=" + numRooms +
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

    public int getNumRooms() {
        return numRooms;
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

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void setNumAvail(int numAvail) {
        this.numAvail = numAvail;
    }
}
