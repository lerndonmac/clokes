package me.lerndonmac.alarmsLogic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.lerndonmac.model.UserSetings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NotificationLogic extends Application {
    private static UserSetings notifSeting;
    public static Stage localStage;

    public static void startNotifLogic(){
        System.out.println("notif start");
        initSetings();

        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(notifSeting.getNotificationMinutesInterval() * 60000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(NotificationLogic::showStage);
        }); // отдельный поток чтобы не останавливать fx поток при исспользовании таймера
        timerThread.setName("timerThread");
        timerThread.start();
    }
    private static void initSetings(){
        try(BufferedReader reader = new BufferedReader(new FileReader(NotificationLogic.class.getResource("/config/setings.cfg.alttx").getFile()));) {
            String[] setingProperties = reader.readLine().split("'");

            notifSeting = new UserSetings();

            //active parsing
            notifSeting.setNotificationActive(setingProperties[1].equals("1"));

            notifSeting.setNotificationMinutesInterval(Integer.parseInt(setingProperties[0]));//interval parsing

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startNotifWin(){
        Parent root;
        try {
            root = FXMLLoader.load(NotificationLogic.class.getResource("/view/notifView.fxml"));
            localStage.setScene(new Scene(root));
            localStage.setTitle("Notification");
            localStage.getIcons().addAll(new Image(NotificationLogic.class.getResourceAsStream("/images/clokesImg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void showStage() {// start win method
        if (localStage != null) {
            startNotifWin();
            localStage.show();
            localStage.toFront();
        }
    }

    @Override
    public void start(Stage stage) {

    }
}
