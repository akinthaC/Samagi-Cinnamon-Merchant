package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeFormController {

    @FXML
    private JFXButton btnBuy;

    @FXML
    private JFXButton btnSell;
    @FXML
    private AnchorPane HomePain;

    @FXML
    void btnOnActionBuy(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BuyForm.fxml"));
        AnchorPane contentPane = loader.load();

        // Add the loaded content to the main pane
        HomePain.getChildren().clear();
        HomePain.getChildren().add(contentPane);


    }

    @FXML
    void btnOnActionSell(ActionEvent event) {

    }

}
