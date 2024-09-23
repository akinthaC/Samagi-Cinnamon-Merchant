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

    public void initialize() {
        lblDate.setText(String.valueOf(LocalDate.now()));
        getPaymentMethods();
        //getCurrentPaymentNo();
        lblOrderNo.setText(BuyFormController.orId);
        lblTotalAmount.setText(String.valueOf(BuyFormController.totalAmount));
    }

    @FXML
    void btnOnActionPayNow(ActionEvent event) throws SQLException {
        String orderNo = lblOrderNo.getText();
        String supID = getSupplierId(lblOrderNo);
        String paymentNo = lblPaymentNo.getText();
        String date = lblDate.getText();
        double totalAmount = Double.parseDouble(lblTotalAmount.getText());
        double payAmount = Double.parseDouble(txtPayAmount.getText());
        String description = txtAreaDescription.getText();

        if (payAmount <= totalAmount & payAmount > 0){

            PaymentInfo paymentInfo = new PaymentInfo(supID, orderNo, paymentNo, date, totalAmount, payAmount, description);

            try {
                boolean isSaved = PaymentInfoRepo.save(paymentInfo);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Payment info updated successfully").show();
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

    private String getSupplierId(Label lblOrderNo) throws SQLException {
        String sql = "select id from supplierItem where orderNo = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, lblOrderNo.getText());
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }


    private void getCurrentPaymentNo() {
        try {
            String currentNo = PaymentInfoRepo.getCurrentNo();

            String nextPayNo = generateNextPaymentNo(currentNo);
            lblPaymentNo.setText(nextPayNo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPaymentNo(String currentNo) {

        if(currentNo != null) {

            String[] split = currentNo.split("[oO]");

            int idNum = Integer.parseInt(split[1]);

            return "P" + String.format("%03d", ++idNum);

        }

        return "P001";
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
