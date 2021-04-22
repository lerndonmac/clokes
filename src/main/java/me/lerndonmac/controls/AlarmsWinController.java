package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import me.lerndonmac.model.Alarms;
import me.lerndonmac.model.SubAlarm;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

public class AlarmsWinController {

    private static AlarmsWinController localController;

    public Button addBut;

    private BufferedReader reader;
    private FileWriter writer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TilePane tilePane;
    private static final ObservableList<Alarms> alarmsObserv = FXCollections.observableArrayList();

    public static ObservableList<Alarms> getAlarmsObserv() {
        return alarmsObserv;
    }

    public static void setLocalController(AlarmsWinController localController) {
        AlarmsWinController.localController = localController;
    }

    @FXML
    public void initialize() throws InterruptedException {
        LoadThread thread = new LoadThread();
        thread.start();
        while (thread.isAlive()) {
            Thread.sleep(100);
        }
        initCels(alarmsObserv);

        addBut.setOnAction(addEvent -> {
            Stage localStage = new Stage();
            Parent root;
            try {
                root = FXMLLoader.load(AlarmsWinController.class.getResource("/view/addMainAlarm.fxml"));
                localStage.setScene(new Scene(root));
                localStage.setTitle("create Alarm");
                localStage.getIcons().addAll(new Image(AlarmsWinController.class.getResourceAsStream("/images/clokesImg.png")));
                localStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    private void exitEvent(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getClass().getResource("/alarms/alarmlist0.alttx").getFile()));){
            for (Alarms alarms : alarmsObserv) {
                writer.write(alarms.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static HashSet<SubAlarm> subAlarms = new HashSet<>();
    private void initList() {
        if (!alarmsObserv.isEmpty()){
            alarmsObserv.clear();
    }
        try {
            reader = new BufferedReader(new FileReader(getClass().getResource("/alarms/alarmlist0.alttx").getFile()));
            while (reader.ready()) {
                String[] oneLine = reader.readLine().split(";");// {alarm}{sub}
                if (!oneLine.equals("")) {
                    String[] alarmParams = oneLine[0].split("'");

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                    Date date = new Date();
                    date.setHours(sdf.parse(alarmParams[1]).getHours());
                    date.setMinutes(sdf.parse(alarmParams[1]).getMinutes());
                    Alarms alarms = new Alarms(alarmParams[0], date, Boolean.getBoolean(alarmParams[2]));
                    if (!alarmParams[3].isEmpty() && alarmParams[3] != null) {
                        alarms.setQuestion(alarmParams[3]);
                    }
                    if (alarmParams[2].equals("1")) {
                        alarms.setActive(true);
                    }

                    if (oneLine.length>1){
                        String[] subsAlarms = oneLine[1].split("\\|");
                        subAlarms = new HashSet<>();
                        for (String subTxt : subsAlarms) {

                            String[] subParams = subTxt.split("'");//[subDefoult1][03:20]
                            subAlarms.add(new SubAlarm(subParams[0], sdf.parse(subParams[1])));
                        }
                        alarms.setSubAlarms(subAlarms);
                    }

                    alarmsObserv.add(alarms);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        alarmsObserv.sort(Comparator.comparing(Alarms::getTime));
    }


    public void initCels(ObservableList<Alarms> alarmsList){
        if (!tilePane.getChildren().isEmpty()){
            tilePane.getChildren().clear();
        }
        for (Alarms alarm : alarmsList){
            System.out.println("new cell");
            FXMLLoader loader = new FXMLLoader(AlarmsWinController.class.getResource("/view/cell.fxml"));
            try {
                Parent parent = loader.load();
                CellController cellController = loader.getController();
                cellController.initCell(alarm);
                System.out.println(alarm);

                tilePane.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    protected Boolean exitBull = false;
    public class LoadThread extends Thread{

        @Override
        public void run(){
                initList();
        }
    }

}
