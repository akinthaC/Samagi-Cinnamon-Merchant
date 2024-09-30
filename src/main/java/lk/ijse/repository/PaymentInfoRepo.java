package lk.ijse.repository;


import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentInfoRepo {

    public static String getCurrentNo() throws SQLException {
        String sql = "SELECT paymentNo FROM payment ORDER BY paymentNo DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String payId = resultSet.getString(1);
            return payId;
        }
        return null;
    }

    public static boolean save(PaymentInfo paymentInfo) throws SQLException {
        String sql = "insert into payment (paymentNo,supplierId,date,totalAmount,payAmount,toBePaAmount,description,paymentType,status) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,paymentInfo.getPaymentNo());
        pstm.setObject(2,paymentInfo.getSupID());
        pstm.setObject(3,paymentInfo.getDate());
        pstm.setObject(4,paymentInfo.getTotalAmount());
        pstm.setObject(5,paymentInfo.getPayAmount());
        pstm.setObject(6,paymentInfo.getToBePaAmount());
        pstm.setObject(7,paymentInfo.getDescription());
        pstm.setObject(8,paymentInfo.getPaymentType());
        pstm.setObject(9,paymentInfo.getStatus());

        return pstm.executeUpdate() > 0;
    }

    public static List<PaymentInfo> getAllPayments(String paymentNo) throws SQLException {
        String sql = "SELECT * FROM payment WHERE paymentNo = ?";

        // Initialize an empty list to store PaymentInfo objects
        List<PaymentInfo> paymentList = new ArrayList<>();

        // Get database connection and prepare the statement
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        // Set the value for the paymentNo parameter in the query
        pstm.setString(1, paymentNo);

        // Execute the query
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            // Assuming PaymentInfo has a constructor like this:
            // PaymentInfo(String paymentId, String paymentNo, String supplierId, double totalAmount, LocalDate date, double payAmount, double toBePaAmount, String paymentType, String description, String status)

            String pNo = resultSet.getString("paymentNo");
            String supplierId = resultSet.getString("supplierId");
            double totalAmount = resultSet.getDouble("totalAmount");
            String date = String.valueOf(resultSet.getDate("date"));
            double payAmount = resultSet.getDouble("payAmount");
            double toBePaAmount = resultSet.getDouble("toBePaAmount");
            String paymentType = resultSet.getString("paymentType");
            String description = resultSet.getString("description");
            String status = resultSet.getString("status");

            PaymentInfo paymentInfo = new PaymentInfo(supplierId, pNo, totalAmount, date, payAmount, toBePaAmount, paymentType, description, status);
            paymentList.add(paymentInfo);
        }

        return paymentList;
    }
}
