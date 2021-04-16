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
    public void initialize() throws InterruptedException {
        initHelp();
        easterEggMoment();
        initBoxes();
        LoadThread load = new LoadThread();
        load.start();
        while (load.isAlive()){
            Thread.sleep(100);
        }
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

    static class LoadThread extends Thread{
        @Override
        public void run(){


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
