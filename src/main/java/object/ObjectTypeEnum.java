package object;

public enum ObjectTypeEnum {
    CUSTOMER,FLIGHT,BUS,HOTEL;

    public static int getTypeNum(ObjectTypeEnum objectTypeEnum){
        switch (objectTypeEnum){
            case FLIGHT:
                return 1;
            case HOTEL:
                return 2;
            case BUS:
                return 3;
            default:
                return 0;
        }
    }
}
