package lk.ijse.controller;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;
import lk.ijse.repository.OrdersRepo;
import lk.ijse.repository.PaymentInfoRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentInfoFormController {

    @FXML
    private JFXComboBox<String> comBoxType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderNo;

    @FXML
    private Label lblPaymentNo;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtPayAmount;

    String supID;

    public void initialize() {
        supID = BuyFormController.supplierID;
        lblDate.setText(String.valueOf(LocalDate.now()));
        getPaymentMethods();
        getCurrentPaymentNo();
        lblOrderNo.setText(BuyFormController.orId);
        lblTotalAmount.setText(BuyFormController.totalAmount);

    }

    @FXML
    void btnOnActionPayNow(ActionEvent event) throws SQLException {
        /*String orderNo = lblOrderNo.getText();*/
        String paymentNo = lblPaymentNo.getText();
        String date = lblDate.getText();
        double totalAmount = Double.parseDouble(lblTotalAmount.getText());
        double payAmount = Double.parseDouble(txtPayAmount.getText());
        String description = txtAreaDescription.getText();
        String paymentType = comBoxType.getSelectionModel().getSelectedItem();

        if (payAmount <= totalAmount & payAmount > 0){

            double toBePaAmount = totalAmount - payAmount;

            String status = "pending";
            if (toBePaAmount==0.00){
                status.equals("complete");
            }
            PaymentInfo paymentInfo = new PaymentInfo( paymentNo,supID, totalAmount, date, payAmount, toBePaAmount, paymentType,description, status);

            try {
                boolean isSaved = PaymentInfoRepo.save(paymentInfo);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Payment info updated successfully").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Payment info update failed").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "please check the Pay amount and Total amounts").show();
        }

    }

    private void clearFields() {
        txtPayAmount.clear();
        txtAreaDescription.clear();
        comBoxType.getItems().clear();
    }


    private void getCurrentPaymentNo() {
        try {
            String currentNo = PaymentInfoRepo.getCurrentNo();
            String nextPayNo = generateNextPaymentNo(currentNo);
            lblPaymentNo.setText(nextPayNo); //set paymentNo

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPaymentNo(String currentNo) {

        if (currentNo != null) {

            // Use a regular expression to extract the numeric part
            String numericPart = currentNo.replaceAll("\\D+", ""); // Remove non-digit characters

            int idNum = Integer.parseInt(numericPart);

            return String.format("%03d", ++idNum); // Increment and format to three digits
        }

        return "0001";
    }

    private void getPaymentMethods() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Cash",
                "Credit Card",
                "Cheque"
        );

        comBoxType.setItems(obList);
    }

}
