package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

            for(String No : NoList) {
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

        for ( PaymentInfo pay: PaymentList){
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


    public void comBoxOnActionSearchPaymentNo(KeyEvent keyEvent) {
        /*if (keyEvent.getCode() == KeyCode.ENTER) {
            String selectedName = comBoxPaymentNo.getEditor().getText();

            // Ensure there is a valid name entered in the ComboBox
            if (selectedName != null && !selectedName.trim().isEmpty()) {
                try {
                    // Fetch the supplier details by name
                    List<String> supplier = PaymentRepo.nameSearch(selectedName);

                    if (supplier != null && supplier.size() > 1) {
                        // Set the supplier details to ComboBoxes or other UI elements
                        comBoxPaymentNo.setValue(supplier.get(1)); // Supplier contact
                        comBoxName.setValue(supplier.get(0)); // Supplier name
                    } else {
                        if(comBoxContact.getValue()!=null){
                            comBoxContact.setValue(null);
                        }
                        showSupplierNotFoundAlert();
                    }

                    // Trigger further actions based on the selected name
                    comBoxOnActionSearchName(new ActionEvent());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
