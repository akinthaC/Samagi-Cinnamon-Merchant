package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PaymentFormController {

    @FXML
    private TableColumn<?, ?> colAdvance;

    @FXML
    private TableColumn<?, ?> colAmountToBePay;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colOrderNo;

    @FXML
    private TableColumn<?, ?> colPaymentNo;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private JFXComboBox<?> comBoxOrderNo;

    @FXML
    private JFXComboBox<?> comBoxPaymentNo;

    @FXML
    private JFXComboBox<?> comBoxType;

    @FXML
    private TableView<?> tblPayment;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtAmountToBePay;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtDate;

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionPendingPayments(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

}
