import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/startWin.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("start win");
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/images/clokesImg.png")));
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}
