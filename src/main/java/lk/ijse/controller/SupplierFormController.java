package lk.ijse.controller;

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
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierName;

    static String supplierId;
    static String supplierAddress;
    static String supplierName;
    static String supplierContact;

    public void initialize() throws SQLException {
        getAllSuppliers();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void getAllSuppliers() throws SQLException {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        List<Supplier> supplierList = SupplierRepo.getAll();

        for ( Supplier supplier: supplierList){
            obList.add(new SupplierTm(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getContact(),
                    supplier.getStatus()
            ));
        }
        tblSupplier.setItems(obList);

    }

    @FXML
    void btnOnActionAddSupplier(ActionEvent event) throws SQLException {
        String id = "";
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtContact.getText();
        String status = "Active";

        Supplier supplier = new Supplier(id, supplierName, supplierAddress, supplierContact, status);

        try {
            boolean isAdded = SupplierRepo.addSupplier(supplier);
            if (isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Supplier added successfully").show();
                getAllSuppliers();
                setCellValueFactory();
                txtSupplierName.clear();
                txtSupplierAddress.clear();
                txtContact.clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier not added successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) throws SQLException {

        try {
            boolean isDeleted = SupplierRepo.deleteSupplier(supplierId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully").show();
                getAllSuppliers();
                setCellValueFactory();
                txtSupplierName.clear();
                txtSupplierAddress.clear();
                txtContact.clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier not deleted successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnOnActionUpdateSupplier(ActionEvent event) {

        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String contact = txtContact.getText();

        try {
            boolean isUpdated = SupplierRepo.updateSupplier(supplierId,name,address,contact);

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully").show();
                getAllSuppliers();
                setCellValueFactory();
                txtSupplierName.clear();
                txtSupplierAddress.clear();
                txtContact.clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier not updated successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblSupplier.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        supplierId = (String) colSupId.getCellData(index);
        supplierName = (String) colSupplierName.getCellData(index);
        supplierAddress = (String) colSupplierAddress.getCellData(index);
        supplierContact = (String) colContact.getCellData(index);

        txtSupplierName.setText(supplierName);
        txtSupplierAddress.setText(supplierAddress);
        txtContact.setText(supplierContact);
    }
}
