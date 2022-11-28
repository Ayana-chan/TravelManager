package ui;

import dao.*;
import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Bus;
import object.Customer;
import object.Flight;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ServiceToOut {
    public void service(){

        Scanner sc=new Scanner(System.in);
        //dao's managers
        BusManager busManager=new BusManager();
        CustomerManager customerManager=new CustomerManager();
        FlightManager flightManager=new FlightManager();
        HotelManager hotelManager=new HotelManager();
        ReservationManager reservationManager=new ReservationManager();

        String instr;

        while(true){
            printMainMenu();
            //input
            String choice0=sc.nextLine();
            System.out.println("");

            if(choice0=="1") {
                ArrayList<Flight> flights = flightManager.searchAllFlight();
                System.out.println("Result:");
                for (Flight f : flights) {
                    System.out.println(f);
                }
            }

            else if(choice0=="2") {
                System.out.println("Input Arguments");
                System.out.print("FlightNum: ");
                String flightNum = sc.nextLine();
                System.out.print("Price: ");
                int price = Integer.parseInt(sc.nextLine());
                System.out.print("NumSeats: ");
                int numSeats = Integer.parseInt(sc.nextLine());
                System.out.print("FromCity: ");
                String fromCity = sc.nextLine();
                System.out.print("ArivCity: ");
                String arivCity = sc.nextLine();

                Flight flight = new Flight(flightNum, price, numSeats, fromCity, arivCity);
                try {
                    flightManager.registerFlight(flight);
                } catch (HaveRegisteredException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
                System.out.println("Success.");
            }

            else if (choice0=="3") {
                System.out.println("Choose Aim");
                System.out.print("FlightNum: ");
                String flightNum = sc.nextLine();
                try {
                    Flight flight = flightManager.searchFlight(flightNum);
                    System.out.println("Native Attribute: " + flight);
                    System.out.println("Input Arguments (Input \"#\" to ignore certain argument)");
                    System.out.print("Price: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        flight.setPrice(Integer.parseInt(instr));
                    }
                    System.out.print("NumSeats: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        flight.setNumSeats(Integer.parseInt(instr));
                    }
                    System.out.print("FromCity: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        flight.setFromCity(instr);
                    }
                    System.out.print("ArivCity: ");
                    if (!Objects.equals(instr, "#")) {
                        flight.setArivCity(instr);
                    }

                    flightManager.modifyFlight(flight);
                    System.out.println("Success.");
                } catch (TargetNotFoundException | InsufficientSpaceException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
            }

            else if(choice0=="4") {
                ArrayList<Bus> buses = busManager.searchAllBus();
                System.out.println("Result:");
                for (Bus f : buses) {
                    System.out.println(f);
                }
            }

            else if(choice0=="5") {
                System.out.println("Input Arguments");
                System.out.print("Location: ");
                String flightNum = sc.nextLine();
                System.out.print("Price: ");
                int price = Integer.parseInt(sc.nextLine());
                System.out.print("NumBus: ");
                int numSeats = Integer.parseInt(sc.nextLine());

                Bus bus = new Bus(flightNum, price, numSeats);
                try {
                    busManager.registerBus(bus);
                } catch (HaveRegisteredException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
                System.out.println("Success.");
            }

            else if (choice0=="6") {
                System.out.println("Choose Aim");
                System.out.print("Location: ");
                String flightNum = sc.nextLine();
                try {
                    Bus bus = busManager.searchBus(flightNum);
                    System.out.println("Native Attribute: " + bus);
                    System.out.println("Input Arguments (Input \"#\" to ignore certain argument)");
                    System.out.print("Price: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        bus.setPrice(Integer.parseInt(instr));
                    }
                    System.out.print("NumBus: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        bus.setNumBus(Integer.parseInt(instr));
                    }

                    busManager.modifyBus(bus);
                    System.out.println("Success.");
                } catch (TargetNotFoundException | InsufficientSpaceException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
            }




            //一次事务结束
            System.out.print("\nInput Any Character To Continue:");
            sc.nextLine();
        }
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
