package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import me.lerndonmac.model.Alarms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AddAlarmController {
    @FXML
    private TextField alarmNameText;
    @FXML
    private TextField alarmQuestText;
    private ObservableList<Integer> hoursList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Integer> alarmHoursBox;
    private ObservableList<Integer> minutesList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Integer> alarmMinutesBox;
    @FXML
    private Button addButt;
    @FXML
    public void initialize(){
        minutesList = initIntBox(60);
        alarmMinutesBox.setItems(minutesList);
        hoursList = initIntBox(24);
        alarmHoursBox.setItems(hoursList);
        addButt.setOnAction(actionEvent -> createAlarm());
    }
    public ObservableList<Integer> initIntBox(Integer countOfDig){
        /**
         *  fill list by digitalis from 0 to (count of dig) -1
         **/
        ObservableList<Integer> outList = FXCollections.observableArrayList();
        for (int i = 0; i < countOfDig; i++) {
            outList.add(i);
        }
        return outList;
    }
    private void createAlarm(){
        Alarms alarm = new Alarms();
        alarm.setName(alarmNameText.getText());
        alarm.setQuestion(alarmQuestText.getText());
        Date time = new Date();
        time.setHours(alarmHoursBox.getValue());
        time.setMinutes(alarmMinutesBox.getValue());
        alarm.setTime(time);
        alarm.setActive(true);
        if (alarm.getTime()!=null&&!alarm.getName().isEmpty()&&!alarm.getQuestion().isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(getClass().getResource("/alarms/alarmlist0.alttx").getFile()))) {
                writer.write(alarm.toString()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            alarmNameText.setText("Введите все параметры");
            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
