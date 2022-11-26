package dao;

import dao.expection.HaveRegisteredException;
import object.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManager extends JDBCUtilsByDruid{
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
