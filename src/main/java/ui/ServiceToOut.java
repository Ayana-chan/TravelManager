package ui;

import dao.*;
import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import javafx.util.Pair;
import object.*;

import java.util.ArrayList;
import java.util.List;
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

            if(Objects.equals(choice0, "1")) {
                ArrayList<Flight> flights = flightManager.searchAllFlight();
                System.out.println("Result:");
                for (Flight f : flights) {
                    System.out.println(f);
                }
            }

            else if(Objects.equals(choice0, "2")) {
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

            else if (Objects.equals(choice0, "3")) {
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
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        flight.setArivCity(instr);
                    }

                    flightManager.modifyFlight(flight);
                    System.out.println("Success.");
                } catch (TargetNotFoundException | InsufficientSpaceException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
            }

            //-----------------------------

            else if(Objects.equals(choice0, "4")) {
                ArrayList<Bus> buses = busManager.searchAllBus();
                System.out.println("Result:");
                for (Bus f : buses) {
                    System.out.println(f);
                }
            }

            else if(Objects.equals(choice0, "5")) {
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

            else if (Objects.equals(choice0, "6")) {
                System.out.println("Choose Aim");
                System.out.print("Location: ");
                String location = sc.nextLine();
                try {
                    Bus bus = busManager.searchBus(location);
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

            //------------------------------------------

            else if(Objects.equals(choice0, "7")) {
                ArrayList<Hotel> hotels = hotelManager.searchAllHotel();
                System.out.println("Result:");
                for (Hotel f : hotels) {
                    System.out.println(f);
                }
            }

            else if(Objects.equals(choice0, "8")) {
                System.out.println("Input Arguments");
                System.out.print("Location: ");
                String flightNum = sc.nextLine();
                System.out.print("Price: ");
                int price = Integer.parseInt(sc.nextLine());
                System.out.print("NumRooms: ");
                int numRooms = Integer.parseInt(sc.nextLine());

                Hotel hotel = new Hotel(flightNum, price, numRooms);
                try {
                    hotelManager.registerHotel(hotel);
                } catch (HaveRegisteredException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
                System.out.println("Success.");
            }

            else if (Objects.equals(choice0, "9")) {
                System.out.println("Choose Aim");
                System.out.print("Location: ");
                String location = sc.nextLine();
                try {
                    Hotel hotel = hotelManager.searchHotel(location);
                    System.out.println("Native Attribute: " + hotel);
                    System.out.println("Input Arguments (Input \"#\" to ignore certain argument)");
                    System.out.print("Price: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        hotel.setPrice(Integer.parseInt(instr));
                    }
                    System.out.print("NumRooms: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        hotel.setNumRooms(Integer.parseInt(instr));
                    }

                    hotelManager.modifyHotel(hotel);
                    System.out.println("Success.");
                } catch (TargetNotFoundException | InsufficientSpaceException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
            }

            //--------------------------------------------------------------------

            else if(Objects.equals(choice0, "10")) {
                ArrayList<Customer> customers = customerManager.searchAllCustomer();
                System.out.println("Result:");
                for (Customer f : customers) {
                    System.out.println(f);
                }
            }

            else if(Objects.equals(choice0, "11")) {
                System.out.println("Input Arguments");
                System.out.print("CustName: ");
                String custName = sc.nextLine();
                System.out.print("CustID: ");
                String custID = sc.nextLine();

                Customer customer = new Customer(custName,custID);
                try {
                    customerManager.registerCustomer(customer);
                } catch (HaveRegisteredException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
                System.out.println("Success.");
            }

            else if (Objects.equals(choice0, "12")) {
                System.out.println("Choose Aim");
                System.out.print("CustName: ");
                String custName = sc.nextLine();
                try {
                    Customer customer = customerManager.searchCustomer(custName);
                    System.out.println("Native Attribute: " + customer);
                    System.out.println("Input Arguments (Input \"#\" to ignore certain argument)");
                    System.out.print("CustID: ");
                    instr = sc.nextLine();
                    if (!Objects.equals(instr, "#")) {
                        customer.setCustID(instr);
                    }

                    customerManager.modifyCustomer(customer);
                    System.out.println("Success.");
                } catch (TargetNotFoundException | InsufficientSpaceException e) {
                    System.out.println("Failed. " + e.getMessage());
                }
            }

            //------------------------------------------------------------------

            else if(Objects.equals(choice0, "13")){
                ArrayList<Reservation> reservations = reservationManager.searchAllReservation();
                System.out.println("Result:");
                for (Reservation f : reservations) {
                    System.out.println(f);
                }
            }

            else if(Objects.equals(choice0, "14")){
                System.out.println("Input Arguments");
                System.out.print("CustName: ");
                String custName = sc.nextLine();
                System.out.print("ResvType: ");
                int ResvType = Integer.parseInt(sc.nextLine());
                System.out.print("ResvObject: ");
                String ResvObject = sc.nextLine();

                Reservation reservation = new Reservation(custName,ResvType,ResvObject);
                reservationManager.addReservation(reservation);
                System.out.println("Success.");
            }

            else if(Objects.equals(choice0, "15")){
                System.out.print("CustName: ");
                String custName = sc.nextLine();

                ArrayList<Pair<String,String>> journeys = reservationManager.searchOwnJourney(custName);
                if(journeys.isEmpty()){
                    System.out.println("No Any Journey");
                }else{
                    for(Pair<String,String> j:journeys){
                        System.out.println("From "+j.getKey()+" To "+j.getValue());
                    }
                }
            }

            //------------------------------------------------------------------

            else if(Objects.equals(choice0, "16")){
                System.out.print("StartCity: ");
                String startCity = sc.nextLine();
                System.out.print("EndCity: ");
                String endCity = sc.nextLine();

                List<String> ans=flightManager.judgeAccessibleBFS(startCity,endCity);
                if(ans==null){
                    System.out.println("\nNot Accessible.");
                }else{
                    System.out.println("\nAccessible.");
                    System.out.println("One of the Roads:");
                    System.out.print("\t");
                    for(int i=0;i<ans.size()-1;i++){
                        System.out.print(ans.get(i)+"->");
                    }
                    System.out.println(ans.get(ans.size()-1));
                }
            }

            else if(Objects.equals(choice0, "17")){
                System.out.println("Bye.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            else{
                System.out.println("\nUnknown Choice.");
            }

            //--------------------------------------------------
            //一次事务结束
            System.out.print("\nInput Any Character To Continue...");
            sc.nextLine();
            System.out.println();
        }
    }

    private void printMainMenu(){
        System.out.print("" +
                "-------------------\n" +
                "*** Main Menu ***\n" +
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
