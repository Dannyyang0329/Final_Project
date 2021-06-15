import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage stage;

    public static MediaPlayer beginPlayer;
    public static MediaPlayer gamePlayer;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Loading Musics
        Media beginMusic = new Media(new File("src\\resources\\Musics\\beginMusic.mp3").toURI().toString());
        beginPlayer = new MediaPlayer(beginMusic);
        beginPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        beginPlayer.setVolume(0.25);
        beginPlayer.play();

        Media gameMusic = new Media(new File("src\\resources\\Musics\\gameMusic.mp3").toURI().toString());
        gamePlayer = new MediaPlayer(gameMusic);
        gamePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        gamePlayer.setVolume(0.25);
        gamePlayer.stop();



        stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        Parent root = loader.load();
        
        stage.setScene(new Scene(root, 1200, 800));
        stage.setTitle("Bomb King");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/resources/Images/bombIconImage.png"));

        stage.show();
    }
}