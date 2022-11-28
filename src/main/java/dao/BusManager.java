package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Bus;
import object.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusManager extends JDBCUtilsByDruid{
    public void registerBus(Bus bus) throws HaveRegisteredException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询是否已注册
            String sql_ask="select * FROM bus where location=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,bus.getLocation());

            resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {  //如果已被注册
                throw new HaveRegisteredException();
            } else {
                String sql="INSERT INTO bus(location,price,numBus) VALUES (?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bus.getLocation());
                preparedStatement.setInt(2,bus.getPrice());
                preparedStatement.setInt(3,bus.getNumBus());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    public Bus searchBus(String location){
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            String sql_ask="select * FROM bus where location=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,location);

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new TargetNotFoundException();
            }
            return new Bus(resultSet.getString("location"),resultSet.getInt("price"),
                    resultSet.getInt("numBus"),resultSet.getInt("numAvail"));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    public ArrayList<Bus> searchAllBus(){
        ArrayList<Bus> buses=new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            String sql="select * FROM bus";
            preparedStatement = connection.prepareStatement(sql);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Bus newBus=new Bus(resultSet.getString("location"),resultSet.getInt("price"),
                        resultSet.getInt("numBus"),resultSet.getInt("numAvail"));
                buses.add(newBus);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
        return buses;
    }

    public void modifyBus(Bus newBus) throws InsufficientSpaceException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询旧数据以保证numAvail不会变成负数
            String sql_ask="select * FROM bus where location=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,newBus.getLocation());

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new TargetNotFoundException();
            }

            int oldBusNum=resultSet.getInt("numBus");
            int oldAvail=resultSet.getInt("numAvail");

            //会变负，则取消修改
            if(oldAvail < oldBusNum - newBus.getNumBus()){
                throw new InsufficientSpaceException();
            }else {
                newBus.setNumAvail(newBus.getNumBus() + oldAvail - oldBusNum);
            }

            //更新
            String sql="update bus " +
                    "set price=?,numBus=?,numAvail=? " +
                    "where location=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,newBus.getPrice());
            preparedStatement.setInt(2,newBus.getNumBus());
            preparedStatement.setInt(3, newBus.getNumAvail());
            preparedStatement.setString(4,newBus.getLocation());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }
}
