import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class BeginController {

    @FXML
    ImageView blackView;


    long previousTime = 0;
    public AnimationTimer fadeOutTimer;
    public AnimationTimer fadeInTimer;

    
    public void onePlayer(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/selectScreen.fxml"));
        Parent root = loader.load();

        SelectController controller = loader.getController();

        this.screenFadeOut(0, 1, root);
        controller.screenFadeIn(1, 1);
    }

    public void twoPlayer(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/selectScreen.fxml"));
        Parent root = loader.load();

        SelectController controller = loader.getController();

        this.screenFadeOut(0, 1, root);
        controller.screenFadeIn(1, 1);
    }

    public void exit(ActionEvent e) {
        System.exit(0);
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
