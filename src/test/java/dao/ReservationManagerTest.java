package dao;

import javafx.util.Pair;
import junit.framework.TestCase;
import object.Reservation;

import java.util.ArrayList;

public class ReservationManagerTest extends TestCase {

    public void testAddReservation() {
        Reservation reservation=new Reservation("李田所",1,"F1");
        ReservationManager reservationManager=new ReservationManager();
        for(int i=0;i<3;i++) {
            reservationManager.addReservation(reservation);
        }
    }

    public void testSearchOwnReservation() {
        ReservationManager reservationManager=new ReservationManager();
        ArrayList<Pair<String,String>> journeys=reservationManager.searchOwnReservation("李田所");
        System.out.println("SearchOwnReservation:");
        for(Pair<String,String> j:journeys){
            System.out.println(j);
        }
    }
}