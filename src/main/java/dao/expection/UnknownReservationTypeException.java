package dao.expection;

public class UnknownReservationTypeException extends RuntimeException{
    public UnknownReservationTypeException(){
        super("Unknown Reservation Type.");
    }
}
