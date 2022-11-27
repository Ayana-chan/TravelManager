package dao;

import junit.framework.TestCase;
import object.Reservation;

public class ReservationManagerTest extends TestCase {

    public void testAddReservation() {
        Reservation reservation=new Reservation("李田所",1,"F1");
        ReservationManager reservationManager=new ReservationManager();
        for(int i=0;i<3;i++) {
            reservationManager.addReservation(reservation);
        }
    }
}