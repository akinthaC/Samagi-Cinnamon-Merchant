package lk.ijse.repository;

import lk.ijse.Db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingPaymentRepo {

    public static List<String> getPendingPaymentsAll() throws SQLException {
        String sql = "SELECT paymentNo,totalAmount,payAmount,toBePaAmount FROM payment where status = 'pending'";
        Connection con = DbConnection.getInstance().getConnection();

        // Prepare the list to store the payment details as strings
        List<String> inactivePayments = new ArrayList<>();

        // Prepare the SQL statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Execute the query and store the result in a ResultSet
            ResultSet resultSet = stmt.executeQuery();

            // Loop through the result set and create strings for each row
            while (resultSet.next()) {
                // Retrieve each column value from the current row
                String paymentNo = resultSet.getString("paymentNo");
                double totalAmount = resultSet.getDouble("totalAmount");
                double payAmount = resultSet.getDouble("payAmount");
                double toBePaAmount = resultSet.getDouble("toBePaAmount");

                // Create a formatted string containing the payment details
                String paymentDetails = String.format("Payment No: %s, Total Amount: %.2f, Paid Amount: %.2f, To Be Paid Amount: %.2f",
                        paymentNo, totalAmount, payAmount, toBePaAmount);

                // Add the string to the list
                inactivePayments.add(paymentDetails);
            }
        }

        // Return the list of payment details as strings
        return inactivePayments;
    }

    public static List<String> searchPaymentNo() throws SQLException {
        String sql = "SELECT paymentNo FROM payment WHERE status = 'pending'";
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

    public static boolean updatePendingPaymentInfo(String paymentNo, Double advance, String date, Double newAmountToBePay) throws SQLException {

        String sql = "UPDATE payment SET payAmount = ?, date = ?,toBePaAmount = ? WHERE paymentNo = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setDouble(1, advance);
        pstm.setString(2, date);
        pstm.setDouble(3, newAmountToBePay);
        pstm.setString(4, paymentNo);
        return pstm.executeUpdate()>0;

    }
}
