package me.lerndonmac.controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import me.lerndonmac.alarmsLogic.SubAlarmLogic;

public class SubAlarmControl {
    @FXML
    private Button stopBut;
    @FXML
    public void initialize(){
        stopBut.setOnAction(stpoEvent -> {
            stopBut.getScene().getWindow().hide();
            SubAlarmLogic.runLogic();
        });
    }

}