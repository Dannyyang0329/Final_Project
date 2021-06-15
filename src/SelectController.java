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
import javafx.scene.image.ImageView;

public class SelectController implements Initializable {

    @FXML
    Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;

    @FXML
    ImageView blackView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        randomNum = (int)(Math.random()*TOTAL_LEVEL)+1;
        new AnimationTimer(){
            double previousTime = 0;
            int delay = 1, duration = 1;

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

    // There is only one map now QQ
    private final static int TOTAL_LEVEL = 1;

    public static int playerNumber1 = 0;
    public static int playerNumber2 = 0;
    public static int randomNum = 0;



    // back button
    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Fxml/beginScreen.fxml"));
        Main.stage.setScene(new Scene(root));

        GameView.isOnePlayer = false;
        GameView.isTwoPlayer = false;
    }

    // select character 1
    public void click1() throws IOException {

        label1.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 1;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 1;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 1) return;
                else playerNumber2 = 1;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 2
    public void click2() throws IOException {

        label2.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 2;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 2;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 2) return;
                else playerNumber2 = 2;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 3
    public void click3() throws IOException {

        label3.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 3;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 3;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 1) return;
                else playerNumber2 = 3;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 4
    public void click4() throws IOException {

        label4.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 4;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 4;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 4) return;
                else playerNumber2 = 4;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 5
    public void click5() throws IOException {

        label5.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 5;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 5;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 5) return;
                else playerNumber2 = 5;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 6
    public void click6() throws IOException {

        label6.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 6;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 6;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 6) return;
                else playerNumber2 = 6;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 7
    public void click7() throws IOException {

        label7.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 7;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 7;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 7) return;
                else playerNumber2 = 7;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 8
    public void click8() throws IOException {

        label8.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 8;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 8;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 8) return;
                else playerNumber2 = 8;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

    // select character 9
    public void click9() throws IOException {

        label9.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 9;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 9;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 9) return;
                else playerNumber2 = 9;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }
   
    // select character 10
    public void click10() throws IOException {

        label10.setVisible(true); 

        if(GameView.isOnePlayer) playerNumber1 = 10;
        if(GameView.isTwoPlayer) {

            // player1 hasn't selected yet.
            if(playerNumber1 == 0) {
                playerNumber1 = 10;
                return;
            }
            else {
                // player2 can't select the same character as player1
                if(playerNumber1 == 10) return;
                else playerNumber2 = 10;
            }
        }

        blackView.setVisible(true);

        FXMLLoader loader = null;
        if(GameView.isOnePlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen1.fxml"));
        if(GameView.isTwoPlayer) loader = new FXMLLoader(getClass().getResource("/resources/Fxml/gameScreen2.fxml"));
        Parent root = loader.load();

        if(GameView.isOnePlayer) new GameView(loader.getController(), playerNumber1);
        if(GameView.isTwoPlayer) new GameView(loader.getController(), playerNumber1, playerNumber2);

        // fade out
        new AnimationTimer(){
            double previousTime = 0;
            int delay=1, duration=1;

            @Override
            public void handle(long now) {
                if(previousTime == 0) previousTime = now;
                else if(now-previousTime>delay*1.0e9 && now-previousTime<(delay+duration)*1.0e9) {
                    blackView.setVisible(true);
                    blackView.setOpacity((now-previousTime)/1.0e9-delay);
                }

                if(now-previousTime >= (delay+duration)*1.0e9) {
                    Main.stage.setScene(new Scene(root));
                    stop();
                }
            }
        }.start();
    }

}