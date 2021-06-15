import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController implements Initializable {
    
    @FXML
    AnchorPane pane, gameOver1, gameOver2;

    @FXML 
    ImageView blackView, mapImageView, playerImage1, playerImage2;

    @FXML
    Label bombNumber1, bombNumber2, blastRange1, blastRange2, speed1, speed2, heart1, heart2, label;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Main.beginPlayer.stop();
        Main.gamePlayer.play();

        mapImageView.setImage(new Image("/resources/Images/map1.png"));

        playerImage1.setImage(new Image("/resources/Images/character"+SelectController.playerNumber1+".png"));
        if(GameView.isTwoPlayer) playerImage2.setImage(new Image("/resources/Images/character"+SelectController.playerNumber2+".png"));

        // fade in
        pane.getChildren().remove(blackView);
        pane.getChildren().add(blackView);

        new AnimationTimer(){
            double previousTime = 0;
            int delay=0, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0)  previousTime = now;       
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity(delay+1-(now-previousTime)/1.0e9);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    blackView.setVisible(false);
                    stop();
                }
            }
        }.start();
    }


    // back button
    public void back(ActionEvent e) throws IOException {
        Main.beginPlayer.play();
        Main.gamePlayer.stop();

        GameView.gameLoop.stop(); 

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/selectScreen.fxml"));
        Main.stage.setScene(new Scene(root));

        SelectController.playerNumber1 = 0;
        SelectController.playerNumber2 = 0;
    }

    // One player restart
    public void restart1() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        Parent root = loader.load();

        new GameView(loader.getController(), SelectController.playerNumber1);
        Main.stage.setScene(new Scene(root));
    }

    // Two players restart
    public void restart2() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        new GameView(loader.getController(), SelectController.playerNumber1, SelectController.playerNumber2);
        Main.stage.setScene(new Scene(root));
    }

    // Back to menu
    public void menu() throws IOException {
        Main.beginPlayer.play();
        Main.gamePlayer.stop();

        GameView.gameLoop.stop();

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        Main.stage.setScene(new Scene(root));

        SelectController.playerNumber1 = 0;
        SelectController.playerNumber2 = 0;
    } 

}
