package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import junit.framework.TestCase;
import object.Flight;

import java.util.ArrayList;

public class FlightManagerTest extends TestCase {

    public void testRegisterFlight() {
        Flight flight1=new Flight("F1",3500,80,"西安","成都");
        Flight flight2=new Flight("F2",4000,90,"成都","南京");
        FlightManager flightManager=new FlightManager();
        try {
            flightManager.registerFlight(flight1);
            flightManager.registerFlight(flight2);
        }catch (HaveRegisteredException ignored){}
    }

    public void testSearchAllFlight() {
        FlightManager flightManager=new FlightManager();
        ArrayList<Flight> flights = flightManager.searchAllFlight();
        System.out.println("SearchAllFlight:");
        for (Flight f:flights) {
            System.out.println(f);
        }
    }

    public void testModifyFlight() {
        Flight flight1=new Flight("F1",4500,20,"西安","石家庄");
        Flight flight2=new Flight("F2",2000,30,"成都","南京");
        FlightManager flightManager=new FlightManager();
        try {
            flightManager.modifyFlight(flight1);
            flightManager.modifyFlight(flight2);
        }catch (InsufficientSpaceException e){
            System.out.println(e.getMessage());
        }
    }
}