package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.Db.DbConnection;
import lk.ijse.model.PlaceOrderSupplier;
import lk.ijse.model.SupplierItem;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderSupplierRepo {
    public static boolean orderSupplier(PlaceOrderSupplier placeOrderSupplier, String contact) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean ifSave = true;
        if (placeOrderSupplier.getSup()!=null) {
            System.out.println("empty");
            String id = (String) placeOrderSupplier.getSup().get(0);
            ifSave= SupplierRepo.saveSupplierTemp(id, contact);

        }
            System.out.println("noempty");
            if (ifSave) {
                String SupId = SupplierRepo.searchSupplierId(contact);
                boolean isSupItemSave = false;
                for (SupplierItem od : placeOrderSupplier.getOdlist()) {
                    System.out.println(od.getItemId() +"  "+ od.getWeight());
                    isSupItemSave = SupplierItemRepo.save(SupId, od.getItemId(), od.getPrice(), od.getWeight(), od.getDate(), od.getNetWeight(), od.getId());
                }

                if (isSupItemSave) {
                    boolean isupdateItem = false;

                    for (SupplierItem od1 : placeOrderSupplier.getOdlist()) {
                        double onHandWeight = ItemRepo.getWeight(od1.getItemId());

                        System.out.println(od1.getItemId() +"  "+ od1.getWeight());
                        isupdateItem = ItemRepo.upateWeight(od1.getItemId(), od1.getWeight());


                    }
                    System.out.println(isupdateItem);
                    if (isupdateItem) {
                        System.out.println(isupdateItem);
                        connection.commit();
                        return true;
                    }
                }
            }



        connection.rollback();
        return false;
    } catch (SQLException e) {
            connection.rollback();
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            return false;

    } finally {
        connection.setAutoCommit(true);
    }


    }
}
