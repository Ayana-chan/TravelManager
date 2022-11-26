package dao;

import object.Flight;
import dao.expection.FailToDoneSqlException;
import dao.expection.HaveRegisteredException;
import object.ObjectAbleToRegister;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * add new customer & bus & flight & hotel
 */
public class InsertObjectsManager extends JDBCUtilsByDruid{
    public void addObjectAbleToRegister(ObjectAbleToRegister newObj) throws FailToDoneSqlException {
        if(newObj instanceof Flight){
            registerFlight((Flight) newObj);
        }
    }

    private void registerFlight(Flight flight) throws FailToDoneSqlException {
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
                String sql="INSERT INTO flights(flightNum,price,numSeats,numAvail,fromCity,arivCity) VALUES (?,?,?,?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,flight.getFlightNum());
                preparedStatement.setInt(2,flight.getPrice());
                preparedStatement.setInt(3,flight.getNumSeats());
                preparedStatement.setInt(4,flight.getNumAvail());
                preparedStatement.setString(5,flight.getFromCity());
                preparedStatement.setString(6,flight.getArivCity());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new FailToDoneSqlException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }
}
