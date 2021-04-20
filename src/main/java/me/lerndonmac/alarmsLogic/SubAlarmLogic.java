package me.lerndonmac.alarmsLogic;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SubAlarmLogic {
    private static Stage localStage;

    private static Alarms localAlarm;


    public static void setLocalAlarm(Alarms localAlarm) {
        SubAlarmLogic.localAlarm = localAlarm;
    }

    public static void runLogic(){
        initSubList();

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
