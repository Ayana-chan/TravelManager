package dao;

import dao.expection.HaveRegisteredException;
import junit.framework.TestCase;
import object.Bus;
import object.Customer;
import object.Flight;
import object.Hotel;

public class InsertObjectsManagerTest extends TestCase {

    public void testAddObjectAbleToRegister() {
        Flight flight=new Flight("F1",1005,80,"西安","成都");
        Bus bus=new Bus("成都",25,200);
        Hotel hotel=new Hotel("西安",300,170);
        Customer customer=new Customer("李田所","114514");
        InsertObjectsManager insertObjectsManager=new InsertObjectsManager();
        try {
            insertObjectsManager.addObjectAbleToRegister(flight);
        }catch (HaveRegisteredException e){
            System.out.println(e.getMessage());
        }
        try {
            insertObjectsManager.addObjectAbleToRegister(bus);
        }catch (HaveRegisteredException e){
            System.out.println(e.getMessage());
        }
        try {
            insertObjectsManager.addObjectAbleToRegister(hotel);
        }catch (HaveRegisteredException e){
            System.out.println(e.getMessage());
        }
        try {
            insertObjectsManager.addObjectAbleToRegister(customer);
        }catch (HaveRegisteredException e){
            System.out.println(e.getMessage());
        }
    }
}