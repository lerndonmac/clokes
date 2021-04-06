package me.lerndonmac.model;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Getter@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Alarms {
@NonNull
    private String name;
@NonNull
    private Date time;
@NonNull
    private Boolean active;
    private String question;
    private Set<SubAlarm> subAlarms;

    public String getTimeForOut(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(getTime());
    }


    @Override
    public String toString() {
        String active;
        if (getActive())active = "1";
        else active = "0";
        return getName() +'\''+ getTimeForOut() +'\''+ active +"\n" ;
    }
}
