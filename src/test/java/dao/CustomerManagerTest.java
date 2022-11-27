package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import junit.framework.TestCase;
import object.Customer;
import object.Flight;

import java.util.ArrayList;

public class CustomerManagerTest extends TestCase {

    public void testRegisterCustomer() {
        Customer customer1=new Customer("丁真","777888");
        Customer customer2=new Customer("李田所","1919810");
        CustomerManager customerManager=new CustomerManager();
        try {
            customerManager.registerCustomer(customer1);
        }catch (HaveRegisteredException ignored){}
        try {
            customerManager.registerCustomer(customer2);
        }catch (HaveRegisteredException ignored){}
    }

    public void testSearchAllCustomer() {
        CustomerManager customerManager=new CustomerManager();
        ArrayList<Customer> customers = customerManager.searchAllCustomer();
        System.out.println("SearchAllCustomer:");
        for (Customer c:customers) {
            System.out.println(c);
        }
    }

    public void testModifyCustomer() {
        Customer customer1=new Customer("丁真","111555");
        Customer customer2=new Customer("李田所","114514");
        CustomerManager customerManager=new CustomerManager();
        try {
            customerManager.modifyCustomer(customer1);
            customerManager.modifyCustomer(customer2);
        }catch (InsufficientSpaceException e){
            System.out.println(e.getMessage());
        }
    }
}