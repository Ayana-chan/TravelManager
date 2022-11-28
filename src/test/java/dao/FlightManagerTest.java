package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import junit.framework.TestCase;
import object.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightManagerTest extends TestCase {

    public void testRegisterFlight() {
        Flight flight1=new Flight("F1",3500,80,"西安","成都");
        Flight flight2=new Flight("F2",4000,90,"成都","南京");
        Flight flight3=new Flight("F3",4000,90,"南京","杭州");
        Flight flight4=new Flight("F4",4000,90,"成都","西安");
        Flight flight5=new Flight("F5",3500,80,"西安","石家庄");
        Flight flight6=new Flight("F6",3500,80,"西安","成都");
        FlightManager flightManager=new FlightManager();
        try {
            flightManager.registerFlight(flight1);
        }catch (HaveRegisteredException ignored){}
        try {
            flightManager.registerFlight(flight2);
        }catch (HaveRegisteredException ignored){}
        try {
            flightManager.registerFlight(flight3);
        }catch (HaveRegisteredException ignored){}
        try {
            flightManager.registerFlight(flight4);
        }catch (HaveRegisteredException ignored){}
        try {
            flightManager.registerFlight(flight5);
        }catch (HaveRegisteredException ignored){}
        try {
            flightManager.registerFlight(flight6);
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
        Flight flight3=new Flight("F3",4000,90,"北京","杭州");
        FlightManager flightManager=new FlightManager();
        try {
            flightManager.modifyFlight(flight1);
            flightManager.modifyFlight(flight2);
            flightManager.modifyFlight(flight3);
        }catch (InsufficientSpaceException e){
            System.out.println(e.getMessage());
        }
    }

    public void testJudgeAccessibleBFS() {
        FlightManager flightManager=new FlightManager();
        List<String> b = flightManager.judgeAccessibleBFS("西安","杭州");
        System.out.println("西安to杭州： \n"+b);
    }
}