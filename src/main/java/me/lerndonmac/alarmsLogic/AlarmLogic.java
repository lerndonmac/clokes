package me.lerndonmac.alarmsLogic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.lerndonmac.controls.AlarmViewController;
import me.lerndonmac.model.Alarms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlarmLogic extends Application {

        private static final List<Alarms> alarmsObserv = new ArrayList<>();
        private Alarms alarm;
        private static final Timer timerToAlarm = new Timer();

        public static Stage localStage;

        public void runLogic(){
            TimerThread timer = new TimerThread(new Alarms());
            System.out.println("alarmLogicStart");

            if (alarmsObserv.isEmpty()) {
                initList();
            }

            for (Alarms alarm:alarmsObserv) {
                if ((alarm.getTime().getTime() - new Date().getTime() > 0)&&(alarm.getActive())) {
                    System.out.println(alarm.getName()+" : started");
                    timer = new TimerThread(alarm);
                    timer.start();

                    this.alarm = alarm;
                    AlarmViewController.setAlarm(this.alarm);

                    break;
                }
            }
            if (!timer.isAlive()) {
                for (Alarms alarm : alarmsObserv) {
                    alarm.setTime(new Date(alarm.getTime().getTime() + 86400000));
                }
                runLogic();
            }
        }
        public void initList() {
            if (!alarmsObserv.isEmpty()){
                alarmsObserv.clear();
            }
            try {
                BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource("/alarms/alarmlist0.alttx").getFile()));
                while (reader.ready()) {
                    String alarm = reader.readLine();
                    String[] alarmParams = alarm.split("'");
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date date = new Date();
                    date.setHours(sdf.parse(alarmParams[1]).getHours());
                    date.setMinutes(sdf.parse(alarmParams[1]).getMinutes());
                    date.setSeconds(0);
                    Alarms alarms = new Alarms(alarmParams[0], date, Boolean.getBoolean(alarmParams[2]));
                    if (!alarmParams[3].isEmpty()){
                        alarms.setQuestion(alarmParams[3]);
                    }
                    if (alarmParams[2].equals("1")) {
                        alarms.setActive(true);
                    }
                    alarmsObserv.add(alarms);
                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            alarmsObserv.sort(Comparator.comparing(Alarms::getTime));
        }

        @Override
        public void start(Stage stage) {
            localStage = stage;
            initList();

        }
        private static void startAlarmWin(){
            Parent root = null;
            try {
                root = FXMLLoader.load(AlarmLogic.class.getResource("/view/alarmView.fxml"));
                localStage.setScene(new Scene(root));
                Platform.setImplicitExit(false);
                localStage.setTitle("Alarm");
                localStage.getIcons().addAll(new Image(AlarmLogic.class.getResourceAsStream("/images/clokesImg.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private static void showStage() {//combining javafx and java awt; it's can to named so // start win method
            if (localStage != null) {
                startAlarmWin();
                localStage.show();
                localStage.toFront();
            }
        }
        private static class TimerThread extends Thread{
            private final Alarms alarm;
            public TimerThread(Alarms alarm) {
                this.alarm = alarm;
            }
            @Override
            public void run(){
                System.out.println("timer to Alarm is come");

                timerToAlarm.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(AlarmLogic::showStage);//Platform.runLater в данной программе отвечает за, работу оконного приложения из иного потока к JAVAFX
                    }
                },alarm.getTime().getTime() - new Date().getTime());
            }
        }
}
