package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWinController {


    @FXML
    private AnchorPane winPane;
    @FXML
    private Label openSetingsText;
    @FXML
    private Label openAlarmsText;
    @FXML
    private Label openAboutText;
    @FXML
    private Label exitText;

    @FXML
    public void initialize(){
        initButtons();
        initStyles();
    }
    private void initButtons(){ // yes Buttons
        openAlarmsText.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/alarmsList.fxml"));

                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/clokesImg.png")));
                stage.setTitle("alarms");
                stage.show();
                AlarmsWinController.setLocalController(loader.getController());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openSetingsText.setOnMouseClicked(mouseEvent ->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/setingsWin.fxml"));
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/clokesImg.png")));
                stage.setTitle("Setings");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openAboutText.setOnMouseClicked(mouseEvent ->{});

        exitText.setOnMouseClicked(mouseEvent -> exitText.getScene().getWindow().hide());
    }

    private void initStyles(){ //Yes it is here wow
        Shadow shadowEffect = new Shadow();
        shadowEffect.setBlurType(BlurType.GAUSSIAN);
        shadowEffect.setHeight(0);
        shadowEffect.setWidth(5.93);
        shadowEffect.setColor(Color.color(0.5, 0, 1.0));
        openAboutText.setOnMouseEntered(mouseEvent -> openAboutText.setEffect(shadowEffect));//#2f00ff
        openAboutText.setOnMouseExited(mouseEvent -> openAboutText.setEffect(null) );//#9277ff

        openSetingsText.setOnMouseEntered(mouseEvent -> {openSetingsText.setEffect(shadowEffect);});
        openSetingsText.setOnMouseExited(mouseEvent -> {openSetingsText.setEffect(null);});

        openAlarmsText.setOnMouseEntered(mouseEvent -> {openAlarmsText.setEffect(shadowEffect);});
        openAlarmsText.setOnMouseExited(mouseEvent -> {openAlarmsText.setEffect(null);});

        exitText.setOnMouseEntered(mouseEvent ->{
            shadowEffect.setColor(Color.color(1,0,0));
            exitText.setEffect(shadowEffect); });
        exitText.setOnMouseExited(mouseEvent -> {
            shadowEffect.setColor(Color.color(0.5, 0, 1.0));
            exitText.setEffect(null);
        } );
    }

}

