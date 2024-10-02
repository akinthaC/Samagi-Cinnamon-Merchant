package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.model.PaymentInfo;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.stage.Stage;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;

public class PaymentFormController {

    @FXML
    private TableColumn<?, ?> colSupId;

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
    private JFXComboBox<String> comBoxPaymentNo;

    @FXML
    private JFXComboBox<String> comBoxType;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtAmountToBePay;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtDate;

    public void initialize() throws SQLException {
        comBoxPaymentNo.setEditable(true);
        getAllPayments();
        setCellValueFactory();
        getPaymentNos();
        getPaymentMethods();
    }

    private void getPaymentNos() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = PaymentRepo.getPaymentIds();

            for (String No : NoList) {
                obList.add(No);
            }

            comBoxPaymentNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPaymentMethods() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Cash",
                "Credit Card",
                "Cheque"
        );

        comBoxType.setItems(obList);
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supID"));//wdk n
        colPaymentNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("payAmount")); //advance
        colAmountToBePay.setCellValueFactory(new PropertyValueFactory<>("toBePaAmount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getAllPayments() throws SQLException {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        List<PaymentInfo> PaymentList = PaymentRepo.getAll();

        for (PaymentInfo pay : PaymentList) {
            obList.add(new PaymentTm(
                    pay.getPaymentNo(),
                    pay.getSupID(),
                    pay.getTotalAmount(),
                    pay.getDate(),
                    pay.getPayAmount(), //Advance
                    pay.getToBePaAmount(),
                    pay.getPaymentType(),
                    pay.getDescription(),
                    pay.getStatus()
            ));
        }
        tblPayment.setItems(obList);
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void btnOnActionPendingPayments(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PendingPayments.fxml"));
        Parent rootNode = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(rootNode));
        stage.centerOnScreen();
        stage.setTitle("AddPayment Form");

        stage.show();
    }


    public void PaymentNoOnKeyPressed(KeyEvent keyEvent) {
        System.out.println("aaaaaaaaaa");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String paymentNo = comBoxPaymentNo.getEditor().getText();
            int index = -1;

            // Ensure there is a valid name entered in the ComboBox
            if (paymentNo != null && !paymentNo.trim().isEmpty()) {
                for (int i = 0; i < tblPayment.getItems().size(); i++) {
                    System.out.println(colPaymentNo.getCellData(i));
                    if (colPaymentNo.getCellData(i) == comBoxPaymentNo.getValue()) {
                        index = i;
                        break;
                    }
                }

            }

            if(colPaymentNo.getCellData(index)==comBoxPaymentNo.getValue() ) {
                System.out.println(colAdvance.getCellData(index));
                txtAdvance.setText((String) colAdvance.getCellData(index));
                txtAmountToBePay.setText((String) colAmountToBePay.getCellData(index));
                txtAreaDescription.setText((String) colDescription.getCellData(index));
                txtDate.setText((String) colDate.getCellData(index));
            }else {

            }
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String paymentNo = comBoxPaymentNo.getEditor().getText().trim();
            int index = -1;

            if (!paymentNo.isEmpty()) {
                for (int i = 0; i < tblPayment.getItems().size(); i++) {
                    String cellPaymentNo = (String) colPaymentNo.getCellData(i);
                    System.out.println(cellPaymentNo);
                    if (cellPaymentNo.equals(paymentNo)) {
                        index = i;
                        break;
                    }
                }
            }

            if (index != -1) {
                System.out.println(colAdvance.getCellData(index));
                txtAdvance.setText(String.valueOf(colAdvance.getCellData(index)));
                txtAmountToBePay.setText(String.valueOf(colAmountToBePay.getCellData(index)));
                txtAreaDescription.setText(String.valueOf(colDescription.getCellData(index)));
                txtDate.setText(String.valueOf(colDate.getCellData(index)));
                comBoxType.setValue((String) colPaymentType.getCellData(index));
            } else {
                new Alert(Alert.AlertType.WARNING, "No record found!").show();
            }
        }

    }

    public void PaymentNoOnKeyReleased(KeyEvent keyEvent) throws SQLException {
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredContact = comBoxPaymentNo.getEditor().getText();

        List<String> stringList = PaymentRepo.getPaymentIds();

        try {

            for (String no : stringList) {
                if (no.toLowerCase().contains(enteredContact.toLowerCase())) {
                    filterData.add(no);
                }
            }


            comBoxPaymentNo.setItems(filterData);
            comBoxPaymentNo.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
