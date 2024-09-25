package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;

import java.sql.Connection;
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
}
