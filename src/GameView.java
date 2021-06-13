import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class GameView {

    GameController controller;
    AnchorPane pane, gameOver1, gameOver2;
    Chunk chunk[][];
    Character player1;
    Character player2;
    private int playerNumber1 = 1;
    private int playerNumber2 = 1;
    private int mapNum;
    private int playerAlive = 4;

    public static boolean isTwoPlayer = false;    
    public static boolean isOnePlayer = false;

    private boolean north1, south1, west1, east1;
    private boolean playerNorth1, playerSouth1, playerWest1, playerEast1;
    private boolean playerNorth2, playerSouth2, playerWest2, playerEast2;
    private boolean north2, south2, west2, east2;
    private boolean playerIsPress1;
    private boolean playerIsPress2;

    public static AnimationTimer gameLoop;
    AnimationTimer pauseTimer1;
    AnimationTimer pauseTimer2;

    public static int map[][][] = new int[][][]{
        {}, 
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,2,2,2,2,2,0,0,2,2,2,2,0,0,1},
            {1,0,1,2,1,2,1,2,1,0,1,2,1,2,1,0,1},
            {1,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
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

    public GameView(int mapNum, GameController controller, int playerNumber1) {
        this.mapNum = mapNum;
        this.controller = controller;
        pane = controller.pane;
        gameOver1 = controller.gameOver1;
        this.playerNumber1 = playerNumber1;

        loadChunk();
        loadPlayers();

        addKeyPressListener();

        startGameLoop();
    }
    public GameView(int mapNum, GameController controller, int playerNumber1, int playerNumber2) {
        this.mapNum = mapNum;
        this.controller = controller;
        pane = controller.pane;
        gameOver2 = controller.gameOver2;
        this.playerNumber1 = playerNumber1;
        this.playerNumber2 = playerNumber2;

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
                    // chunk[i][j].setImageView(new Image("resources/Images/sand.png"));
                    chunk[i][j].setImageView(null);
                    chunk[i][j].imageView.setLayoutX(300+48*j);
                    chunk[i][j].imageView.setLayoutY(40+48*i);

                    pane.getChildren().add(chunk[i][j].imageView);
                }
                if(map[mapNum][i][j] == 1) {
                    chunk[i][j].setBlocked(true);
                    chunk[i][j].isWall = true;
                }
                if(map[mapNum][i][j] == 2) {
                    chunk[i][j].setBlocked(true);
                    chunk[i][j].setCreatedItem(true);
                    chunk[i][j].setImageView(new Image("/resources/Images/box.png"));
                    chunk[i][j].imageView.setLayoutX(300+48*j);
                    chunk[i][j].imageView.setLayoutY(40+48*i);

                    pane.getChildren().add(chunk[i][j].imageView);
                }

            }
        }
    }

    private void loadPlayers() {
        player1 = new Character(new ImageView(new Image("/resources/Images/sprite"+playerNumber1+".png")));
        player1.setLayoutX(348);
        player1.setLayoutY(88);
        player1.setChunk(1, 1);
        player1.animation.play();
        pane.getChildren().add(player1);

        if(isTwoPlayer) {
            player2 = new Character(new ImageView(new Image("/resources/Images/sprite"+playerNumber2+".png")));
            player2.setLayoutX(1020);
            player2.setLayoutY(664);
            player2.setChunk(13, 15);
            player2.animation.play();
            pane.getChildren().add(player2);
        }
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
                        else playerNorth1 = true;
                    }
                    if(in == KeyCode.S) {
                        if(chunk[player1.Y+1][player1.X].getBlocked()) {
                            player1.direction = 'S';
                            return;
                        }
                        else playerSouth1 = true;
                    }
                    if(in == KeyCode.A) {
                        if(chunk[player1.Y][player1.X-1].getBlocked()) {
                            player1.direction = 'A';
                            return;
                        }
                        else playerWest1 = true;
                    }
                    if(in == KeyCode.D) {
                        if(chunk[player1.Y][player1.X+1].getBlocked()) {
                            player1.direction = 'D';
                            return;
                        }
                        else playerEast1 = true;
                    }

                    if(in == KeyCode.SPACE) {
                        if(!chunk[player1.Y][player1.X].isFiringBomb) setBomb(1);
                    }
                }

                if(isTwoPlayer && !playerIsPress2) {
                    KeyCode in = e.getCode();

                    if(in == KeyCode.UP) {
                        if(chunk[player2.Y-1][player2.X].getBlocked()) {
                            player2.direction = 'W';
                            return;
                        }
                        else playerNorth2 = true;
                    }
                    if(in == KeyCode.DOWN) {
                        if(chunk[player2.Y+1][player2.X].getBlocked()) {
                            player2.direction = 'S';
                            return;
                        }
                        else playerSouth2 = true;
                    }
                    if(in == KeyCode.LEFT) {
                        if(chunk[player2.Y][player2.X-1].getBlocked()) {
                            player2.direction = 'A';
                            return;
                        }
                        else playerWest2 = true;
                    }
                    if(in == KeyCode.RIGHT) {
                        if(chunk[player2.Y][player2.X+1].getBlocked()) {
                            player2.direction = 'D';
                            return;
                        }
                        else playerEast2 = true;
                    }
                    if(in == KeyCode.ENTER) {
                        if(!chunk[player2.Y][player2.X].isFiringBomb) setBomb(2);
                    }
                }
            }
        });

        pane.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e) {
                KeyCode in = e.getCode();

                if(in == KeyCode.W) playerNorth1 = false;
                if(in == KeyCode.S) playerSouth1 = false;
                if(in == KeyCode.A) playerWest1 = false;
                if(in == KeyCode.D) playerEast1 = false;

                if(isTwoPlayer) {
                    if(in == KeyCode.UP) playerNorth2 = false;
                    if(in == KeyCode.DOWN) playerSouth2 = false;
                    if(in == KeyCode.RIGHT) playerEast2 = false;
                    if(in == KeyCode.LEFT) playerWest2 = false;
                }
            }
        });    
    }

    private void playerGo(int num, char dir) {
        if(num == 1) {
            if(dir == 'N') {
                if(!chunk[player1.Y-1][player1.X].getBlocked()) {
                    playerIsPress1 = true;
                    north1 = true;

                    stepPause(1);
                }
            }
            if(dir == 'S') {
                if(!chunk[player1.Y+1][player1.X].getBlocked()) {
                    playerIsPress1 = true;
                    south1 = true;

                    stepPause(1);
                }
            }
            if(dir == 'E') {
                if(!chunk[player1.Y][player1.X+1].getBlocked()) {
                    playerIsPress1 = true;
                    east1 = true;

                    stepPause(1);
                }

            }
            if(dir == 'W') {
                if(!chunk[player1.Y][player1.X-1].getBlocked()) {
                    playerIsPress1 = true;
                    west1 = true;

                    stepPause(1);
                }
            }
        }
        if(num == 2) {
            if(dir == 'N') {
                if(!chunk[player2.Y-1][player2.X].getBlocked()) {
                    playerIsPress2 = true;
                    north2 = true;

                    stepPause(2);
                }
            }
            if(dir == 'S') {
                if(!chunk[player2.Y+1][player2.X].getBlocked()) {
                    playerIsPress2 = true;
                    south2 = true;

                    stepPause(2);
                }
            }
            if(dir == 'E') {
                if(!chunk[player2.Y][player2.X+1].getBlocked()) {
                    playerIsPress2 = true;
                    east2 = true;

                    stepPause(2);
                }

            }
            if(dir == 'W') {
                if(!chunk[player2.Y][player2.X-1].getBlocked()) {
                    playerIsPress2 = true;
                    west2 = true;

                    stepPause(2);
                }
            }
        }
    }

    private void stepPause(int num) {

        if(num == 1) {
            pauseTimer1 = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(player1.deltaDistance >= 48) {
                        if(north1) player1.Y--;
                        if(south1) player1.Y++;
                        if(east1)  player1.X++;
                        if(west1)  player1.X--;

                        playerIsPress1 = false;
                        north1 = false;  
                        east1 = false;
                        south1 = false;
                        west1 = false;
                        player1.deltaDistance = 0;
                        pauseTimer1.stop();
                    }
                }
            };

            pauseTimer1.start();
        }
        else if(num == 2) {
            pauseTimer2 = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(player2.deltaDistance >= 48) {
                        if(north2) player2.Y--;
                        if(south2) player2.Y++;
                        if(east2)  player2.X++;
                        if(west2)  player2.X--;

                        playerIsPress2 = false;
                        north2 = false;  
                        east2 = false;
                        south2 = false;
                        west2 = false;
                        player2.deltaDistance = 0;
                        pauseTimer2.stop();
                    }
                }
            };

            pauseTimer2.start();
        }
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer(){

            @Override
            public void handle(long now) {
                if(playerNorth1) playerGo(1, 'N');
                if(playerSouth1) playerGo(1, 'S');
                if(playerWest1) playerGo(1, 'W');
                if(playerEast1) playerGo(1, 'E');

                if(isTwoPlayer) {
                    if(playerNorth2) playerGo(2, 'N');
                    if(playerSouth2) playerGo(2, 'S');
                    if(playerWest2) playerGo(2, 'W');
                    if(playerEast2) playerGo(2, 'E');
                }

                playerWalk(1);
                if(isTwoPlayer) playerWalk(2);

                // Speed
                if(chunk[player1.Y][player1.X].isBoot) { 
                    // chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isBoot = false;
                    if(player1.speed+1 < 7) player1.speed++;
                    controller.speed1.setText(Integer.toString(player1.speed));
                }

                // Blast Range
                if(chunk[player1.Y][player1.X].isPotion) {
                    // chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isPotion = false;
                    player1.blastRange++;
                    controller.blastRange1.setText(Integer.toString(player1.blastRange));
                }

                // Bomb Number
                if(chunk[player1.Y][player1.X].isBomb) {
                    // chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isBomb = false;
                    player1.bombNumber++;
                    controller.bombNumber1.setText(Integer.toString(player1.bombNumber));
                }

                // Heart
                if(chunk[player1.Y][player1.X].isHeart) {
                    // chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isHeart = false;
                    player1.heart++;
                    controller.heart1.setText(Integer.toString(player1.heart));
                }

                if(isTwoPlayer) {
                    // Speed
                    if(chunk[player2.Y][player2.X].isBoot) { 
                        // chunk[player2.Y][player2.X].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isBoot = false;
                        if(player2.speed<7) player2.speed++;
                        controller.speed2.setText(Integer.toString(player2.speed));
                    }

                    // Blast Range
                    if(chunk[player2.Y][player2.X].isPotion) {
                        // chunk[player2.Y][player2.X].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isPotion = false;
                        player2.blastRange++;
                        controller.blastRange2.setText(Integer.toString(player2.blastRange));
                    }

                    // Bomb Number
                    if(chunk[player2.Y][player2.X].isBomb) {
                        // chunk[player2.Y][player2.X].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isBomb = false;
                        player2.bombNumber++;
                        controller.bombNumber2.setText(Integer.toString(player2.bombNumber));
                    }

                    // Heart
                    if(chunk[player2.Y][player2.X].isHeart) {
                        // chunk[player2.Y][player2.X].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isHeart = false;
                        player2.heart++;
                        controller.heart2.setText(Integer.toString(player2.heart));
                    }
                }

                if(isOnePlayer) {
                    if(!player1.isInvincible)
                        try { damage(1); } catch (IOException e) { e.printStackTrace();} 
                }
                if(isTwoPlayer) {
                    if(!player1.isInvincible) try { damage(1); } catch (IOException e) { e.printStackTrace();}
                    if(!player2.isInvincible) try { damage(2); } catch (IOException e) { e.printStackTrace();}
                }
            }
            
        };

        gameLoop.start();
    }

    private void playerWalk(int num) {

        int dx = 0, dy = 0;

        if(num == 1) {
            // determine player is walking or not
            if (north1) dy -= 1;
            if (south1) dy += 1;
            if (east1)  dx += 1;
            if (west1)  dx -= 1;

            if(dx == 0 && dy == 0) player1.stopWalking();
            else {
                player1.moveX(dx);
                player1.moveY(dy);
            }
        }
        else if(num == 2) {
            // determine player is walking or not
            if (north2) dy -= 1;
            if (south2) dy += 1;
            if (east2)  dx += 1;
            if (west2)  dx -= 1;

            if(dx == 0 && dy == 0) player2.stopWalking();
            else {
                player2.moveX(dx);
                player2.moveY(dy);
            }
        }
    }

    private void setBomb(int num){

        
        if(num == 1) {
            int y=player1.Y, x=player1.X;

            if(chunk[y][x].imageView.getImage() == new Image("/resources/Images/elephantBomb.png")) return;
            if(player1.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            controller.bombNumber1.setText(Integer.toString(--player1.bombNumber));

            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        // chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[y][x].setImageView(null);
                        controller.bombNumber1.setText(Integer.toString(++player1.bombNumber));

                        bombBurst(y, x, player1.blastRange);
                        stop();
                    }
                }
                
            }.start();
        }

        if(num == 2) {

            int y=player2.Y, x=player2.X;

            if(chunk[y][x].imageView.getImage() == new Image("/resources/Images/elephantBomb.png")) return;
            if(player2.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            controller.bombNumber2.setText(Integer.toString(--player2.bombNumber));

            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        // chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
                        chunk[y][x].setImageView(null);
                        controller.bombNumber2.setText(Integer.toString(++player2.bombNumber));
                        bombBurst(y, x, player2.blastRange);
                        stop();
                    }
                }
                
            }.start();
        }
    }

    private void bombBurst(int y, int x, int range) {

        new AnimationTimer() {
            int r=1;
            boolean canUp=true, canDown=true, canLeft=true, canRight=true;

            long previousTime = 0;
            double lastTimeShow = 0;
            double duration = 0.6e9;

            @Override
            public void handle(long now) {
                if(previousTime == 0) {
                    previousTime = now;
                    chunk[y][x].setImageView(new Image("/resources/Images/fire.jpg"));
                }

                // Blast
                if(now-previousTime>lastTimeShow && now-previousTime<=r*(duration/range)) {
                    if(now-previousTime%10 <= 1) return;

                    //CENTER
                    chunk[y][x].setImageView(new Image("/resources/Images/fire.jpg"));
                    chunk[y][x].isDangered = true;

                    // UP
                    for(int i=1 ; r<=range && canUp && i<=r ; i++) {
                        if(chunk[y-i][x].isWall) canUp = false;
                        else if(!chunk[y-i][x].getBlocked() || chunk[y-i][x].getCreatedItem() || chunk[y-i][x].isFiringBomb) {
                            chunk[y-i][x].clearItem();
                            chunk[y-i][x].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y-i][x].isDangered = true;
                        }
                    }
                    // DOWN
                    for(int i=1 ; r<=range && canDown && i<=r ; i++) {
                        if(chunk[y+i][x].isWall) canDown = false;
                        else if(!chunk[y+i][x].getBlocked() || chunk[y+i][x].getCreatedItem() || chunk[y+i][x].isFiringBomb) {
                            chunk[y+i][x].clearItem();
                            chunk[y+i][x].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y+i][x].isDangered = true;
                        }
                    }
                    // LEFT
                    for(int i=1 ; r<=range && canLeft && i<=r ; i++) {
                        if(chunk[y][x-i].isWall) canLeft = false;
                        else if(!chunk[y][x-i].getBlocked() || chunk[y][x-i].getCreatedItem() || chunk[y][x-i].isFiringBomb) {
                            chunk[y][x-i].clearItem();
                            chunk[y][x-i].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y][x-i].isDangered = true;
                        }
                    }
                    // RIGHT
                    for(int i=1 ; r<=range && canRight && i<=r ; i++) {
                        if(chunk[y][x+i].isWall) canRight = false;
                        else if(!chunk[y][x+i].getBlocked() || chunk[y][x+i].getCreatedItem() || chunk[y][x+i].isFiringBomb) {
                            chunk[y][x+i].clearItem();
                            chunk[y][x+i].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y][x+i].isDangered = true;
                        }
                    }

                    if(r+1 <= range) r++;
                }

                // Show item
                if(now-previousTime >= range*(duration/range)) {

                    // CENTER
                    // chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[y][x].setImageView(null);
                    chunk[y][x].isDangered =false;
                    chunk[y][x].isFiringBomb = false;

                    // UP
                    for(int i=1 ; i<=range && !chunk[y-i][x].isWall; i++) {
                        chunk[y-i][x].isDangered = false;
                        // if(!chunk[y-i][x].getBlocked()) chunk[y-i][x].setImageView(new Image("/resources/Images/sand.png"));
                        if(!chunk[y-i][x].getBlocked()) chunk[y-i][x].setImageView(null);
                        if(chunk[y-i][x].isFiringBomb) chunk[y-i][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y-i][x].getCreatedItem()) {
                            chunk[y-i][x].setBlocked(false);
                            chunk[y-i][x].setCreatedItem(false);
                            chunk[y-i][x].createItem();
                        }
                    }
                    // DOWN
                    for(int i=1 ; i<=range && !chunk[y+i][x].isWall; i++) {
                        chunk[y+i][x].isDangered = false;
                        // if(!chunk[y+i][x].getBlocked() || chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(new Image("/resources/Images/sand.png"));
                        if(!chunk[y+i][x].getBlocked() || chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(null);
                        if(chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y+i][x].getCreatedItem()) {
                            chunk[y+i][x].setBlocked(false);
                            chunk[y+i][x].setCreatedItem(false);
                            chunk[y+i][x].createItem();
                        }
                    }
                    // LEFT
                    for(int i=1 ; i<=range && !chunk[y][x-i].isWall ; i++) {
                        chunk[y][x-i].isDangered = false;
                        // if(!chunk[y][x-i].getBlocked() || chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(new Image("/resources/Images/sand.png"));
                        if(!chunk[y][x-i].getBlocked() || chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(null);
                        if(chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y][x-i].getCreatedItem()) {
                            chunk[y][x-i].setBlocked(false);
                            chunk[y][x-i].setCreatedItem(false);
                            chunk[y][x-i].createItem();
                        }
                    }
                    // RIGHT
                    for(int i=1 ; i<=range && !chunk[y][x+i].isWall ; i++) {
                        chunk[y][x+i].isDangered = false;
                        // if(!chunk[y][x+i].getBlocked() || chunk[y][x+i].isFiringBomb) chunk[y][x+i].setImageView(new Image("/resources/Images/sand.png"));
                        if(!chunk[y][x+i].getBlocked() || chunk[y][x+i].isFiringBomb) chunk[y][x+i].setImageView(null);
                        if(chunk[y][x+i].isFiringBomb) chunk[y][x+i].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y][x+i].getCreatedItem()) {
                            chunk[y][x+i].setBlocked(false);
                            chunk[y][x+i].setCreatedItem(false);
                            chunk[y][x+i].createItem();
                        }
                    }
                    stop();
                }
            }
        }.start();

    }

    private void damage(int num) throws IOException {
        if(num == 1) {
            if(chunk[player1.Y][player1.X].isDangered) {
                if(player1.heart-1 > 0) {
                    player1.heart--;
                    player1.isInvincible = true;
                    controller.heart1.setText(Integer.toString(player1.heart));

                    new AnimationTimer(){
                        double previousTime = 0;
                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now-previousTime >= 2.0e9) {
                                player1.isInvincible = false;
                                stop();
                            }
                        }
                    }.start();
                }
                else {
                    if(isOnePlayer) {
                        if(playerAlive > 1) {
                            gameLoop.stop(); 
                            pane.getChildren().remove(gameOver1);
                            pane.getChildren().add(gameOver1);
                            controller.label.setText("You Lose !!!");
                            controller.label.setTextFill(Color.RED);
                            gameOver1.setVisible(true);
                        }
                    }
                    if(isTwoPlayer) {
                        if(playerAlive > 1) {
                            gameLoop.stop(); 
                            pane.getChildren().remove(gameOver2);
                            pane.getChildren().add(gameOver2);
                            controller.label.setText("Player1 Lose !!!");
                            controller.label.setTextFill(Color.RED);
                            gameOver2.setVisible(true);
                        }
                    }
                }
            }
            else return; 
        }
        if(num == 2) {
            if(chunk[player2.Y][player2.X].isDangered) {
                if(player2.heart-1 > 0) {
                    player2.heart--;
                    player2.isInvincible = true;
                    controller.heart2.setText(Integer.toString(player2.heart));

                    new AnimationTimer(){
                        double previousTime = 0;
                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now - previousTime >= 2.0e9) {
                                player2.isInvincible = false;
                                stop();
                            }
                        }
                    }.start();
                }
                else {
                    if(isOnePlayer) {
                        if(playerAlive > 1) {
                            gameLoop.stop(); 
                            pane.getChildren().remove(gameOver1);
                            pane.getChildren().add(gameOver1);
                            controller.label.setText("You Lose !!!");
                            controller.label.setTextFill(Color.RED);
                            gameOver1.setVisible(true);
                        }
                    }
                    if(isTwoPlayer) {
                        if(playerAlive > 1) {
                            gameLoop.stop(); 
                            pane.getChildren().remove(gameOver2);
                            pane.getChildren().add(gameOver2);
                            controller.label.setText("Player2 Lose !!!");
                            controller.label.setTextFill(Color.RED);
                            gameOver2.setVisible(true);
                        }
                    }
                }
            }
            else return; 
        }
    }
}