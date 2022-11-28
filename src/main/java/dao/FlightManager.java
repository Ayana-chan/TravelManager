package dao;

import JDBC.JDBCUtilsByDruid;
import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class FlightManager extends JDBCUtilsByDruid {
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

    public Flight searchFlight(String flightNum) throws InsufficientSpaceException{
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询旧数据以保证numAvail不会变成负数
            String sql_ask="select * FROM flights where flightNum=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,flightNum);

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new TargetNotFoundException();
            }
            return new Flight(resultSet.getString("flightNum"),resultSet.getInt("price"),
                    resultSet.getInt("numSeats"),resultSet.getInt("numAvail"),
                    resultSet.getString("fromCity"),resultSet.getString("arivCity"));


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

    /**
     * 使用BFS查询可达路径，只能输出一条最短路径
     * @param startPlace
     * @param endPlace
     * @return 可达的某一条最短路径。如果不可达，返回null
     */
    public List<String> judgeAccessibleBFS(String startPlace, String endPlace){
        //原地，必可达
        if(Objects.equals(startPlace, endPlace)){
            List<String> ans=new ArrayList<>();
            ans.add(startPlace);
            ans.add(startPlace);
            return ans;
        }

        //BFS遍历结点
        class BFSNode{
            public String pre;
            public String city;

            public BFSNode(String pre, String city) {
                this.pre = pre;
                this.city = city;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                BFSNode bfsNode = (BFSNode) o;
                return city.equals(bfsNode.city);
            }

            @Override
            public int hashCode() {
                return Objects.hash(city);
            }

            @Override
            public String toString() {
                return "BFSNode{" +
                        "pre='" + pre + '\'' +
                        ", city='" + city + '\'' +
                        '}';
            }
        }

        Set<BFSNode> visited=new HashSet<>();
        Queue<BFSNode> queue=new LinkedBlockingQueue<>();
        queue.add(new BFSNode("",startPlace));//startPlace只入队列不入visited
        while(!queue.isEmpty()){
//            System.out.println("queue: " + queue);
            String fromCity=queue.poll().city;
            Set<String> arivCities=findNextArivCitySet(fromCity);
            //若找到
            if(arivCities.contains(endPlace)){
                List<String> ans=new ArrayList<>();
                ans.add(endPlace);
                ans.add(fromCity);
                //遍历取路径
                while(!Objects.equals(fromCity, startPlace)){
//                    System.out.println("ans: "+ans);
                    for (BFSNode node : visited) {
                        if (Objects.equals(node.city, fromCity)) {
                            fromCity = node.pre;
                            ans.add(fromCity);
                            break;
                        }
                    }
                }
                //ans是倒序的，要正回来
                Collections.reverse(ans);
                return ans;
            }
            //未找到，入队
            for(String p:arivCities){
                if(!visited.contains(new BFSNode("", p))){
                    BFSNode newNode=new BFSNode(fromCity, p);
                    queue.add(newNode);
                    visited.add(newNode);//标记
                }
            }
        }
        return null;
    }
}


