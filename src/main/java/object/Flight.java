package object;

public class Flight implements ObjectAbleToRegister{
    private String flightNum;
    private int price;
    private int numSeats;
    private int numAvail;
    private String fromCity;
    private String arivCity;

    //插入
    public Flight(String flightNum, int price, int numSeats, String fromCity, String arivCity) {
        this.flightNum = flightNum;
        this.price = price;
        this.numSeats = numSeats;
        this.fromCity = fromCity;
        this.arivCity = arivCity;
    }
    //读取
    public Flight(String flightNum, int price, int numSeats, int numAvail, String fromCity, String arivCity) {
        this.flightNum = flightNum;
        this.price = price;
        this.numSeats = numSeats;
        this.numAvail = numAvail;
        this.fromCity = fromCity;
        this.arivCity = arivCity;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNum='" + flightNum + '\'' +
                ", price=" + price +
                ", numSeats=" + numSeats +
                ", numAvail=" + numAvail +
                ", fromCity='" + fromCity + '\'' +
                ", arivCity='" + arivCity + '\'' +
                '}';
    }

    //get

    public String getFlightNum() {
        return flightNum;
    }

    public int getPrice() {
        return price;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumAvail() {
        return numAvail;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getArivCity() {
        return arivCity;
    }

    //set

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public void setNumAvail(int numAvail) {
        this.numAvail = numAvail;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setArivCity(String arivCity) {
        this.arivCity = arivCity;
    }
}
