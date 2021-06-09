
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage stage;
    private Scene scene;
    private Parent root;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScene();
    }

    private void initializeScene() throws IOException {
        stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        root = loader.load();
        
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Bomb King");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/resources/Images/bombIconImage.png"));

        stage.show();
    }

}