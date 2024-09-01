package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PaymentInfoFormController {

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private JFXComboBox<?> comBoxType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderNo;

    @FXML
    private Label lblPaymentNo;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TextField txtPayAmount;

    @FXML
    void btnOnActionPayNow(ActionEvent event) {

    }

    @FXML
    void comBoxOnActionContact(KeyEvent event) {

    }

    @FXML
    void comBoxOnActionSearchContact1(KeyEvent event) {

    }

}
