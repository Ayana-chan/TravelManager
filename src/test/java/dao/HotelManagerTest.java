package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import junit.framework.TestCase;
import object.Bus;
import object.Hotel;

import java.util.ArrayList;

public class HotelManagerTest extends TestCase {

    public void testRegisterHotel() {
        Hotel hotel1=new Hotel("成都",200,120);
        Hotel hotel2=new Hotel("石家庄",300,270);
        HotelManager hotelManager=new HotelManager();
        try {
            hotelManager.registerHotel(hotel1);
        }catch (HaveRegisteredException ignored){}
        try {
            hotelManager.registerHotel(hotel2);
        }catch (HaveRegisteredException ignored){}
    }

    public void testSearchAllHotel() {
        HotelManager hotelManager=new HotelManager();
        ArrayList<Hotel> hotels = hotelManager.searchAllHotel();
        System.out.println("SearchAllHotel:");
        for (Hotel h:hotels) {
            System.out.println(h);
        }
    }

    public void testModifyHotel() {
        Hotel hotel1=new Hotel("成都",250,20);
        Hotel hotel2=new Hotel("石家庄",350,270);
        HotelManager hotelManager=new HotelManager();
        try {
            hotelManager.modifyHotel(hotel1);
            hotelManager.modifyHotel(hotel2);
        }catch (InsufficientSpaceException e){
            System.out.println(e.getMessage());
        }
    }
}