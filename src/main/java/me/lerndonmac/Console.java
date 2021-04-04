package me.lerndonmac;

import me.lerndonmac.model.Alarms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Console {
    public static void main(String[] args) {
        Alarms alarms = new Alarms("defoult",new Date(), Boolean.TRUE);
        Console console = new Console();
        console.write(alarms);
    }
    public void write(Alarms alarms){
        try {
            FileWriter writer = new FileWriter("D:\\projects\\ModeuleKloces\\src\\main\\resources\\alarms\\alarmlist.txt");
            writer.write(alarms.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
