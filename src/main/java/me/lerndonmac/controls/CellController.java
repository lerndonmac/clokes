package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import me.lerndonmac.model.Alarms;

import java.text.SimpleDateFormat;

public class CellController {
    ObservableList<Integer> hours = FXCollections.observableArrayList();
    ObservableList<Integer> minets = FXCollections.observableArrayList();
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
    private TextField soundNameText;
    @FXML
    private Button selectSoundButt;
    @FXML
    private Button changeTimeButt;

    private Alarms alarm;


    public void initCell(Alarms alarm){
        initClocks();
        this.alarm = alarm;
        alarmNameText.setText(alarm.getName());
        activeChoseBox.setSelected(alarm.getActive());
//        soundNameText.setText(alarm.getSound());
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
