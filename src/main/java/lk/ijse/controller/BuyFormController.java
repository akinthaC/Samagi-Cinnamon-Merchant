package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BuyFormController {

    @FXML
    private TableColumn<?, ?> colBuyingPrice;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colCuttingAmount;

    @FXML
    private TableColumn<?, ?> colProductType;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private JFXComboBox<String> comBoxContact;

    @FXML
    private JFXComboBox<String> comBoxName;

    @FXML
    private Label lblNetWeight;

    @FXML
    private Label lblProductType;

    @FXML
    private AnchorPane paneType;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtCuttingAmount;

    @FXML
    private TextField txtWeight;

    public void initialize() throws IOException {
        comBoxContact.setEditable(true);
        comBoxName.setEditable(true);
    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {
        String CusName = comBoxName.getValue();
        String CusContact = comBoxContact.getValue();
        String Weight = txtWeight.getText();
        String CuttingAmount = txtCuttingAmount.getText();
        String BuyingPrice = txtBuyingPrice.getText();
        String ProductType = lblProductType.getText();

        System.out.println("name"+ comBoxName.getValue());


    }

    @FXML
    void btnOnActionCinnamon(ActionEvent event) {


    }

    @FXML
    void btnOnActionOther(ActionEvent event) {

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void comBoxOnActionContact(ActionEvent event) {


    }

    @FXML
    void comBoxOnActionName(ActionEvent event) throws SQLException {
        ObservableList<String> filterData = FXCollections.observableArrayList();
        String enteredName = comBoxName.getEditor().getText();

        List<String> stringList = SupplierRepo.searchSupplierNmae();

        boolean status;

        do {

            try {

                for (String name : stringList) {
                    if (name.contains(enteredName)) {
                        filterData.add(name);
                    }
                }
                comBoxName.setItems(filterData);
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
            status= false;

        } while (status);{
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Customer not found \n Do you want add Customer", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    System.out.println("Okkkk");
                }else {
                    System.out.println("nooo");
                }
            }

    }


}

