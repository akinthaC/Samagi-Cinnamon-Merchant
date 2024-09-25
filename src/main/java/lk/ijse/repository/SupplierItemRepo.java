package lk.ijse.repository;

import lk.ijse.Db.DbConnection;

import java.sql.*;

public class SupplierItemRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT id FROM supplieritem ORDER BY id DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String ordId = resultSet.getString(1);
            return ordId;
        }
        return null;
    }


    public static boolean save(String supId, String itemId, double price, double weight, Date date, double netWeight, String id) throws SQLException {
        String sql = "INSERT INTO supplierItem VALUES(?,?,?,?,?,?,?)";


        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,supId);
        pstm.setObject(2, itemId);
        pstm.setObject(3, price);
        pstm.setObject(4, weight);
        pstm.setObject(5, date);
        pstm.setObject(6, netWeight);
        pstm.setObject(7, id);


        return pstm.executeUpdate() > 0;
    }
}
