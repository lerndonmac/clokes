package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import me.lerndonmac.alarmsLogic.SubAlarmLogic;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AlarmViewController {
    public static Alarms alarm;
    @FXML
    private Button yesButt;@FXML
    private Button noButt;@FXML
    private Label alarmNameLable;@FXML
    private Label alarmTimeLable;@FXML
    private Label alarmQuestionLable;@FXML
    public void initialize(){
        alarmNameLable.setText(alarm.getName());
        alarmQuestionLable.setText(alarm.getQuestion());
        alarmTimeLable.setText(alarm.getTimeForOut());
        initButtons();
    }
    private void initButtons(){
        AtomicBoolean whatToConfirm = null;
        noButt.setOnAction(actionEvent -> {
            for (SubAlarm sub:alarm.getSubAlarms()){
                sub.setActive(false);

            }
            whatToConfirm.set(false);
            alarmQuestionLable.setText("Вы уверенны?");
            confirmInit(whatToConfirm);

        });
        yesButt.setOnAction(actionEvent -> {
            for (SubAlarm sub:alarm.getSubAlarms()){
                sub.setActive(true);
            }
            whatToConfirm.set(true);
            alarmQuestionLable.setText("Вы уверенны?");
            confirmInit(whatToConfirm);
        });
    }
    private void confirmInit(AtomicBoolean whatToConfirm){
        if (whatToConfirm.get()) {
            noButt.setOnAction(actionEvent -> {
                for (SubAlarm sub : alarm.getSubAlarms()) {
                    sub.setActive(false);
                }

            });
        }else {
            noButt.setOnAction(actionEvent -> {
                for (SubAlarm sub : alarm.getSubAlarms()) {
                    sub.setActive(true);
                }

            });
        }
        yesButt.setOnAction(actionEvent -> {
            yesButt.getScene().getWindow().hide();
        });
    }

    public void setAlarm(Alarms alarm) {
        this.alarm = alarm;
    }
}
