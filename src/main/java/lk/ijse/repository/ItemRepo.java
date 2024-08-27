package lk.ijse.repository;

import lk.ijse.Db.DbConnection;
import lk.ijse.model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static List<String> gtAllItems(String type) throws SQLException {
        String sql = "SELECT name FROM item WHERE type= ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, type);
        ResultSet resultSet = pstm.executeQuery();

        List<String>  accessoriesList = new ArrayList<>();

        while (resultSet.next()) {

            String name = resultSet.getString("name");
            accessoriesList.add(name);

        }
        return accessoriesList;

    }
}
