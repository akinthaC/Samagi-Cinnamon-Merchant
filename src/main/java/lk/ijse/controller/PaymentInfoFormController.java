package lk.ijse.controller;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lk.ijse.Db.DbConnection;
import lk.ijse.model.PaymentInfo;
import lk.ijse.repository.OrdersRepo;
import lk.ijse.repository.PaymentInfoRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
    String cusName;

    String payAmount1;

    public void initialize() {
        supID = BuyFormController.supplierID;
        cusName = BuyFormController.cusName;
        lblDate.setText(String.valueOf(LocalDate.now()));
        getPaymentMethods();
        getCurrentPaymentNo();
        lblOrderNo.setText(BuyFormController.orId);
        lblTotalAmount.setText(BuyFormController.totalAmount);
        setTime();


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
            payAmount1 = String.valueOf(toBePaAmount);

            String status = "pending";
            if (toBePaAmount==0.00){
                status.equals("complete");
            }
            PaymentInfo paymentInfo = new PaymentInfo( paymentNo,supID, totalAmount, date, payAmount, toBePaAmount, paymentType,description, status);

            try {
                boolean isSaved = PaymentInfoRepo.save(paymentInfo);
                if (isSaved){
                    printBill();
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed & payment successfully!").show();
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
     String time;
    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            LocalTime currentTime = LocalTime.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = currentTime.format(timeFormatter);
            time=formattedTime;

        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);

        clock.play();
    }

    private void printBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/SupBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        Map<String,Object> data = new HashMap<>();
        data.put("NetTotal",lblTotalAmount.getText());
        data.put("OrId",lblOrderNo.getText());
        data.put("date",lblDate.getText());
        data.put("Time",time);
        data.put("id",lblOrderNo.getText());
        data.put("Advance",txtPayAmount.getText());
        data.put("ToBePayPayment",payAmount1);
        data.put("CusName",cusName);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

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

        return "001";
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
