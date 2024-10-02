package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.Item;
import lk.ijse.model.Supplier;

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

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item where deletes='Active'";
        Connection connection = DbConnection.getInstance().getConnection();

        List<Item> data = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString("itemId"),
                    resultSet.getString("itemName"),
                    resultSet.getString("onHandWeight"),
                    resultSet.getString("deletes"),
                    resultSet.getString("type")
            ));
        }
        return data;
    }

    public static boolean addItem(Item item) throws SQLException {
        String sql = "INSERT INTO item(itemName,onHandWeight,deletes,type) VALUES(?, ?, ?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, item.getItemName());
        pstm.setObject(2, item.getOnHandWeight());
        pstm.setObject(3, item.getDeletes());
        pstm.setObject(4,item.getItemType());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteItem(String itemId) throws SQLException {
        String sql = "UPDATE item set deletes='Inactive' WHERE itemId = ? ";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, itemId);
        return pstm.executeUpdate() >0;
    }

    public static boolean updateItem(String itemId, String itemName, String itemOnHandWeight, String itemType) throws SQLException {
        String sql = "UPDATE item set itemName = ?, onHandWeight = ?, type = ? WHERE itemId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, itemName);
        pstm.setObject(2, itemOnHandWeight);
        pstm.setObject(3, itemType);
        pstm.setObject(4, itemId);

        return pstm.executeUpdate() > 0;
    }
}
