package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static List<String> gtAllItems(String type) throws SQLException {
        String sql = "SELECT itemName FROM item WHERE type= ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, type);
        ResultSet resultSet = pstm.executeQuery();

        List<String>  accessoriesList = new ArrayList<>();

        while (resultSet.next()) {

            String name = resultSet.getString("itemName");
            accessoriesList.add(name);

        }
        return accessoriesList;

    }

    public static String getId(String text) throws SQLException {
        String sql = "SELECT itemId FROM item WHERE itemName = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, text);


        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString(1);

            return id;
        }
        return null;

    }

    public static double getWeight(String itemId) throws SQLException {
        String sql = "SELECT onHandWeight FROM item WHERE itemId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, itemId);


        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            double weight = Double.parseDouble(resultSet.getString(1));

            return weight;
        }
        return 0.0;
    }

    public static boolean upateWeight(String itemId, double weight) throws SQLException {
        String sql = "UPDATE item SET onHandWeight = onHandWeight + ? WHERE itemId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, (int) weight);
        pstm.setString(2, itemId);

        return pstm.executeUpdate() > 0;
    }
}
