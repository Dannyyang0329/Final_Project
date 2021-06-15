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

    // One Player
    public void onePlayer(ActionEvent e) throws IOException {

        GameView.isOnePlayer = true;

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/selectScreen.fxml"));

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=0, duration=1;

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
                    stop();
                }
            }
        }.start();
    }

    // Two Player
    public void twoPlayer(ActionEvent e) throws IOException {

        GameView.isTwoPlayer = true;

        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/selectScreen.fxml"));

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=0, duration=1;

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
                    stop();
                }
            }
        }.start();
    }

    // exit button
    public void exit(ActionEvent e) {
        System.exit(0);
    } 
}