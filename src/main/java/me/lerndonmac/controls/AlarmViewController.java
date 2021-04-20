package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import me.lerndonmac.alarmsLogic.SubAlarmLogic;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.util.concurrent.atomic.AtomicBoolean;

public class AlarmViewController {
    private static Alarms localAlarm;
    @FXML
    private Button yesButt;@FXML
    private Button noButt;@FXML
    private Label alarmNameLable;@FXML
    private Label alarmTimeLable;@FXML
    private Label alarmQuestionLable;

    @FXML
    public void initialize(){
        alarmNameLable.setText(localAlarm.getName());
        alarmQuestionLable.setText(localAlarm.getQuestion());
        alarmTimeLable.setText(localAlarm.getTimeForOut());
        initButtons();
    }
    private void initButtons(){
        AtomicBoolean whatToConfirm = new AtomicBoolean();

        noButt.setOnAction(actionEvent -> {
            for (SubAlarm sub:localAlarm.getSubAlarms()){
                sub.setActive(false);

            }
            whatToConfirm.set(false);
            alarmQuestionLable.setText("Вы уверенны?");
            confirmInit(whatToConfirm);

        });

        yesButt.setOnAction(actionEvent -> {
            for (SubAlarm sub:localAlarm.getSubAlarms()){
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
                for (SubAlarm sub : localAlarm.getSubAlarms()) {
                    sub.setActive(false);
                }

            });
        }else {
            noButt.setOnAction(actionEvent -> {
                for (SubAlarm sub : localAlarm.getSubAlarms()) {
                    sub.setActive(true);
                }
                alarmQuestionLable.setText(localAlarm.getQuestion());

            });
        }
        yesButt.setOnAction(actionEvent -> {
            SubAlarmLogic.setLocalAlarm(localAlarm);
            SubAlarmLogic.runLogic();
            yesButt.getScene().getWindow().hide();});
    }

    public static void setAlarm(Alarms alarm) {
        localAlarm = alarm;
    }
}
