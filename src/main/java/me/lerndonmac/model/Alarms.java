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

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        String active;
        if (getActive())active = "1";
        else active = "0";
        String subs = "";
        if (getSubAlarms().isEmpty())
        {
            return getName() +'\''+ getTimeForOut() +'\''+ active +'\''+getQuestion()+"\n" ;
        }
        for (SubAlarm sub : getSubAlarms()){
            subs = subs + sub.getName() +'\''+ sub.getTimeForOut() + '"'; // "-между сабами ;-между Классами '-между полями
        }
        return getName() +'\''+ getTimeForOut() +'\''+ active +'\''+getQuestion()+';'+ subs + '\n' ;
    }
}
