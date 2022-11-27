package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class FlightManager extends JDBCUtilsByDruid{
    /**
     * 获得fromCity为startPlace的所有航班的目的地，即startPlace的所有下一站
     * @param fromCity
     * @return
     */
    public static Set<String> findNextArivCitySet(String fromCity){
        Set<String> arivCities=new HashSet<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            String sql="select arivCity FROM flights where fromCity=?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,fromCity);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                arivCities.add(resultSet.getString(1));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
        return arivCities;
    }

    public void registerFlight(Flight flight) throws HaveRegisteredException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询是否已注册
            String sql_ask="select * FROM flights where flightNum=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,flight.getFlightNum());

            resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {  //如果已被注册
                throw new HaveRegisteredException();
            } else {
                String sql="INSERT INTO flights(flightNum,price,numSeats,fromCity,arivCity) VALUES (?,?,?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,flight.getFlightNum());
                preparedStatement.setInt(2,flight.getPrice());
                preparedStatement.setInt(3,flight.getNumSeats());
                preparedStatement.setString(4,flight.getFromCity());
                preparedStatement.setString(5,flight.getArivCity());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    public ArrayList<Flight> searchAllFlight(){
        ArrayList<Flight> flights=new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            String sql="select * FROM flights";
            preparedStatement = connection.prepareStatement(sql);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Flight newFlight=new Flight(resultSet.getString("flightNum"),resultSet.getInt("price"),
                        resultSet.getInt("numSeats"),resultSet.getInt("numAvail"),
                        resultSet.getString("fromCity"),resultSet.getString("arivCity"));
                flights.add(newFlight);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
        return flights;
    }

    public void modifyFlight(Flight newFlight) throws InsufficientSpaceException{
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询旧数据以保证numAvail不会变成负数
            String sql_ask="select * FROM flights where flightNum=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,newFlight.getFlightNum());

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new TargetNotFoundException();
            }

            int oldSeats=resultSet.getInt("numSeats");
            int oldAvail=resultSet.getInt("numAvail");

            //会变负，则取消修改
            if(oldAvail < oldSeats - newFlight.getNumSeats()){
                throw new InsufficientSpaceException();
            }else {
                newFlight.setNumAvail(newFlight.getNumSeats()+oldAvail - oldSeats);
            }

            //更新
            String sql="update flights " +
                    "set price=?,numSeats=?,numAvail=?,fromCity=?,arivCity=? " +
                    "where flightNum=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,newFlight.getPrice());
            preparedStatement.setInt(2,newFlight.getNumSeats());
            preparedStatement.setInt(3, newFlight.getNumAvail());
            preparedStatement.setString(4,newFlight.getFromCity());
            preparedStatement.setString(5,newFlight.getArivCity());
            preparedStatement.setString(6,newFlight.getFlightNum());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    public boolean judgeAccessible(String startPlace,String endPlace){
        Set<String> visited=new HashSet<>();
        Queue<String> queue=new LinkedBlockingQueue<>();
        queue.add(startPlace);
        while(!queue.isEmpty()){
            //出队
            String fromCity=queue.poll();
            Set<String> arivCities=findNextArivCitySet(fromCity);
            //若可找到
            if(arivCities.contains(endPlace)){
                return true;
            }
            //入队
            for(String p:arivCities){
                if(!visited.contains(p)){
                    queue.add(p);
                    visited.add(p);//标记
                }
            }
        }
        return false;
    }
}


//    ArrayList<Set<String>> twoSets=new ArrayList<>();
//        twoSets.add(new HashSet<>());
//        twoSets.add(new HashSet<>());
//        twoSets.get(0).add(startPlace);
//        int indexFlag=0;
//        while (true){
//            for(String p:twoSets.get(indexFlag)){
//                Set<String> arivCities= findNextArivCitySet(p);
//                //若可到达
//                if(arivCities.contains(endPlace)){
//                    return true;
//                }
//                //将可到达地点加入下一个集合
//                twoSets.get(1-indexFlag).addAll(arivCities);
//            }
//            twoSets.get(indexFlag).clear();
//            indexFlag=1-indexFlag;
//        }
