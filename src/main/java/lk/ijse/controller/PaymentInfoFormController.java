package lk.ijse.controller;

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
import lk.ijse.model.PaymentInfo;
import lk.ijse.repository.OrdersRepo;
import lk.ijse.repository.PaymentInfoRepo;

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
        //setOrderNo For The Payment Info page
        lblOrderNo.setText(BuyFormController.orId);
    }

    @FXML
    void btnOnActionPayNow(ActionEvent event) {
        String orderNo = lblOrderNo.getText();
        String paymentNo = lblPaymentNo.getText();
        String date = lblDate.getText();
        String totalAmount = lblTotalAmount.getText();
        double payAmount = Double.parseDouble(txtPayAmount.getText());
        String description = txtAreaDescription.getText();

        PaymentInfo paymentInfo = new PaymentInfo(orderNo, paymentNo, date, totalAmount, payAmount, description);

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
