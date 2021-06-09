import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class GameView {

    AnchorPane pane;
    Chunk chunk[][];
    Character player1;
    private int playerNumber;
    private int mapNum;

    private boolean north, south, west, east;
    private boolean playerIsPress1;

    long previousTime1 = 0;
    AnimationTimer gameLoop;
    AnimationTimer pauseTimer;

    public static int map[][][] = new int[][][]{
        {}, 
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,2,2,2,2,2,0,0,2,2,2,2,0,0,1},
            {1,0,1,2,1,2,1,2,1,0,1,2,1,2,1,0,1},
            {1,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,2,0,2,1},
            {1,0,0,2,2,2,2,1,1,1,1,2,2,2,0,0,1},
            {1,2,1,2,1,2,1,1,1,1,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,0,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,1},
            {1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,0,1},
            {1,0,0,2,2,2,2,0,0,0,2,2,2,2,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        },
    };

    public GameView(int mapNum, AnchorPane pane, int playerNumber) {
        this.mapNum = mapNum;
        this.pane = pane;
        this.playerNumber = playerNumber;

        loadChunk();
        loadPlayers();

        addKeyPressListener();

        startGameLoop();
    }

    private void loadChunk() {
        chunk = new Chunk[15][17];

        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {

                chunk[i][j] = new Chunk();

                if(map[mapNum][i][j] == 0) {
                    chunk[i][j].setBlocked(false);
                    continue;
                }
                if(map[mapNum][i][j] == 1) {
                    chunk[i][j].setBlocked(true);
                    continue;
                }
                if(map[mapNum][i][j] == 2) {
                    chunk[i][j].setBlocked(true);
                    chunk[i][j].setCreatedItem(true);
                    chunk[i][j].setImageView(new Image("/resources/Images/box.png"));
                    chunk[i][j].imageView.setLayoutX(300+48*j);
                    chunk[i][j].imageView.setLayoutY(40+48*i);

                    pane.getChildren().add(chunk[i][j].getImageView());
                }
            }
        }
    }

    private void loadPlayers() {
        player1 = new Character(new ImageView(new Image("/resources/Images/sprite"+playerNumber+".png")));
        player1.setLayoutX(348);
        player1.setLayoutY(88);
        player1.setChunk(1, 1);
        player1.animation.play();
        pane.getChildren().add(player1);
    }

    private void addKeyPressListener() {

        pane.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent e) {
                if(playerIsPress1 == false) {
                    KeyCode in = e.getCode();

                    if(in == KeyCode.W) {
                        if(chunk[player1.Y-1][player1.X].getBlocked()) {
                            player1.direction = 'W';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            north = true;
                            player1.Y--;
                            player1.deltaDistance = 0;

                            stepPause();
                        }
                    }
                    if(in == KeyCode.S) {
                        if(chunk[player1.Y+1][player1.X].getBlocked()) {
                            player1.direction = 'S';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            south = true;
                            player1.Y++;
                            player1.deltaDistance = 0;

                            stepPause();
                        }
                    }
                    if(in == KeyCode.A) {
                        if(chunk[player1.Y][player1.X-1].getBlocked()) {
                            player1.direction = 'A';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            west = true;
                            player1.X--;
                            player1.deltaDistance = 0;

                            stepPause();
                        }
                    }
                    if(in == KeyCode.D) {
                        if(chunk[player1.Y][player1.X+1].getBlocked()) {
                            player1.direction = 'D';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            east = true;
                            player1.X++;
                            player1.deltaDistance = 0;

                            stepPause();
                        }
                    }
                }
            }
        });
    }

    private void stepPause() {

        pauseTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(previousTime1 == 0) previousTime1 = now;
                if(now-previousTime1 > 950000000/player1.speed) {
                    playerIsPress1 = false;
                    north = false;  
                    east = false;
                    south = false;
                    west = false;
                    previousTime1 = 0;
                    pauseTimer.stop();
                }
            }

        };

        pauseTimer.start();
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer(){

            @Override
            public void handle(long now) {
                playerWalk(player1);
            }
            
        };

        gameLoop.start();
    }

    private void playerWalk(Character player) {

        int dx = 0, dy = 0;

        // determine player is walking or not
        if (north) dy -= 1;
        if (south) dy += 1;
        if (east)  dx += 1;
        if (west)  dx -= 1;

        if(dx == 0 && dy == 0) player.stopWalking();
        else {
            player.moveX(dx);
            player.moveY(dy);
        }
    }
}