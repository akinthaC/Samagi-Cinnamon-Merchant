package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {

    public static List<PaymentInfo> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";

        List<PaymentInfo> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new PaymentInfo(
                    resultSet.getString("paymentNo"),
                    resultSet.getString("supplierId"),
                    resultSet.getDouble("totalAmount"),
                    resultSet.getString("date"),
                    resultSet.getDouble("payAmount"),
                    resultSet.getDouble("toBePaAmount"),
                    resultSet.getString("paymentType"),
                    resultSet.getString("description"),
                    resultSet.getString("status")
            ));
        }
        return data;
    }

    public static List<PaymentInfo> getPaymentNoBySupplierId(String supplierId) throws SQLException {

        String sql = "SELECT * FROM payment where supplierId = ? and status='pending'";
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, supplierId);

        List<PaymentInfo> data = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            data.add(new PaymentInfo(
                    resultSet.getString("paymentNo"),
                    resultSet.getString("supplierId"),
                    resultSet.getDouble("totalAmount"),
                    resultSet.getString("date"),
                    resultSet.getDouble("payAmount"),
                    resultSet.getDouble("toBePaAmount"),
                    resultSet.getString("paymentType"),
                    resultSet.getString("description"),
                    resultSet.getString("status")
            ));
        }
        return data;
    }

    public static List<String> getPaymentIds() throws SQLException {
        String sql = "SELECT paymentNo FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }


}
