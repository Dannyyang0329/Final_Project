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

public class SelectController {

    private final static int TOTAL_LEVEL = 1;

    @FXML
    Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;

    @FXML
    ImageView blackView;

    private static Image mapImage[] = new Image[TOTAL_LEVEL+1];

    long previousTime;
    AnimationTimer fadeOutTimer;
    AnimationTimer fadeInTimer;

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        Main.stage.setScene(new Scene(root));
    }

    public void click1() throws IOException {
        label1.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 1);
        controller.playerImage1.setImage(new Image("/resources/Images/character1.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click2() throws IOException {
        label2.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 2);
        controller.playerImage1.setImage(new Image("/resources/Images/character2.png")); 
        
        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click3() throws IOException {
        label3.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();

        new GameView(randomMap(controller), controller.pane, 3);
        controller.playerImage1.setImage(new Image("/resources/Images/character3.png")); 
        
        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click4() throws IOException {
        label4.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();

        new GameView(randomMap(controller), controller.pane, 4);
        controller.playerImage1.setImage(new Image("/resources/Images/character4.png")); 
        
        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click5() throws IOException {
        label5.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 5);
        controller.playerImage1.setImage(new Image("/resources/Images/character5.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click6() throws IOException {
        label6.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 6);
        controller.playerImage1.setImage(new Image("/resources/Images/character6.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click7() throws IOException {
        label7.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 7);
        controller.playerImage1.setImage(new Image("/resources/Images/character7.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click8() throws IOException {
        label8.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 8);
        controller.playerImage1.setImage(new Image("/resources/Images/character8.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click9() throws IOException {
        label9.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 9);
        controller.playerImage1.setImage(new Image("/resources/Images/character9.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public void click10() throws IOException {
        label10.setVisible(true); 
        blackView.setVisible(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        
        new GameView(randomMap(controller), controller.pane, 10);
        controller.playerImage1.setImage(new Image("/resources/Images/character10.png")); 

        this.screenFadeOut(2, 1, root);
        controller.screenFadeIn(3, 1);
    }

    public int randomMap(GameController control) {

        int randomNum = (int)(Math.random()*TOTAL_LEVEL)+1;

        if(mapImage[1] == null) {
            mapImage[1] = new Image("/resources/Images/map1.png");
            // mapImage[2] = new Image("/resources/Images/map2.png");
            // mapImage[3] = new Image("/resources/Images/map3.png");
        }

        control.mapImageView.setImage(mapImage[randomNum]);

        return randomNum;
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