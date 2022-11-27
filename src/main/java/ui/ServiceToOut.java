package ui;

import dao.*;
import object.Customer;
import object.Flight;

import java.util.ArrayList;
import java.util.Scanner;

public class ServiceToOut {
    public void service(){
        printWelcomeMenu();

        Scanner sc=new Scanner(System.in);
        //dao's managers
        BusManager busManager=new BusManager();
        CustomerManager customerManager=new CustomerManager();
        FlightManager flightManager=new FlightManager();
        HotelManager hotelManager=new HotelManager();
        ReservationManager reservationManager=new ReservationManager();

        while(true){
            System.out.println("\n");
            printMainMenu();
            //input
            String choice0=sc.nextLine();
            System.out.println("");

            switch (choice0){
                case "1":
                    ArrayList<Flight> flights=flightManager.searchAllFlight();
                    System.out.println("Result:");
                    for(Flight f:flights){
                        System.out.println(f);
                    }
                    break;

            }
            //一次事务结束
            System.out.print("\nInput Any Character To Continue:");
            sc.nextLine();
        }
    }

    private void printWelcomeMenu(){
        System.out.println("" +
                "TravelManager\n" +
                "Produced by DeathWind");
    }

    private void printMainMenu(){
        System.out.print("" +
                "* Main Menu *\n" +
                "---\n" +
                "1: Search Flights\n" +
                "2: Add New Flight\n" +
                "3: Modify Flight\n" +
                "---\n" +
                "4: Search Buses\n" +
                "5: Add New Bus\n" +
                "6: Modify Bus\n" +
                "---\n" +
                "7: Search Hotels\n" +
                "8: Add New Hotel\n" +
                "9: Modify Hotel\n" +
                "---\n" +
                "10: Search Customers\n" +
                "11: Add New Customer\n" +
                "12: Modify Customer\n" +
                "---\n" +
                "13: Search Reservation\n" +
                "14: Add Reservation\n" +
                "15: Search Own Journey\n" +
                "---\n" +
                "16: Check Accessible\n" +
                "17: QUIT\n" +
                "\n" +
                "Input Your Choice: ");
    }
}
