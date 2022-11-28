package ui;

public class Main {
    public static void main(String[] args) {
        ServiceToOut serviceToOut=new ServiceToOut();
        serviceToOut.service();
    }

    private void printWelcomeMenu(){
        System.out.println("" +
                "TravelManager\n" +
                "Produced by DeathWind\n");
    }
}