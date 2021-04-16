package me.lerndonmac;

import me.lerndonmac.model.Alarms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

public class Console {
    private static List<Alarms> alarmsObserv = new ArrayList<>();

    public static void main(String[] args) {
      initList();
    }
    public static void initList() {
      assert !alarmsObserv.isEmpty();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\projects\\DP\\ModeuleKloces\\src\\main\\resources\\alarms\\alarmlist0.alttx"));
            while (reader.ready()) {
                String alarm = reader.readLine();
                String[] alarmParams = alarm.split("'");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                date.setHours(sdf.parse(alarmParams[1]).getHours());
                date.setMinutes(sdf.parse(alarmParams[1]).getMinutes());
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
        for (Alarms alarms: alarmsObserv) {
            System.out.println(alarms.getTime());
        }
    }

}
