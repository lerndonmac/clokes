package me.lerndonmac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class UserSetings {
    private int notificationMinutesInterval;
    private Boolean notificationActive;

    private Boolean startUpActive;

    private Date shutDownTime;
    private Boolean shutDownActive;

    public UserSetings(String settings) {//45'1'0'23:00'0
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.notificationMinutesInterval = Integer.parseInt(settings.split("'")[0]);
        this.notificationActive = settings.split("'")[1].equals("1");
        this.startUpActive = settings.split("'")[2].equals("1");
        try {
            Date date = new Date();
            date.setHours(sdf.parse(settings.split("'")[3]).getHours());
            date.setMinutes(sdf.parse(settings.split("'")[3]).getMinutes());
            this.shutDownTime = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.shutDownActive = settings.split("'")[4].equals("1");
    }

    public String getTimeForOut(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(getShutDownTime());
    }

    public String toFileStr(){
        String notifActive = "0";
        String startActive = "0";
        String shutActive = "0";
        if (getNotificationActive()){notifActive = "1";} if (getStartUpActive()){startActive = "1";} if (getShutDownActive()){shutActive = "1";}

        return getNotificationMinutesInterval() + "'" + notifActive
                + "'" + startActive
                + "'" + getTimeForOut() + "'" + shutActive;
    }
    @Override
    public String toString() {
        return "notification Minutes Interval=" + getNotificationMinutesInterval() +
                ", notification Active=" + getNotificationActive() +
                ", start Up Active=" + getStartUpActive() +
                ", shut Down Time=" + getShutDownTime() +
                ", shut Down Active=" + getShutDownActive();
    }
}
