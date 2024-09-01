package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PendingPaymentsController {

    @FXML
    private JFXComboBox<?> comBoxOrderNo;

    @FXML
    private JFXComboBox<?> comBoxPaymentNo;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TableView<?> tblPendingPayments;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtDate;

    @FXML
    void btnOnActionPayAdvance(ActionEvent event) {

    }

    @FXML
    void comBoxOnActionContact(KeyEvent event) {

    }

    @FXML
    void comBoxOnActionSearchContact1(KeyEvent event) {

    }

}
