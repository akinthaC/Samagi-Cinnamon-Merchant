package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardFormController {


    public AnchorPane MainPain;

    public void btnOnActionBuy(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/BuyForm.fxml"));
        AnchorPane newPane = loader.load();


        MainPain.getChildren().clear();


        MainPain.getChildren().add(newPane);
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);


        newPane.setOpacity(1);

    }
}
