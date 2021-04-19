import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.lerndonmac.alarmsLogic.AlarmLogic;
import me.lerndonmac.alarmsLogic.NotificationLogic;
import me.lerndonmac.controls.AlarmViewController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    public static Stage stage;//it's too
    private AlarmLogic logic = new AlarmLogic();

    public void startProgramm() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/startWin.fxml"));
            stage.setScene(new Scene(root));
            Platform.setImplicitExit(false);
            stage.setTitle("start win");
            stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/images/clokesImg.png")));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }// it's all to start window
    public static void startWindow(String[] args) {

        launch(args);
    }// it's all to start window
    //-what, it's was possible? -maybe, but it's work
    private void initTrey() {
        try {
            startProgramm();
            java.awt.Toolkit.getDefaultToolkit();
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("loh");
                Platform.exit();
            }
            //init access to system tray
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            java.awt.Image image = ImageIO.read(getClass().getResourceAsStream("/images/clokesImg.png"));
            java.awt.TrayIcon icon = new java.awt.TrayIcon(image);
            tray.add(icon);

            //init right button menu
            java.awt.MenuItem openItemTray = new java.awt.MenuItem("ModuleKloces");
            //open window from tray yea
            openItemTray.addActionListener(o -> Platform.runLater(this::showStage));
            java.awt.MenuItem exitItemTray = new java.awt.MenuItem("Exit");
            //close PROGRAM from TRAY
            exitItemTray.addActionListener(e -> {
                javafx.application.Platform.exit();
                System.exit(1);
                tray.remove(icon);
            });

            java.awt.PopupMenu itemMenu = new PopupMenu();
            itemMenu.add(openItemTray);
            itemMenu.addSeparator();
            itemMenu.add(exitItemTray);

            icon.setPopupMenu(itemMenu);
        }catch (java.awt.AWTException | IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(NotificationLogic::startNotifLogic);
        NotificationLogic.localStage = stage;
        Platform.runLater(logic::runLogic);
        AlarmLogic.localStage = stage;
    }
    private void showStage() {//combining javafx and java awt; it's can to named so
        if (stage != null) {
            startProgramm();
            stage.show();
            stage.toFront();
        }
    }


    public void start(Stage stage) {
        Main.stage = stage;
        initTrey();
    }// it's all to start window
    public static void startAlarm(Stage stage) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/alarmView.fxml"));
        AlarmViewController controller = loader.getController();
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

}
