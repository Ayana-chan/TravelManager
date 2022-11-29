package dao;

import dao.expection.InsufficientSpaceException;
import javafx.util.Pair;
import junit.framework.TestCase;
import object.Reservation;

import java.util.ArrayList;
import java.util.Map;

public class ReservationManagerTest extends TestCase {

    public void testAddReservation() {
        Reservation reservation=new Reservation("李田所",1,"F1");
        ReservationManager reservationManager=new ReservationManager();
        for(int i=0;i<3;i++) {
            try {
                reservationManager.addReservation(reservation);
            }catch (InsufficientSpaceException e){
                System.out.println("Failed. Full.");
            }
        }
    }

    public void testSearchOwnReservation() {
        ReservationManager reservationManager=new ReservationManager();
        Map<String, Pair<String, String>> journeys=reservationManager.searchOwnJourney("李田所");
        System.out.println("SearchOwnReservation:");
        for(String j:journeys.keySet()){
            System.out.println(j+" : "+journeys.get(j));
        }
    }
}