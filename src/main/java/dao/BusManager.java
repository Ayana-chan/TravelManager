package dao;

import dao.expection.HaveRegisteredException;
import object.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
