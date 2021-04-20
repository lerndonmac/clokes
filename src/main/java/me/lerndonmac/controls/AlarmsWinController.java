package me.lerndonmac.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import me.lerndonmac.model.Alarms;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class AlarmsWinController {

    public Button addBut;
    private int countOfAlarms;

    private BufferedReader reader;
    private FileWriter writer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TilePane tilePane;
    ObservableList<Alarms> alarmsObserv = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws InterruptedException {
        LoadThread thread = new LoadThread();
        thread.start();
        while (thread.isAlive()) {
            Thread.sleep(100);
        }
        initCels();
        scrollPane.getContent().getScene().getWindow().setOnCloseRequest(windowEvent -> {
            exitBull = true;
            exitEvent();
        });
        addBut.setOnAction(addEvent -> {

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
    private void initList() {
        if (!alarmsObserv.isEmpty()){
            alarmsObserv.clear();
    }
        try {
            reader = new BufferedReader(new FileReader(getClass().getResource("/alarms/alarmlist0.alttx").getFile()));
            while (reader.ready()) {
                countOfAlarms++;
                String alarm = reader.readLine();
                String[] alarmParams = alarm.split("'");

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                Date date = new Date();
                date.setHours(sdf.parse(alarmParams[1]).getHours());
                date.setMinutes(sdf.parse(alarmParams[1]).getMinutes());
                Alarms alarms = new Alarms(alarmParams[0], date, Boolean.getBoolean(alarmParams[2]));
                if (!alarmParams[3].isEmpty()&&alarmParams[3]!=null){
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

    public void initCels(){
        for (Alarms alarm : alarmsObserv){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cell.fxml"));
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
