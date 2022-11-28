package dao;

import JDBC.JDBCUtilsByDruid;
import dao.expection.HaveRegisteredException;
import dao.expection.InsufficientSpaceException;
import dao.expection.TargetNotFoundException;
import object.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerManager {
    public void registerCustomer(Customer customer) throws HaveRegisteredException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

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
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
    }

    public Customer searchCustomer(String custName){
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            String sql_ask="select * FROM customers where custName=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,custName);

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new TargetNotFoundException();
            }
            return new Customer(resultSet.getString("custName"),resultSet.getString("custID"));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
    }

    public ArrayList<Customer> searchAllCustomer(){
        ArrayList<Customer> customers=new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            String sql="select * FROM customers";
            preparedStatement = connection.prepareStatement(sql);

            resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Customer newCustomer=new Customer(resultSet.getString("custName"),resultSet.getString("custID"));
                customers.add(newCustomer);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
        return customers;
    }

    public void modifyCustomer(Customer newCustomer) throws InsufficientSpaceException {
        Connection connection = null;
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            connection=JDBCUtilsByDruid.getConnection();

            //查询是否存在
            String sql_ask="select * FROM customers where custName=?";
            preparedStatement = connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,newCustomer.getCustName());

            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new TargetNotFoundException();
            }

            //更新
            String sql="update customers " +
                    "set custID=? " +
                    "where custName=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newCustomer.getCustID());
            preparedStatement.setString(2,newCustomer.getCustName());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(resultSet,preparedStatement,connection);
        }
    }
}
