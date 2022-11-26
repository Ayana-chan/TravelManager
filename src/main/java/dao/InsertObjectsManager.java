package dao;

import object.*;
import dao.expection.HaveRegisteredException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * add new customer & bus & flight & hotel
 */
public class InsertObjectsManager extends JDBCUtilsByDruid{
    //dispatcher
    public void addObjectAbleToRegister(ObjectAbleToRegister newObj) throws HaveRegisteredException {
        if(newObj instanceof Flight){
            registerFlight((Flight) newObj);
        } else if (newObj instanceof Bus) {
            registerBus((Bus) newObj);
        } else if (newObj instanceof Hotel) {
            registerHotel((Hotel) newObj);
        } else if (newObj instanceof Customer) {
            registerCustomer((Customer) newObj);
        }
    }

    private void registerFlight(Flight flight) throws HaveRegisteredException {
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
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    private void registerBus(Bus bus) throws HaveRegisteredException {
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
                String sql="INSERT INTO bus(location,price,numBus,numAvail) VALUES (?,?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bus.getLocation());
                preparedStatement.setInt(2,bus.getPrice());
                preparedStatement.setInt(3,bus.getNumBus());
                preparedStatement.setInt(4,bus.getNumAvail());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    private void registerHotel(Hotel hotel) throws HaveRegisteredException {
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
                String sql="INSERT INTO hotels(location,price,numRooms,numAvail) VALUES (?,?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, hotel.getLocation());
                preparedStatement.setInt(2,hotel.getPrice());
                preparedStatement.setInt(3,hotel.getNumRooms());
                preparedStatement.setInt(4,hotel.getNumAvail());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }

    private void registerCustomer(Customer customer) throws HaveRegisteredException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=getConnection();

            //查询是否已注册
            String sql_ask="select * FROM customers where custName=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,customer.getCustName());

            resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {  //如果已被注册
                throw new HaveRegisteredException();
            } else {
                String sql="INSERT INTO customers(custName,custID) VALUES (?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, customer.getCustName());
                preparedStatement.setString(2,customer.getCustID());

                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(resultSet,preparedStatement,connection);
        }
    }
}
