package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.IOException;
import java.util.HashSet;

public class SubAlarmEditeController {
    private static Alarms localAlarm;
    private HashSet<SubAlarm> subAlarms = new HashSet<>();
    @FXML
    private TilePane tilePane;
    @FXML
    private Button addButt;

    public static void setAlarm(Alarms alarm){
        localAlarm = alarm;
    }


    @FXML
    public void initialize(){
        subAlarms = localAlarm.getSubAlarms();
        initCels(localAlarm);
        addButt.setOnAction(actionEvent -> {
            localAlarm.getSubAlarms().add(new SubAlarm());
            initCels(localAlarm);
        });
    }

    public void initCels(Alarms alarm){
        System.out.println("start init subs cells from :"+alarm.getSubAlarms());
        if (!tilePane.getChildren().isEmpty()){
            tilePane.getChildren().clear();
        }
        if (alarm.getSubAlarms() != null) {
            for (SubAlarm sub : alarm.getSubAlarms()) {
                    System.out.println("new sub cell");
                    FXMLLoader loader = new FXMLLoader(AlarmsWinController.class.getResource("/view/subAlarmCell.fxml"));
                    try {
                        Parent parent = loader.load();
                        SubAlarmCellController cellController = loader.getController();
                        cellController.initCell(sub, localAlarm);
                        System.out.println(sub);

                        tilePane.getChildren().add(parent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }else System.out.println(alarm);

    }


}
