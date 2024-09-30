package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private JFXComboBox<?> comBoxItemType;

    @FXML
    private TableView<?> tblPayment;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtOnHandWeight;

    @FXML
    void btnOnActionAddItem(ActionEvent event) {

    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdateItem(ActionEvent event) {

    }

}
