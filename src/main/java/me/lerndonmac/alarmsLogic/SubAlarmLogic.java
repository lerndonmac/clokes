package me.lerndonmac.alarmsLogic;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

public class SubAlarmLogic {
    private static Stage localStage;

    private static Alarms localAlarm;

    private static HashSet<SubAlarm> subAlarms;

    public static void setLocalAlarm(Alarms localAlarm) {
        SubAlarmLogic.localAlarm = localAlarm;
    }

    public static void runLogic(){

        subAlarms = localAlarm.getSubAlarms();
        for (SubAlarm sub : subAlarms){
            if ((sub.getTimeOfCall().getTime() - new Date().getTime() > 0)&&(sub.getActive())){
                Thread logicThread = new Thread(()->{
                    try {
                        Thread.sleep(sub.getTimeOfCall().getTime() - new Date().getTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(SubAlarmLogic::showStage);
                });
                logicThread.start();
            }
        }

    }


    private static void startAlarmWin(){
        Parent root;
        try {
            root = FXMLLoader.load(NotificationLogic.class.getResource("/view/subAlarmView.fxml"));
            localStage.setScene(new Scene(root));
            localStage.setTitle("по рассписанию");
            localStage.getIcons().addAll(new Image(NotificationLogic.class.getResourceAsStream("/images/clokesImg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void showStage() {// start win method
        if (localStage != null) {
            startAlarmWin();
            localStage.show();
            localStage.toFront();
        }
    }

}
