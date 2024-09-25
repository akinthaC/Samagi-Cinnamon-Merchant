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
import javafx.stage.Stage;
import lk.ijse.model.PaymentInfo;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.repository.PaymentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private JFXComboBox<?> comBoxOrderNo;

    @FXML
    private JFXComboBox<?> comBoxPaymentNo;

    @FXML
    private JFXComboBox<?> comBoxType;

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
        getAllPayments();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supID"));
        colOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
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
                    pay.getOrderNo(),
                    pay.getSupID(),
                    pay.getDate(),
                    pay.getTotalAmount(),
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
}
