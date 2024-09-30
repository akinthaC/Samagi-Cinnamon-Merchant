package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableView<?> tblPayment;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierName;

    @FXML
    void btnOnActionAddSupplier(ActionEvent event) {

    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdateSupplier(ActionEvent event) {

    }

}
