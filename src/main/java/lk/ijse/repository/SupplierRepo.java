package lk.ijse.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.Db.DbConnection;

public class SupplierRepo {
    public static List<String> searchSupplierNmae() throws SQLException {
        String sql = "SELECT name  FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            idList.add(name);
        }
        return idList;
    }

    public static List<String> nameSearch(String name) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, name);
        List<String> stringList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String name1 = resultSet.getString(2);
            String contact = resultSet.getString(4);


            stringList.add(name1);
            stringList.add(contact);

            return stringList;
        }
        return null;
    }

    public static List<String> SuplierSearchByContact(String contact) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE phonenumber = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, contact);
        List<String> stringList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String name1 = resultSet.getString(2);
            String contact1 = resultSet.getString(4);


            stringList.add(name1);
            stringList.add(contact1);

            return stringList;
        }

        return null ;
    }

    public static List<String> searchSupplierContact() throws SQLException {
        String sql = "SELECT phonenumber  FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String contact = resultSet.getString(1);
            idList.add(contact);
        }
        return idList;
    }
}
