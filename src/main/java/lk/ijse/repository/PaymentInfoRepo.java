package lk.ijse.repository;


import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
