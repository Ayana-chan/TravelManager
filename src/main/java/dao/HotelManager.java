package dao;

import dao.expection.HaveRegisteredException;
import object.Bus;
import object.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
