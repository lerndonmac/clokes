package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

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

    private static Alarms localAlarm;


    public void initCell(Alarms alarm){
        initClocks();
        localAlarm = alarm;
        initSubList();
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
    private static Set<SubAlarm> subAlarms = new HashSet<>();
    private static void initSubList(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SubAlarm.class.getResource("/alarms/subAlarmsList0.alttx").getFile()));
            while (reader.ready()){
                String oneLine = reader.readLine(); //defoult1;subDefoult1'03:20|subDefoult2'04:00
                String alarmName = oneLine.split(";")[0];//defoult1

                if (localAlarm.getName().equals(alarmName)) {
                    String[] subsAlarms = oneLine.split(";")[1].split("\\|");//[subDefoult1'03:20][subDefoult2'04:00]
                    subAlarms = new HashSet<>();

                    for (String subTxt : subsAlarms) {

                        String[] subParams = subTxt.split("'");//[subDefoult1][03:20]
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        subAlarms.add(new SubAlarm(subParams[0], sdf.parse(subParams[1])));
                    }
                }
            }
            System.out.println(subAlarms);
            localAlarm.setSubAlarms(subAlarms);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
