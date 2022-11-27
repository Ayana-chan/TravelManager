package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import junit.framework.TestCase;
import object.Bus;
import object.Flight;

import java.util.ArrayList;

public class BusManagerTest extends TestCase {

    public void testRegisterBus() {
        Bus bus1=new Bus("成都",20,230);
        Bus bus2=new Bus("石家庄",15,400);
        BusManager busManager=new BusManager();
        try {
            busManager.registerBus(bus1);
        }catch (HaveRegisteredException ignored){}
        try {
            busManager.registerBus(bus2);
        }catch (HaveRegisteredException ignored){}
    }

    public void testSearchAllBus() {
        BusManager busManager=new BusManager();
        ArrayList<Bus> buses = busManager.searchAllBus();
        System.out.println("SearchAllBus:");
        for (Bus b:buses) {
            System.out.println(b);
        }
    }

    public void testModifyBus() {
        Bus bus1=new Bus("成都",2,130);
        Bus bus2=new Bus("石家庄",5,400);
        BusManager busManager=new BusManager();
        try {
            busManager.modifyBus(bus1);
            busManager.modifyBus(bus2);
        }catch (InsufficientSpaceException e){
            System.out.println(e.getMessage());
        }
    }
}