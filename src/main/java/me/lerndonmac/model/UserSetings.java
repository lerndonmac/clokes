package me.lerndonmac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public String getTimeForOut(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(getShutDownTime());
    }

    public String toFileStr(){
        String notifActive = "0";
        String startActive = "0";
        String shutActive = "0";
        if (getNotificationActive()){notifActive = "1";} if (getStartUpActive()){startActive = "1";} if (getShutDownActive()){shutActive = "1";}
        return getNotificationMinutesInterval() + '\'' + notifActive
                + '\'' + startActive
                + '\'' + getTimeForOut() + '\'' + shutActive;
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
