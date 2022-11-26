package dao;

import junit.framework.TestCase;
import object.Flight;

public class InsertObjectsManagerTest extends TestCase {

    public void testAddObjectAbleToRegister() {
        Flight flight=new Flight("F1",1005,80,"西安","成都");
        InsertObjectsManager insertObjectsManager=new InsertObjectsManager();
        insertObjectsManager.addObjectAbleToRegister(flight);
    }
}