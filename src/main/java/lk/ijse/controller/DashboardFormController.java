package lk.ijse.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane MainPain;

    @FXML
    private AnchorPane rootAnchorPane;

    public void initialize() throws IOException {
        btnOnActionHome(new ActionEvent());
    }

    @FXML
    void btnOnActionEmployee(ActionEvent event) {

    }

    @FXML
    void btnOnActionHome(ActionEvent event) throws IOException {
        loadFormWithAtractiveAnimation("/view/HomeForm.fxml");


    }

    @FXML
    void btnOnActionItems(ActionEvent event) {

    }

    @FXML
    void btnOnActionStock(ActionEvent event) {

    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) {

    }

    private void loadFormWithAtractiveAnimation(String formPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formPath));
        AnchorPane newPane = loader.load();

        newPane.setOpacity(0);
        rootAnchorPane.getChildren().add(newPane);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), newPane);
        translateTransition.setFromX(newPane.getWidth());
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), newPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);


        ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(0.5), newPane);
        zoomIn.setFromX(0.5);
        zoomIn.setFromY(0.5);
        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);

        // Combine all transitions
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(translateTransition,fadeTransition,zoomIn);
        parallelTransition.setOnFinished(event -> {
            rootAnchorPane.getChildren().clear();
            rootAnchorPane.getChildren().add(newPane);
        });
        parallelTransition.play();
    }

    @FXML
    void btnOnActionPayment(ActionEvent event) {

    }
    @FXML
    void btnOnActionBuy(ActionEvent event) throws IOException {
        loadFormWithAtractiveAnimation("/view/BuyForm.fxml");

       /* FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/BuyForm.fxml"));
        AnchorPane newPane = loader.load();


        rootAnchorPane.getChildren().clear();


        rootAnchorPane.getChildren().add(newPane);
        AnchorPane.setTopAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);


        newPane.setOpacity(1);*/
    }

    @FXML
    void btnOnActionSell(ActionEvent event) {

    }
}

