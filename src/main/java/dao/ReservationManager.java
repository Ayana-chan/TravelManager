package dao;

import JDBC.JDBCUtilsByDruid;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import dao.expection.UnknownReservationTypeException;
import javafx.util.Pair;
import object.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservationManager {
    public ArrayList<Reservation> searchAllReservation(){
        ArrayList<Reservation> reservations=new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            String sql="select * FROM reservations";
            preparedStatement = connection.prepareStatement(sql);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Reservation newReservation=new Reservation(resultSet.getInt("resvKey"),resultSet.getString("custName"),
                        resultSet.getInt("resvType"),resultSet.getString("resvObject"));
                reservations.add(newReservation);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
        return reservations;
    }

    /**
     *
     * @param reservation
     * @return false means "resvObject is not available (full)"
     */
    public void addReservation(Reservation reservation){
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            //查看是否有空位
            if(!judgeAvailable(reservation.getResvType(),reservation.getResvObject())){
                throw new InsufficientSpaceException();
            }

            String sql="INSERT INTO reservations(custName,resvType,resvObject) VALUES (?,?,?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reservation.getCustName());
            preparedStatement.setInt(2,reservation.getResvType());
            preparedStatement.setString(3,reservation.getResvObject());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
    }

    public Map<String,Pair<String,String>> searchOwnJourney(String customerName) {
        Map<String,Pair<String,String>> journeys = new HashMap<>();

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();

            //查找指定用户预订的所有航班
            String sql_resv = "select flightNum,fromCity,arivCity " +
                    "from (reservations join flights on resvType=1 and resvObject=flightNum) " +
                    "where custName=?";
            preparedStatement = connection.prepareStatement(sql_resv);

            preparedStatement.setString(1,customerName);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                journeys.put(resultSet.getString("flightNum"),new Pair<>(resultSet.getString("fromCity"),resultSet.getString("arivCity")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(resultSet, preparedStatement, connection);
        }
        return journeys;
    }

    /**
     * 用于在增加预订时看看是否可以预订
     * @param resvType
     * @param resvObject
     * @return 无空位时返回false
     * @throws RuntimeException resvType错误或者resvObject找不到都会抛出RuntimeException
     */
    private boolean judgeAvailable(int resvType,String resvObject) throws RuntimeException{
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            //judge type
            String s1,s2;
            switch (resvType){
                case 1:
                    s1="flights";
                    s2="flightNum";
                    break;
                case 2:
                    s1="hotels";
                    s2="location";
                    break;
                case 3:
                    s1="bus";
                    s2="location";
                    break;
                default:
                    throw new UnknownReservationTypeException();
            }
            String sql="select numAvail from "+ s1 +" where "+ s2 +"=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,resvObject);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if(resultSet.getInt(1)>0){
                    return true;
                }
                return false;//full
            }else{  //没有那个实体，resvObject对不上
                throw new TargetNotFoundException();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
    }
}
