package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.lerndonmac.model.Alarms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class CellController {
    private final ObservableList<Integer> hours = FXCollections.observableArrayList();
    private final ObservableList<Integer> minets = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Integer> hoursCombo;
    @FXML
    private ComboBox<Integer> minetsCombo;
    @FXML
    private CheckBox activeChoseBox;
    @FXML
    private Button deleteButt;
    @FXML
    private TextField alarmNameText;
    @FXML
    private Button editSubAlarmsButt;

    private Alarms localAlarm;

    @FXML
    public void initialize(){
        initButs();

    }
    private void initButs(){
        deleteButt.setOnAction(actionEvent -> {
            AlarmsWinController.getAlarmsObserv().remove(localAlarm);
            update(AlarmsWinController.getAlarmsObserv());
            reloadWin();
        });
        activeChoseBox.setOnAction(actionEvent -> {
            AlarmsWinController.getAlarmsObserv().remove(localAlarm);
            localAlarm.setActive(!localAlarm.getActive());
            AlarmsWinController.getAlarmsObserv().add(localAlarm);
            update(AlarmsWinController.getAlarmsObserv());
            reloadWin();

        });
        hoursCombo.setOnAction((event) -> {
            AlarmsWinController.getAlarmsObserv().remove(localAlarm);
            localAlarm.setHours(hoursCombo.getValue());
            AlarmsWinController.getAlarmsObserv().add(localAlarm);
            update(AlarmsWinController.getAlarmsObserv());
            reloadWin();

        });
        minetsCombo.setOnAction((event) -> {
            AlarmsWinController.getAlarmsObserv().remove(localAlarm);
            localAlarm.setMinutes(minetsCombo.getValue());
            AlarmsWinController.getAlarmsObserv().add(localAlarm);
            update(AlarmsWinController.getAlarmsObserv());
            reloadWin();

        });
        alarmNameText.setOnAction((actionEvent) -> {
            AlarmsWinController.getAlarmsObserv().remove(localAlarm);
            localAlarm.setName(alarmNameText.getText());
            AlarmsWinController.getAlarmsObserv().add(localAlarm);
            update(AlarmsWinController.getAlarmsObserv());
            reloadWin();

        });
        editSubAlarmsButt.setOnAction(actionEvent -> {
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

        });
    }
    private void reloadWin(){
        hoursCombo.getScene().getWindow().hide();
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
    }
    private void update(ObservableList<Alarms> alarmsList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CellController.class.getResource("/alarms/alarmlist0.alttx").getFile()))){
            for (Alarms alarm : alarmsList){
                writer.write(alarm.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initCell(Alarms alarm){
        initClocks();
        localAlarm = alarm;

        alarmNameText.setText(alarm.getName());
        activeChoseBox.setSelected(alarm.getActive());
        SimpleDateFormat sdfH = new SimpleDateFormat("HH");
        SimpleDateFormat sdfM = new SimpleDateFormat("mm");
        for (Integer hour:hours){
            if ((Integer.parseInt(sdfH.format(alarm.getTime())) == hour)) {
                hoursCombo.setValue(hour);
            }
        }
        for(Integer minet:minets){
            if ((Integer.parseInt(sdfM.format(alarm.getTime())) == minet)){
                minetsCombo.setValue(minet);
            }
        }
    }
    private void initClocks(){
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        for (int i = 0; i < 60; i++) {
            minets.add(i);
        }
        hoursCombo.setItems(hours);
        minetsCombo.setItems(minets);
    }

}
