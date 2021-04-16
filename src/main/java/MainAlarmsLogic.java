import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.tk.quantum.QuantumToolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.lerndonmac.controls.AlarmViewController;
import me.lerndonmac.model.Alarms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainAlarmsLogic extends Application {
    private List<Alarms> alarmsObserv = new ArrayList<>();
    private Alarms alarm;
    private static Timer timerToAlarm = new Timer();
    private Stage stage = new Stage();

    public void runLogic(){

        System.out.println("alarmLogicStart");
        initList();
        for (Alarms alarm:alarmsObserv) {
            if ((alarm.getTime().getTime() - new Date().getTime() > 0)&&(alarm.getActive())) {
                TimerThread timer = new TimerThread(alarm);
                timer.start();
                System.out.println(alarm);
                while (timer.isAlive()){

                }
                this.alarm = alarm;
                start(stage);
                runLogic();
            }
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
                if (alarmParams[2].equals("1")) {
                    alarms.setActive(true);
                }
                alarmsObserv.add(alarms);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        alarmsObserv.sort(Comparator.comparing(Alarms::getTime));
        System.out.println(alarmsObserv);
    }

    @Override
    public void start(Stage stage) {
        AlarmViewController.alarm = this.alarm;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/alarmView.fxml"));
        AlarmViewController controller = loader.getController();
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }
    private static class TimerThread extends Thread{
        private Alarms alarm;

        public TimerThread(Alarms alarm) {
            this.alarm = alarm;
        }
        @Override
        public void run(){
            final boolean[] whait = {false};
            timerToAlarm.schedule(new TimerTask() {
                @Override
                public void run() {
                    whait[0] = true;
                }
            }, alarm.getTime().getTime() - new Date().getTime());
            while (!whait[0]){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Alarm");
        }
    }
}
