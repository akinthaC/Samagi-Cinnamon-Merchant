package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Item;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.ItemTm;
import lk.ijse.repository.ItemRepo;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;

import static lk.ijse.controller.SupplierFormController.supplierName;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemType;

    @FXML
    private TableColumn<?, ?> colOnHandWeight;

    @FXML
    private JFXComboBox<String> comBoxItemType;

    @FXML
    private TableView<ItemTm> tblPayment;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtOnHandWeight;

    static String itemId;
    static String itemName;
    static String onHandWeight;
    static String itemType;

    public void initialize() throws SQLException {
        getAllItems();
        setCellValueFactory();
        getItemTypes();
    }

    private void getItemTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Cinnamon",
                "Other"
        );

        comBoxItemType.setItems(obList);
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colOnHandWeight.setCellValueFactory(new PropertyValueFactory<>("onHandWeight"));
    }

    private void getAllItems() throws SQLException {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        List<Item> itemList = ItemRepo.getAll();

        for ( Item item: itemList){
            obList.add(new ItemTm(
                    item.getItemId(),
                    item.getItemName(),
                    item.getOnHandWeight(),
                    item.getDeletes(),
                    item.getItemType()
            ));
        }
        tblPayment.setItems(obList);
    }

    @FXML
    void btnOnActionAddItem(ActionEvent event) {
        String id = "";
        String itemName = txtItemName.getText();
        String itemOnHandWeight = txtOnHandWeight.getText();
        String itemType = comBoxItemType.getValue();
        String status = "Active";

        Item item = new Item(id, itemName, itemOnHandWeight, status, itemType);

        try {
            boolean isAdded = ItemRepo.addItem(item);
            if (isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Item added successfully").show();
                getAllItems();
                setCellValueFactory();
                txtItemName.clear();
                txtOnHandWeight.clear();
                comBoxItemType.setValue("");
            }else {
                new Alert(Alert.AlertType.ERROR, "Item not added successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        try {
            boolean isDeleted = ItemRepo.deleteItem(itemId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully").show();
                getAllItems();
                setCellValueFactory();
                txtOnHandWeight.clear();
                txtItemName.clear();
                comBoxItemType.setValue("");
            }else {
                new Alert(Alert.AlertType.ERROR, "Item not deleted successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOnActionUpdateItem(ActionEvent event) {
        String itemName = txtItemName.getText();
        String itemOnHandWeight = txtOnHandWeight.getText();
        String itemType = comBoxItemType.getValue();

        try {
            boolean isUpdated = ItemRepo.updateItem(itemId,itemName,itemOnHandWeight,itemType);

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Item updated successfully").show();
                getAllItems();
                setCellValueFactory();
                txtItemName.clear();
                txtOnHandWeight.clear();
                comBoxItemType.setValue("");
            }else {
                new Alert(Alert.AlertType.ERROR, "Item not updated successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void OnMouseClicked(MouseEvent mouseEvent) {
        int index = tblPayment.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        itemId = (String) colItemId.getCellData(index);
        itemName = (String) colItemName.getCellData(index);
        onHandWeight = (String) colOnHandWeight.getCellData(index);
        itemType = (String) colItemType.getCellData(index);

        txtItemName.setText(itemName);
        txtOnHandWeight.setText(onHandWeight);
        comBoxItemType.setValue(itemType);
    }
}
