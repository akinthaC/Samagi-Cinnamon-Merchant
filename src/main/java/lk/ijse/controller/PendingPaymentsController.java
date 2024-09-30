package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.PaymentInfo;
import lk.ijse.model.tm.PendingPaymentTm;
import lk.ijse.model.tm.SupplierSearchTm;
import lk.ijse.repository.PaymentInfoRepo;
import lk.ijse.repository.PaymentRepo;
import lk.ijse.repository.PendingPaymentRepo;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PendingPaymentsController {
    @FXML
    public TableView<SupplierSearchTm> tblSupplierSearch;

    @FXML
    private TableColumn<?, ?> colTotalAmountSupplier;

    @FXML
    private TableColumn<?, ?> colAmountToBePaySupplier;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colAdvance;

    @FXML
    private TableColumn<?, ?> colAmountToBePay;

    @FXML
    private TableColumn<?, ?> colPaymentNo;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    public JFXComboBox comBoxSupplierContact;

    @FXML
    public Label lblAmountToBePay;

    @FXML
    private JFXComboBox<?> comBoxOrderNo;

    @FXML
    private JFXComboBox<String> comBoxPaymentNo;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TableView<PendingPaymentTm> tblPendingPayments;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtDate;

    List<PaymentInfo> paymentDetails;

    public void initialize() throws SQLException {
        comBoxSupplierContact.setEditable(true);
        comBoxPaymentNo.setEditable(true);
        txtDate.setText(String.valueOf(LocalDate.now()));
        getPendingPayments();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colPaymentNo.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("payAmount")); //this is for advance
        colAmountToBePay.setCellValueFactory(new PropertyValueFactory<>("toBePaidAmount"));
    }

    private void getPendingPayments() throws SQLException {

        // ObservableList to hold parsed payment data
        ObservableList<PendingPaymentTm> obList = FXCollections.observableArrayList();

        // Fetch the payment data (currently in string format)
        List<String> pendingPaymentsAll = PendingPaymentRepo.getPendingPaymentsAll();

        // Loop through the string list and extract relevant data
        for (String payment : pendingPaymentsAll) {
            // Split the string to extract values
            String[] splitData = payment.split(", ");

            // Extract fields
            String paymentNo = splitData[0].split(": ")[1];
            double totalAmount = Double.parseDouble(splitData[1].split(": ")[1]);
            double payAmount = Double.parseDouble(splitData[2].split(": ")[1]);
            double toBePaidAmount = Double.parseDouble(splitData[3].split(": ")[1]);

            // Create a new PendingPaymentTm object and add it to the ObservableList
            obList.add(new PendingPaymentTm(paymentNo, totalAmount, payAmount, toBePaidAmount));
        }

        // Set the items in the TableView
        tblPendingPayments.setItems(obList);
    }

    @FXML
    void btnOnActionPayAdvance(ActionEvent event) {

        try {
            if (txtAdvance.getText().isEmpty() || txtDate.getText().isEmpty() || comBoxPaymentNo.getValue().isEmpty()) {
                new Alert(Alert.AlertType.ERROR,"please check your payment details!",ButtonType.OK).show();
            }
            else {

                String paymentNo = comBoxPaymentNo.getValue();
                Double advance = Double.valueOf(txtAdvance.getText());
                String date = txtDate.getText();
                Double amountToBePay = Double.valueOf(lblAmountToBePay.getText());
                Double newAmountToBePay = amountToBePay-advance;

                try {
                    if (amountToBePay >= advance) {
                        //update payment-info
                        boolean isUpdated = PendingPaymentRepo.updatePendingPaymentInfo(paymentNo, advance, date, newAmountToBePay);
                        if (isUpdated) {
                            txtAdvance.setText("");
                            lblAmountToBePay.setText("");
                            lblTotalAmount.setText("");
                            getPendingPayments();
                            setCellValueFactory();
                            new Alert(Alert.AlertType.INFORMATION,"Pay Advance successfully!",ButtonType.OK).show();

                            tblSupplierSearch.refresh();
                            colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                            colTotalAmountSupplier.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
                            colAmountToBePaySupplier.setCellValueFactory(new PropertyValueFactory<>("toBePaAmount"));
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Advance not paid!",ButtonType.OK).show();
                        }

                    }else {
                        new Alert(Alert.AlertType.ERROR,"Can't pay advance > amountToBePay",ButtonType.OK).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void comBoxOnActionSearchPaymentNo(ActionEvent keyEvent) throws SQLException {

        String selectedPaymentNo = comBoxPaymentNo.getEditor().getText();

        List<PaymentInfo> allPayments = PaymentInfoRepo.getAllPayments(selectedPaymentNo);
        System.out.println(allPayments);

        try {
            String supID = allPayments.get(0).getPaymentNo();
            System.out.println(supID);
            if (supID == null) {
                comBoxSupplierContact.setValue("");
            }else {
                System.out.println(supID);
                String contact = SupplierRepo.searchSupplierContactForPaymentInfo(supID);
                System.out.println(contact);
                double totalAmount = allPayments.get(0).getTotalAmount();
                double toBePaAmount = allPayments.get(0).getToBePaAmount();

                comBoxSupplierContact.setValue(contact);
                lblTotalAmount.setText(String.valueOf(totalAmount));
                lblAmountToBePay.setText(String.valueOf(toBePaAmount));
            }
        } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void comBoxOnActionSearchContact(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String selectedContact = comBoxSupplierContact.getEditor().getText();


            // Ensure there is a valid name entered in the ComboBox
            if (selectedContact != null && !selectedContact.trim().isEmpty()) {
                try {
                    // Fetch the supplier details by contact
                    List<String> supplier = SupplierRepo.SupplierSearchByContactforPendinngPayments(selectedContact);
                    System.out.println(supplier);
                    if (supplier != null && supplier.size() > 1) {
                        // Set the supplier details to ComboBoxes or other UI elements
                        String supplierId = supplier.get(0);// Assuming supplier ID is at index 0
                        String supplierName = supplier.get(1);//name
                        String supplierContact = supplier.get(3); // Assuming supplier name is at index 1

                        comBoxSupplierContact.setValue(supplierContact);

                        // Fetch the payment number using supplier ID
                        paymentDetails = PaymentRepo.getPaymentNoBySupplierId(supplierId);
                        System.out.println(paymentDetails);


                        // Set the payment number in the payment ComboBox
                        if (paymentDetails != null && !paymentDetails.isEmpty()) {

                            ObservableList<SupplierSearchTm> obList = FXCollections.observableArrayList();

                            List<PaymentInfo> supplierStockDetail = paymentDetails;

                            for (PaymentInfo No : supplierStockDetail) {
                                obList.add(new SupplierSearchTm(
                                        No.getPaymentNo(),
                                        No.getSupID(),
                                        No.getTotalAmount(),
                                        No.getDate(),
                                        No.getPayAmount(),
                                        No.getToBePaAmount(),
                                        No.getPaymentType(),
                                        No.getDescription()
                                ));
                            }

                            tblSupplierSearch.setItems(obList);
                            colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                            colTotalAmountSupplier.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
                            colAmountToBePaySupplier.setCellValueFactory(new PropertyValueFactory<>("toBePaAmount"));

                        } else {
                            // Handle case where no payment number is found
                            comBoxPaymentNo.setValue(null);
                            showPaymentNotFoundAlert();
                        }

                    } else {
                        if (comBoxPaymentNo.getValue() != null) {
                            comBoxPaymentNo.setValue(null);
                        }
                        showSupplierNotFoundAlert();
                    }

                    // Trigger further actions based on the selected name
                    comBoxOnActionSearchPaymentNo(new ActionEvent());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void OnMouseClicked(MouseEvent mouseEvent) {
        int index = tblSupplierSearch.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        comBoxPaymentNo.setValue(paymentDetails.get(index).getPaymentNo());
        lblAmountToBePay.setText(String.valueOf(paymentDetails.get(index).getToBePaAmount()));
        lblTotalAmount.setText(String.valueOf(paymentDetails.get(index).getTotalAmount()));

    }

    public void comBoxOnActionPaymentNo(KeyEvent keyEvent) throws SQLException {
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredContact = comBoxPaymentNo.getEditor().getText();

        List<String> stringList = PendingPaymentRepo.searchPaymentNo();

        try {

            for (String name : stringList) {
                if (name.toLowerCase().contains(enteredContact.toLowerCase())) {
                    filterData.add(name);
                }
            }

            comBoxPaymentNo.setItems(filterData);
            comBoxPaymentNo.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void comBoxOnActionContact(KeyEvent keyEvent) throws SQLException {
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredContact = comBoxSupplierContact.getEditor().getText();

        List<String> stringList = SupplierRepo.searchSupplierContact();

        try {

            for (String name : stringList) {
                if (name.toLowerCase().contains(enteredContact.toLowerCase())) {
                    filterData.add(name);
                }
            }

            comBoxSupplierContact.setItems(filterData);
            comBoxSupplierContact.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showSupplierNotFoundAlert() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Show alert asking if user wants to add a new supplier
        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Supplier not found\nDo you want to add the supplier?", yes, no).showAndWait();
        if (type.orElse(no) == yes) {
            // Logic to add a new supplier
            System.out.println("Add new supplier");
        } else {
            System.out.println("Supplier not added");
        }
    }

    private void showPaymentNotFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Not Found");
        alert.setHeaderText(null);
        alert.setContentText("No payment number found for the selected supplier.");
        alert.showAndWait();
    }


}
