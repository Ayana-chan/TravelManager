package dao;

import dao.expection.HaveRegisteredException;
import object.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightManager extends JDBCUtilsByDruid{
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
}
