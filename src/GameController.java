import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {
    
    @FXML
    AnchorPane pane, gameOver1, gameOver2;

    @FXML 
    ImageView blackView, mapImageView, playerImage1, playerImage2;

    @FXML
    Label bombNumber1, bombNumber2, blastRange1, blastRange2, speed1, speed2, heart1, heart2, label;

    long previousTime;
    AnimationTimer fadeOutTimer;
    AnimationTimer fadeInTimer;

    public void back(ActionEvent e) throws IOException {
        GameView.gameLoop.stop(); 

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/selectScreen.fxml"));
        Main.stage.setScene(new Scene(root));

        SelectController.playerNumber1 = 0;
        SelectController.playerNumber2 = 0;
    }

    public void restart1() throws IOException {
        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        controller.playerImage1.setImage(new Image("/resources/Images/character"+SelectController.playerNumber1+".png"));
        
        new GameView(SelectController.randomNum, controller, SelectController.playerNumber1);
        Main.stage.setScene(new Scene(root));
    }

    public void restart2() throws IOException {
        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        controller.playerImage1.setImage(new Image("/resources/Images/character"+SelectController.playerNumber1+".png"));
        controller.playerImage2.setImage(new Image("/resources/Images/character"+SelectController.playerNumber2+".png"));
        
        new GameView(SelectController.randomNum, controller, SelectController.playerNumber1, SelectController.playerNumber2);
        Main.stage.setScene(new Scene(root));
    }

    public void menu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        Main.stage.setScene(new Scene(root));
    }

    public void screenFadeOut(int delay, int duration, Parent root) {

        fadeOutTimer = new AnimationTimer(){

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    previousTime = 0;

                    Main.stage.setScene(new Scene(root));
                    fadeOutTimer.stop();
                }
            }
        };

        fadeOutTimer.start();
    }

    public void screenFadeIn(int delay, int duration) {

        pane.getChildren().remove(blackView);
        pane.getChildren().add(blackView);

        fadeInTimer = new AnimationTimer(){

            @Override
            public void handle(long now) {
                if(previousTime == 0)  previousTime = now;       
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity(delay+1-(now-previousTime)/1.0e9);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    previousTime = 0;

                    blackView.setVisible(false);
                    fadeInTimer.stop();
                }
            }
        };

        fadeInTimer.start();
    }
}
