package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import lk.ijse.model.PlaceOrderSupplier;
import lk.ijse.model.Supplier;
import lk.ijse.model.SupplierItem;
import lk.ijse.model.tm.cartTm;
import lk.ijse.repository.ItemRepo;
import lk.ijse.repository.PlaceOrderSupplierRepo;
import lk.ijse.repository.SupplierItemRepo;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyFormController {

    @FXML
    private TableColumn<?, ?> colBuyingPrice;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colCuttingAmount;

    @FXML
    private TableColumn<?, ?> colProductType;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private TableColumn<?, ?> colNetWeight;

    @FXML
    private JFXComboBox<String> comBoxContact;

    @FXML
    private JFXComboBox<String> comBoxName;

    @FXML
    private Label lblOrderNo;


    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblNetWeight;

    @FXML
    private Label lblProductType;

    @FXML
    private AnchorPane paneType;

    @FXML
    private TableView<cartTm> tblCart;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtCuttingAmount;

    @FXML
    private TextField txtWeight;
    @FXML
    private AnchorPane buyPain;

    static String orId;
    static String totalAmount;

    private ObservableList<cartTm> obList = FXCollections.observableArrayList();

    public void initialize() throws IOException, SQLException {
        comBoxContact.setEditable(true);
        comBoxName.setEditable(true);
        setCellValueFactory();
        generateOrderId();

    }

    private void generateOrderId(){
        try {
            String currentId = SupplierItemRepo.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            lblOrderNo.setText(nextOrderId);
            orId = nextOrderId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null) {

            // Use a regular expression to extract the numeric part
            String numericPart = currentId.replaceAll("\\D+", ""); // Remove non-digit characters

            int idNum = Integer.parseInt(numericPart);

            return String.format("%03d", ++idNum); // Increment and format to three digits
        }

        return "0001";
    }


        private void setCellValueFactory() {
        colProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colNetWeight.setCellValueFactory(new PropertyValueFactory<>("netWeight"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCuttingAmount.setCellValueFactory(new PropertyValueFactory<>("cuttingAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {
        String CusName = comBoxName.getValue();
        String CusContact = comBoxContact.getValue();
        double Weight = Double.parseDouble(txtWeight.getText());
        double CuttingAmount = Double.parseDouble(txtCuttingAmount.getText());
        double BuyingPrice = Double.parseDouble(txtBuyingPrice.getText());
        String ProductType = lblProductType.getText();


        double cuttingWeight = (Weight * CuttingAmount)/100;
        double netWeight = Weight - cuttingWeight;
        double buyTotal = BuyingPrice * netWeight;




        System.out.println("name"+ comBoxName.getValue());

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setStyle("-fx-background-color: #e32323; -fx-text-fill: white; " +
                "-fx-background-radius: 30; -fx-shape: 'M 0 20 Q 0 0 20 0 L 80 0 Q 100 0 100 20 L 100 80 Q 100 100 80 100 L 20 100 Q 0 100 0 80 L 0 20 Z'; " +
                "-fx-border-color: black; -fx-border-width: 2;");

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblCart.refresh();

                calculateNetTotal();
            }
        });


        for (int i = 0; i < tblCart.getItems().size(); i++) {
            if(false) {
                System.out.println("noooo");
            }
        }

        cartTm cartTm = new cartTm(ProductType,Weight,netWeight,BuyingPrice,buyTotal,CuttingAmount,btnRemove);
        obList.add(cartTm);

        tblCart.setItems(obList);
        calculateNetTotal();
        clearFields();


    }

    private void clearFields() {
        txtWeight.setText(null);
        txtBuyingPrice.setText(null);
        txtCuttingAmount.setText("3");
        lblProductType.setText(null);
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
         lblTotalAmount.setText(String.valueOf(netTotal));

        lblNetWeight.setText(String.valueOf(netTotal));
        totalAmount = String.valueOf(netTotal);
    }

    @FXML
    void btnOnActionCinnamon(ActionEvent event) throws SQLException {
        if (paneType != null) {
            // Clear all child elements from the AnchorPane
            paneType.getChildren().clear();
        }
        String type = "cinnamon";
        List<String> itemList = ItemRepo.gtAllItems(type);

        double yPosition = 10.0; // Start y position for the buttons
        double xPosition = 10.0; // Start x position for the buttons
        double buttonHeight = 45.0; // Total height of a button including the gap between buttons
        double maxPaneHeight = 550.0; // Maximum height for one column of buttons

        for (String name : itemList) {
            Button button = new Button(name);
            button.setStyle(
                    "-fx-text-fill: black; " +               // Text color (white)
                            "-fx-font-size: 13px; " +                // Font size
                            "-fx-alignment: center; " +              // Center text
                            "-fx-font-weight: bold; " +              // Bold text
                            "-fx-pref-width: 150px; " +              // Button width
                            "-fx-pref-height: 35px; " +              // Button height
                            "-fx-border-radius: 10px; " +            // Rounded corners for the border
                            "-fx-background-radius: 10px; " +        // Rounded corners for the background
                            "-fx-background-color: #FFFFFF; " +      // Background color (green)
                            "-fx-border-color: #2E7D32; " +          // Border color (dark green)
                            "-fx-border-width: 2px;");  // Padding inside the button

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #f1c40f; " + // Hover color (orange)
                            "-fx-text-fill: black; " +               // White text color
                            "-fx-font-size: 14px; " +               // Same styles
                            "-fx-font-weight: bold; " +
                            "-fx-pref-width: 150px; " +
                            "-fx-pref-height: 37px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-background-radius: 5px; " +
                            "-fx-border-color: #2E7D32; " +
                            "-fx-border-width: 2px;"));

            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: white; " +  // Revert to initial color (green)
                            "-fx-text-fill:black; " +
                            "-fx-font-size: 13px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-pref-width: 150px; " +
                            "-fx-pref-height: 35px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-background-radius: 5px; " +
                            "-fx-border-color: #2E7D32; " +
                            "-fx-border-width: 2px;"));

            // Optionally, add an action to the button
            button.setOnAction(event1 -> {
                System.out.println("Button clicked: " + name);
                lblProductType.setText(name);
            });

            // Set the button's position in the AnchorPane
            AnchorPane.setTopAnchor(button, yPosition);
            AnchorPane.setLeftAnchor(button, xPosition); // Set x position

            // Add the button to the AnchorPane
            paneType.getChildren().add(button);

            // Increase the yPosition for the next button
            yPosition += buttonHeight;

            // Check if yPosition exceeds the maxPaneHeight (height of the pane), reset for a new column
            if (yPosition >= maxPaneHeight) {
                yPosition = 10.0;  // Reset yPosition for the next column
                xPosition += 170.0;  // Move xPosition to the right for the next column
            }
        }

    }

    @FXML
    void btnOnActionOther(ActionEvent event) throws SQLException {
        if (paneType != null) {
            // Clear all child elements from the AnchorPane
            paneType.getChildren().clear();
        }
        String type = "other";
        List<String> itemList = ItemRepo.gtAllItems(type);

        double yPosition = 10.0; // Start y position for the buttons
        double xPosition = 10.0; // Start x position for the buttons
        double buttonHeight = 45.0; // Total height of a button including the gap between buttons
        double maxPaneHeight = 550.0; // Maximum height for one column of buttons

        for (String name : itemList) {
            Button button = new Button(name);
            button.setStyle(
                    "-fx-text-fill: black; " +               // Text color (white)
                            "-fx-font-size: 13px; " +                // Font size
                            "-fx-alignment: center; " +              // Center text
                            "-fx-font-weight: bold; " +              // Bold text
                            "-fx-pref-width: 150px; " +              // Button width
                            "-fx-pref-height: 35px; " +              // Button height
                            "-fx-border-radius: 10px; " +            // Rounded corners for the border
                            "-fx-background-radius: 10px; " +        // Rounded corners for the background
                            "-fx-background-color: #FFFFFF; " +      // Background color (green)
                            "-fx-border-color: #2E7D32; " +          // Border color (dark green)
                            "-fx-border-width: 2px;");  // Padding inside the button

            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: #079992; " + // Hover color (orange)
                            "-fx-text-fill: black; " +               // White text color
                            "-fx-font-size: 14px; " +               // Same styles
                            "-fx-font-weight: bold; " +
                            "-fx-pref-width: 150px; " +
                            "-fx-pref-height: 37px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-background-radius: 5px; " +
                            "-fx-border-color: #2E7D32; " +
                            "-fx-border-width: 2px;"));

            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: white; " +  // Revert to initial color (green)
                            "-fx-text-fill:black; " +
                            "-fx-font-size: 13px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-pref-width: 150px; " +
                            "-fx-pref-height: 35px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-background-radius: 5px; " +
                            "-fx-border-color: #2E7D32; " +
                            "-fx-border-width: 2px;"));

            // Optionally, add an action to the button
            button.setOnAction(event1 -> {
                System.out.println("Button clicked: " + name);
                lblProductType.setText(name);
            });

            // Set the button's position in the AnchorPane
            AnchorPane.setTopAnchor(button, yPosition);
            AnchorPane.setLeftAnchor(button, xPosition); // Set x position

            // Add the button to the AnchorPane
            paneType.getChildren().add(button);

            // Increase the yPosition for the next button
            yPosition += buttonHeight;

            // Check if yPosition exceeds the maxPaneHeight (height of the pane), reset for a new column
            if (yPosition >= maxPaneHeight) {
                yPosition = 10.0;  // Reset yPosition for the next column
                xPosition += 170.0;  // Move xPosition to the right for the next column
            }
        }
    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) throws IOException, SQLException {
        List<String> stringList = new ArrayList<>();
        List<SupplierItem> odList = new ArrayList<>();
        List odList1 = new ArrayList<>();
        List sup = new ArrayList<>();


        String SupId = SupplierRepo.searchSupplierId(comBoxContact.getValue());

        String name;
        String contact;
        boolean isFlag;
        if (SupId == null) {
            name = comBoxName.getValue();
            contact = comBoxContact.getValue();
            sup.add(name);
            sup.add(contact);
            isFlag = true;
            System.out.println(isFlag);
        } else{

            isFlag = false;
        System.out.println(isFlag);
    }


            for (int i = 0; i < tblCart.getItems().size(); i++) {
                cartTm tm = obList.get(i);

                String productId = ItemRepo.getId(tm.getType());
                System.out.println(productId + tm.getType());
                String orId = lblOrderNo.getText();
                Double price = tm.getBuyPrice();
                Date date = Date.valueOf(LocalDate.now());
                double weight = tm.getWeight();
                double netWeight = tm.getNetWeight();

                SupplierItem supplierItem = new SupplierItem(SupId, productId , price, weight, date, netWeight,orId);
                odList.add(supplierItem);

                odList1.add(productId);
                odList1.add(tm.getType());

            }
            try {

                PlaceOrderSupplier placeOrderSupplier = new PlaceOrderSupplier(odList, odList1,sup);
                boolean isPlaced = PlaceOrderSupplierRepo.orderSupplier(placeOrderSupplier,comBoxContact.getValue(),comBoxName.getValue(),isFlag);
                if (isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                    
                    paymentInfo();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BuyForm.fxml"));
                    AnchorPane contentPane = loader.load();

                    // Add the loaded content to the main pane
                    buyPain.getChildren().clear();
                    buyPain.getChildren().add(contentPane);


                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }

    private void paymentInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentInfoForm.fxml"));
        Parent rootNode = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(rootNode));
        stage.centerOnScreen();
        stage.setTitle("AddPayment Form");

        stage.show();
    }


    @FXML
    void comBoxOnActionContact(KeyEvent event) throws SQLException {
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredContact = comBoxContact.getEditor().getText();

        List<String> stringList = SupplierRepo.searchSupplierContact();

        try {

            for (String name : stringList) {
                if (name.toLowerCase().contains(enteredContact.toLowerCase())) {
                    filterData.add(name);
                }
            }


            comBoxContact.setItems(filterData);
            comBoxContact.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comBoxOnActionName(KeyEvent event) throws SQLException {
        // Clear the current items in the ComboBox for filtered results
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredName = comBoxName.getEditor().getText();

        // Fetch the supplier names from the repository
        List<String> stringList = SupplierRepo.searchSupplierNmae();

        try {
            // Filter the supplier names based on the entered name
            for (String name : stringList) {
                if (name.toLowerCase().contains(enteredName.toLowerCase())) {
                    filterData.add(name);
                }
            }

            // Update the ComboBox with filtered names and display the dropdown
            comBoxName.setItems(filterData);
            comBoxName.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Handle pressing the ENTER key

    }


    public void comBoxOnActionSearchName(ActionEvent actionEvent) throws SQLException {



    }



    public void comBoxOnActionSearchName1(KeyEvent keyEvent) throws SQLException {
       /* String selectedName = comBoxName.getValue();

        // Fetch supplier details by name
        List<String> supplier = SupplierRepo.nameSearch(selectedName);

        if (supplier != null && supplier.size() > 1) {
            // Set the supplier details to ComboBoxes or other UI elements
            comBoxContact.setValue(supplier.get(1)); // Supplier contact
            comBoxName.setValue(supplier.get(0)); // Supplier name
        } else {
            showSupplierNotFoundAlert();
        }*/

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String selectedName = comBoxName.getEditor().getText();

            // Ensure there is a valid name entered in the ComboBox
            if (selectedName != null && !selectedName.trim().isEmpty()) {
                try {
                    // Fetch the supplier details by name
                    List<String> supplier = SupplierRepo.nameSearch(selectedName);

                    if (supplier != null && supplier.size() > 1) {
                        // Set the supplier details to ComboBoxes or other UI elements
                        comBoxContact.setValue(supplier.get(1)); // Supplier contact
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
        }
    }

    // Helper method to show an alert if the supplier is not found
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

    public void comBoxOnActionSearchContact1(KeyEvent keyEvent) throws SQLException {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String selectedContact = comBoxContact.getEditor().getText();

            // Ensure there is a valid name entered in the ComboBox
            if (selectedContact != null && !selectedContact.trim().isEmpty()) {
                try {
                    // Fetch the supplier details by name
                    List<String> supplier = SupplierRepo.SuplierSearchByContact(selectedContact);

                    if (supplier != null && supplier.size() > 1) {
                        // Set the supplier details to ComboBoxes or other UI elements
                        comBoxContact.setValue(supplier.get(1)); // Supplier contact
                        comBoxName.setValue(supplier.get(0)); // Supplier name
                    } else {
                        if(comBoxName.getValue()!=null){
                            comBoxName.setValue(null);
                        }
                        showSupplierNotFoundAlert();
                    }

                    // Trigger further actions based on the selected name
                    comBoxOnActionSearchName(new ActionEvent());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

