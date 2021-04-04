package me.lerndonmac.model;

import lombok.*;

import java.io.File;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Alarms {
@NonNull
    private String name;
@NonNull
    private Date time;
    private String soundPath;
@NonNull
    private Boolean active;

public String getSound(){
    File file = new File(getSoundPath());
    return file.getName();
}

    @Override
    public String toString() {
        return name +'\''+ time  +'\''+ soundPath  +'\''+ getActive() +"\n" ;
    }
}
