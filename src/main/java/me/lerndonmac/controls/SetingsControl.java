package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import me.lerndonmac.model.UserSetings;

import java.io.*;

public class SetingsControl {
    @FXML
    private AnchorPane winPane;        @FXML
    //Shut Down Nodes
    private Text shutDownActivator;      @FXML
    private TextArea shutDownRealize;    @FXML
    private CheckBox shutDownChek;    @FXML
    private ChoiceBox<Integer> shutDownMinetsTxt;    @FXML
    private ChoiceBox<Integer> shutDownHoursTxt;    @FXML
    //eyeProtection Nodes
    private Text eyeProtectionActivator;@FXML
    private TextArea eyeProtectionRealize;    @FXML
    private CheckBox eyeProtectionChek;    @FXML
    private TextField eyeProtectionTxt;      @FXML
    //start up Nodes
    private Text startUpActivator;            @FXML
    private TextArea startUpRealize;    @FXML
    private CheckBox startUpChek;              @FXML

    private static UserSetings localSetings;
    public void initialize() throws InterruptedException {
        initHelp();
        easterEggMoment();
        initBoxes();
        LoadThread load = new LoadThread();
        load.start();
        while (load.isAlive()){
            Thread.sleep(100);
        }
        initWindow();
        initChanges();
    }
    private void initWindow(){
        this.shutDownChek.setSelected(localSetings.getShutDownActive());
        this.shutDownMinetsTxt.setValue(localSetings.getShutDownTime().getMinutes());
        this.shutDownHoursTxt.setValue(localSetings.getShutDownTime().getHours());

        this.eyeProtectionChek.setSelected(localSetings.getNotificationActive());
        this.eyeProtectionTxt.setText(String.valueOf(localSetings.getNotificationMinutesInterval()));

        this.startUpChek.setSelected(localSetings.getStartUpActive());
    }
    private void initBoxes(){
        for (int i = 0; i < 24; i++) {
            shutDownHoursTxt.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            shutDownMinetsTxt.getItems().add(i);
        }
    }
    private void initHelp(){
        eyeProtectionActivator.setOnMouseEntered(mouseEvent -> {
            eyeProtectionRealize.setVisible(true);
        });
        eyeProtectionActivator.setOnMouseExited(mouseEvent -> {
            eyeProtectionRealize.setVisible(false);
        });
        startUpActivator.setOnMouseEntered(mouseEvent -> {
            startUpRealize.setVisible(true);
        });
        startUpActivator.setOnMouseExited(mouseEvent -> {
            startUpRealize.setVisible(false);
        });
        shutDownActivator.setOnMouseEntered(mouseEvent -> {
            shutDownRealize.setVisible(true);
        });
        shutDownActivator.setOnMouseExited(mouseEvent -> {
            shutDownRealize.setVisible(false);
        });
    }
    private static void initSettings(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SetingsControl.class.getResource("/config/setings.cfg.alttx").getFile()));
            String setings =  reader.readLine();
            localSetings = new UserSetings(setings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initChanges(){
        DeployThread deploadThread = new DeployThread();
        this.shutDownChek.selectedProperty().addListener(((observableValue, aBoolean, newB) -> {
            localSetings.setShutDownActive(newB);
           updateSettings();
        }));
        this.shutDownMinetsTxt.valueProperty().addListener((observableValue, integer, newInt) -> {
            localSetings.getShutDownTime().setMinutes(newInt);
           updateSettings();
        });
        this.shutDownHoursTxt.valueProperty().addListener((observableValue, integer, newInt) -> {
            localSetings.getShutDownTime().setHours(newInt);
           updateSettings();
        });

        this.eyeProtectionChek.selectedProperty().addListener((observableValue, aBoolean, newB) ->{
            localSetings.setNotificationActive(newB);
           updateSettings();
        } );
        this.eyeProtectionTxt.setOnAction(actionEvent -> {
            localSetings.setNotificationMinutesInterval(Integer.parseInt(eyeProtectionTxt.getText()));
           updateSettings();
        });

        this.startUpChek.selectedProperty().addListener((observableValue, aBoolean, newB) -> {
            localSetings.setStartUpActive(newB);
           updateSettings();
        });
    }
    private static void updateSettings(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SetingsControl.class.getResource("/config/setings.cfg.alttx").getFile()))){
            writer.write(localSetings.toFileStr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class DeployThread extends Thread{
        @Override
        public void run(){
            updateSettings();
        }
    }
    private static class LoadThread extends Thread{
        @Override
        public void run(){
            initSettings();
        }
    }
























    @FXML
    private Arc easterEgg;
    @FXML
    private TextArea easterEggRealize;
    @FXML
    private Circle easterEggActivator;
    private void easterEggMoment(){
        easterEggActivator.setOnMouseEntered(mouseEvent -> {
            easterEgg.setVisible(true);
            easterEggRealize.setVisible(true);
        });
        easterEggActivator.setOnMouseExited(mouseEvent -> {
            easterEgg.setVisible(false);
            easterEggRealize.setVisible(false);
        });

    }
}
