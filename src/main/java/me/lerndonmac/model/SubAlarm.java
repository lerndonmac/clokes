package me.lerndonmac.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubAlarm {

    private String name;

    private Date timeOfCall;

    private Boolean active;

    public SubAlarm(String name, Date timeOfCall) {
        this.name = name;
        this.timeOfCall = timeOfCall;
    }

    public boolean isNull(){
        return timeOfCall == null&&name == null;
    }


    public String getTimeForOut(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(getTimeOfCall());
    }
    public void setHours(Integer hour){
        timeOfCall.setHours(hour);
    }

    public void setMinutes(Integer minutes){
        timeOfCall.setMinutes(minutes);
    }

    @Override
    public String toString() {
        return getName();
    }
}
