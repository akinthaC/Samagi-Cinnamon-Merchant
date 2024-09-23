package lk.ijse.repository;


import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentInfoRepo {

    public static String getCurrentNo() throws SQLException {
        String sql = "SELECT orderId FROM payment ORDER BY orderId DESC LIMIT 1";
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
        String sql = "insert into payment values(?,?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,paymentInfo.getSupID());
        pstm.setObject(2,paymentInfo.getOrderNo());
        pstm.setObject(3,paymentInfo.getPaymentNo());
        pstm.setObject(4,paymentInfo.getDate());
        pstm.setObject(5,paymentInfo.getTotalAmount());
        pstm.setObject(6,paymentInfo.getPayAmount());
        pstm.setObject(7,paymentInfo.getDescription());

        return pstm.executeUpdate() > 0;
    }
}
