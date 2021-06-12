import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {
    
    @FXML
    AnchorPane pane;

    @FXML 
    ImageView blackView, mapImageView, playerImage1, playerImage2;

    @FXML
    Label bombNumber1, bombNumber2, blastRange1, blastRange2, speed1, speed2, heart1, heart2;

    long previousTime;
    AnimationTimer fadeOutTimer;
    AnimationTimer fadeInTimer;

    // public Label getBombNumber1() {
    //     return bombNumber1;
    // }
    // public Label getBombNumber2() {
    //     return bombNumber2;
    // }
    // public Label getBlastRange1() {
    //     return blastRange1;
    // }
    // public Label getBlastRange2() {
    //     return blastRange2;
    // }
    // public Label getSpeed1() {
    //     return speed1;
    // }
    // public Label getSpeed2() {
    //     return speed2;
    // }
    // public Label getHeart1() {
    //     return heart1;
    // }
    // public Label getHeart2() {
    //     return heart2;
    // }

    public void back(ActionEvent e) throws IOException {
        GameView.gameLoop.stop(); 

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/SelectScreen.fxml"));
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
