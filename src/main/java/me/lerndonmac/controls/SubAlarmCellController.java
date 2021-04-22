package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.IOException;

public class SubAlarmCellController {
    private ObservableList<Integer> hours = FXCollections.observableArrayList();
    private ObservableList<Integer> minets = FXCollections.observableArrayList();
    @FXML
    private TextField nameText;
    @FXML
    private ComboBox<Integer> hoursebox;
    @FXML
    private ComboBox<Integer> minutesBox;
    @FXML
    private Button deleteButt;

    private SubAlarm localSubAlarm;
    private static Alarms localAlarm;

    @FXML
    public void initialize(){

    }
    private void initButts(){
        nameText.setOnAction(actionEvent -> {
            localAlarm.getSubAlarms().remove(localSubAlarm);
            localSubAlarm.setName(nameText.getText());
            localAlarm.getSubAlarms().add(localSubAlarm);
            initCell(localSubAlarm, localAlarm);
        });
        deleteButt.setOnAction(actionEvent -> {
            localAlarm.getSubAlarms().remove(localSubAlarm);
            reloadWin();
        });
        hoursebox.setOnAction(actionEvent -> {
            localAlarm.getSubAlarms().remove(localSubAlarm);
            localSubAlarm.setHours(hoursebox.getValue());
            localAlarm.getSubAlarms().add(localSubAlarm);
            initCell(localSubAlarm, localAlarm);
        });
        minutesBox.setOnAction(actionEvent -> {
            localAlarm.getSubAlarms().remove(localSubAlarm);
            localSubAlarm.setMinutes(minutesBox.getValue());
            localAlarm.getSubAlarms().add(localSubAlarm);
            initCell(localSubAlarm, localAlarm);
        });
    }
    private void reloadWin(){
        minutesBox.getScene().getWindow().hide();
        SubAlarmEditeController.setAlarm(localAlarm);
        Stage stage = new Stage();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/subAlarmEditWin.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/clokesImg.png")));
            stage.setTitle("Sub alarms");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initCell(SubAlarm sub, Alarms alarm){
        localAlarm = alarm;
        localSubAlarm = sub;
        initClocks();
        initButts();
        if (!sub.isNull()) {
            nameText.setText(localSubAlarm.getName());
            hoursebox.setValue(localSubAlarm.getTimeOfCall().getHours());
            minutesBox.setValue(localSubAlarm.getTimeOfCall().getMinutes());
        }
    }
    private void initClocks(){
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        for (int i = 0; i < 60; i++) {
            minets.add(i);
        }
        hoursebox.setItems(hours);
        minutesBox.setItems(minets);
    }
}
