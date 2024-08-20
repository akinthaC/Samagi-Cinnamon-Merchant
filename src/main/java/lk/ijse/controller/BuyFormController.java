package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BuyFormController {

    @FXML
    private TableColumn<?, ?> colBuyingPrice;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colCuttingAmount;

    @FXML
    private TableColumn<?, ?> colProductType;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private JFXComboBox<?> comBoxContact;

    @FXML
    private JFXComboBox<?> comBoxName;

    @FXML
    private Label lblNetWeight;

    @FXML
    private Label lblProductType;

    @FXML
    private AnchorPane paneType;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtCuttingAmount;

    @FXML
    private TextField txtWeight;

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {

    }

    @FXML
    void btnOnActionCinnamon(ActionEvent event) {

    }

    @FXML
    void btnOnActionOther(ActionEvent event) {

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void comBoxOnActionContact(ActionEvent event) {

    }

    @FXML
    void comBoxOnActionName(ActionEvent event) {

    }

}
