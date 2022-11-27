package dao;

import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Bus;
import object.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelManager extends JDBCUtilsByDruid{
    public void registerHotel(Hotel hotel) throws HaveRegisteredException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询是否已注册
            String sql_ask="select * FROM hotels where location=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,hotel.getLocation());

            resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {  //如果已被注册
                throw new HaveRegisteredException();
            } else {
                String sql="INSERT INTO hotels(location,price,numRooms) VALUES (?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, hotel.getLocation());
                preparedStatement.setInt(2,hotel.getPrice());
                preparedStatement.setInt(3,hotel.getNumRooms());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    public ArrayList<Hotel> searchAllHotel(){
        ArrayList<Hotel> hotels=new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            String sql="select * FROM hotels";
            preparedStatement = connection.prepareStatement(sql);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Hotel newHotel=new Hotel(resultSet.getString("location"),resultSet.getInt("price"),
                        resultSet.getInt("numRooms"),resultSet.getInt("numAvail"));
                hotels.add(newHotel);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
        return hotels;
    }

    public void modifyHotel(Hotel newHotel) throws InsufficientSpaceException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询旧数据以保证numAvail不会变成负数
            String sql_ask="select * FROM hotels where location=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,newHotel.getLocation());

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new TargetNotFoundException();
            }

            int oldRooms=resultSet.getInt("numRooms");
            int oldAvail=resultSet.getInt("numAvail");

            //会变负，则取消修改
            if(oldAvail < oldRooms - newHotel.getNumRooms()){
                throw new InsufficientSpaceException();
            }else {
                newHotel.setNumAvail(newHotel.getNumRooms() + oldAvail - oldRooms);
            }

            //更新
            String sql="update hotels " +
                    "set price=?,numRooms=?,numAvail=? " +
                    "where location=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,newHotel.getPrice());
            preparedStatement.setInt(2,newHotel.getNumRooms());
            preparedStatement.setInt(3, newHotel.getNumAvail());
            preparedStatement.setString(4,newHotel.getLocation());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }
}
