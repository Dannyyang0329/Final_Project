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

    private GameController controller;
    private AnchorPane pane, gameOver1, gameOver2;
    private Chunk chunk[][];
    private int mapNum;
    private int playerAlive = 4;

    private Character player1;
    private Character player2;
    private AIPlayer AI1, AI2, AI3;
    private int playerNumber1 = 1;
    private int playerNumber2 = 1;
    private int AINumber1 = 1;
    private int AINumber2 = 1;
    private int AINumber3 = 1;

    public static boolean isTwoPlayer = false;    
    public static boolean isOnePlayer = false;

    private boolean playerNorth1, playerSouth1, playerWest1, playerEast1;
    private boolean playerNorth2, playerSouth2, playerWest2, playerEast2;

    private boolean north1, south1, west1, east1;
    private boolean north2, south2, west2, east2;
    private boolean north3, south3, west3, east3;
    private boolean north4, south4, west4, east4;
    private boolean north5, south5, west5, east5;

    private boolean playerIsPress1;
    private boolean playerIsPress2;
    private boolean AIIsPress1;
    private boolean AIIsPress2;
    private boolean AIIsPress3;

    public static AnimationTimer gameLoop;

    public static int map[][][] = new int[][][]{
        {}, 
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,2,2,2,2,0,0,2,2,2,0,0,0,1},
            {1,0,1,2,1,2,1,2,1,0,1,2,1,2,1,0,1},
            {1,0,2,2,2,0,2,2,2,2,2,0,2,2,2,0,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,0,0,2,2,2,2,1,1,1,1,2,2,2,0,0,1},
            {1,2,1,2,1,2,1,1,1,1,1,2,1,2,1,2,1},
            {1,2,2,2,2,0,2,2,2,0,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,0,2,2,2,0,2,2,2,2,2,2,2,2,2,0,1},
            {1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,0,1},
            {1,0,0,0,2,2,2,0,0,0,2,2,2,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        }
    };

    // constructor
    public GameView(GameController controller, int playerNumber1) {

        this.mapNum = SelectController.randomNum;
        this.controller = controller;
        this.playerNumber1 = playerNumber1;

        this.pane = controller.pane;
        this.gameOver1 = controller.gameOver1;

        loadChunk();
        loadPlayers();

        addKeyPressListener();

        startGameLoop();
    }
    public GameView(GameController controller, int playerNumber1, int playerNumber2) {

        this.mapNum = SelectController.randomNum;
        this.controller = controller;
        this.playerNumber1 = playerNumber1;
        this.playerNumber2 = playerNumber2;

        this.pane = controller.pane;
        this.gameOver2 = controller.gameOver2;

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

                // Road
                if(map[mapNum][i][j] == 0) {
                    chunk[i][j].setBlocked(false);
                    chunk[i][j].setImageView(null);
                    chunk[i][j].imageView.setLayoutX(300+48*j);
                    chunk[i][j].imageView.setLayoutY(40+48*i);
                    pane.getChildren().add(chunk[i][j].imageView);
                }
                // Wall
                else if(map[mapNum][i][j] == 1) {
                    chunk[i][j].setBlocked(true);
                    chunk[i][j].setWall(true);
                }

                // Box
                else if(map[mapNum][i][j] == 2) {
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
        // Add player 1
        player1 = new Character(new ImageView(new Image("/resources/Images/sprite"+playerNumber1+".png")));
        player1.setLayoutX(348);
        player1.setLayoutY(88);
        player1.setChunk(1, 1);
        player1.animation.play();
        pane.getChildren().add(player1);
        chunk[1][1].setPlayer(true);

        // Add player 2
        if(isTwoPlayer) {
            player2 = new Character(new ImageView(new Image("/resources/Images/sprite"+playerNumber2+".png")));
            player2.setLayoutX(1020);
            player2.setLayoutY(664);
            player2.setChunk(13, 15);
            player2.animation.play();
            pane.getChildren().add(player2);
            chunk[13][15].setPlayer(true);
        }

        // Add AI 1  
        AINumber1 = (int)(Math.random()*10)+1;
        while(AINumber1 == playerNumber1 || AINumber1 == playerNumber2) AINumber1 = (int)(Math.random()*10)+1;
        AI1 = new AIPlayer(new ImageView(new Image("/resources/Images/sprite"+AINumber1+".png")), chunk);
        AI1.setLayoutX(1020);
        AI1.setLayoutY(88);
        AI1.setChunk(1, 15);
        AI1.animation.play();
        pane.getChildren().add(AI1);
        chunk[1][15].setPlayer(true);

        // Add AI 2
        AINumber2 = (int)(Math.random())+1;
        while(AINumber2 == playerNumber1 || AINumber2 == playerNumber2 || AINumber2 == AINumber1) AINumber2 = (int)(Math.random()*10)+1;
        AI2 = new AIPlayer(new ImageView(new Image("/resources/Images/sprite"+AINumber2+".png")), chunk);
        AI2.setLayoutX(348);
        AI2.setLayoutY(664);
        AI2.setChunk(13, 1);
        AI2.animation.play();
        pane.getChildren().add(AI2);
        chunk[13][1].setPlayer(true);

        // Add AI 3
        if(isOnePlayer) {
            AINumber3 = (int)(Math.random())+1;
            while(AINumber3 == playerNumber1 || AINumber3 == playerNumber2 || AINumber3 == AINumber1 || AINumber3 == AINumber2) AINumber3 = (int)(Math.random()*10)+1;
            AI3 = new AIPlayer(new ImageView(new Image("/resources/Images/sprite"+AINumber3+".png")), chunk);
            AI3.setLayoutX(1020);
            AI3.setLayoutY(664);
            AI3.setChunk(13, 15);
            AI3.animation.play();
            pane.getChildren().add(AI3);
            chunk[13][15].setPlayer(true);
        }
    }

    private void addKeyPressListener() {

        pane.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent e) {

                KeyCode in = e.getCode();

                // Control player 1
                if(in == KeyCode.SPACE) if(!chunk[player1.Y][player1.X].getFiringBomb()) setBomb(1);
                if(playerIsPress1 == false) {
                    if(in == KeyCode.W) {
                        if(chunk[player1.Y-1][player1.X].getBlocked()) {
                            player1.direction = 'W';
                            return;
                        }
                        else playerNorth1 = true;
                    }
                    
                    else if(in == KeyCode.S) {
                        if(chunk[player1.Y+1][player1.X].getBlocked()) {
                            player1.direction = 'S';
                            return;
                        }
                        else playerSouth1 = true;
                    }
                    
                    else if(in == KeyCode.A) {
                        if(chunk[player1.Y][player1.X-1].getBlocked()) {
                            player1.direction = 'A';
                            return;
                        }
                        else playerWest1 = true;
                    }
                    
                    else if(in == KeyCode.D) {
                        if(chunk[player1.Y][player1.X+1].getBlocked()) {
                            player1.direction = 'D';
                            return;
                        }
                        else playerEast1 = true;
                    }
                }

                // Control player 2
                if(isTwoPlayer) {
                    if(in == KeyCode.ENTER) if(!chunk[player2.Y][player2.X].getFiringBomb()) setBomb(2);
                    if(!playerIsPress2) {
                        
                        if(in == KeyCode.UP) {
                            if(chunk[player2.Y-1][player2.X].getBlocked()) {
                                player2.direction = 'W';
                                return;
                            }
                            else playerNorth2 = true;
                        }

                        else if(in == KeyCode.DOWN) {
                            if(chunk[player2.Y+1][player2.X].getBlocked()) {
                                player2.direction = 'S';
                                return;
                            }
                            else playerSouth2 = true;
                        }

                        else if(in == KeyCode.LEFT) {
                            if(chunk[player2.Y][player2.X-1].getBlocked()) {
                                player2.direction = 'A';
                                return;
                            }
                            else playerWest2 = true;
                        }

                        else if(in == KeyCode.RIGHT) {
                            if(chunk[player2.Y][player2.X+1].getBlocked()) {
                                player2.direction = 'D';
                                return;
                            }
                            else playerEast2 = true;
                        }
                    }
                }
            }
        });

        pane.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e) {
                KeyCode in = e.getCode();

                if(in == KeyCode.W) playerNorth1 = false;
                else if(in == KeyCode.S) playerSouth1 = false;
                else if(in == KeyCode.A) playerWest1 = false;
                else if(in == KeyCode.D) playerEast1 = false;

                if(isTwoPlayer) {
                    if(in == KeyCode.UP) playerNorth2 = false;
                    else if(in == KeyCode.DOWN) playerSouth2 = false;
                    else if(in == KeyCode.RIGHT) playerEast2 = false;
                    else if(in == KeyCode.LEFT) playerWest2 = false;
                }
            }
        });    
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player1
                if(!player1.isDead) {
                    if(playerNorth1) playerGo(1, 'N');
                    else if(playerSouth1) playerGo(1, 'S');
                    else if(playerWest1) playerGo(1, 'W');
                    else if(playerEast1) playerGo(1, 'E');
                }

                // player2
                if(isTwoPlayer && !player2.isDead) {
                    if(playerNorth2) playerGo(2, 'N');
                    else if(playerSouth2) playerGo(2, 'S');
                    else if(playerWest2) playerGo(2, 'W');
                    else if(playerEast2) playerGo(2, 'E');
                }

                // AI1
                if(!AI1.isDead) {
                    AI1.setAiMap(chunk);
                    if(!AIIsPress1) playerGo(3, AI1.getMoveDir());
                }

                // AI2
                if(!AI2.isDead) {
                    AI2.setAiMap(chunk);
                    if(!AIIsPress2) playerGo(4, AI2.getMoveDir());
                }

                // AI3
                if(isOnePlayer && !AI3.isDead) {
                    AI3.setAiMap(chunk);
                    if(!AIIsPress3) playerGo(5, AI3.getMoveDir());
                }


                playerWalk(1);
                if(isTwoPlayer) playerWalk(2);

                playerWalk(3);
                playerWalk(4);
                if(isOnePlayer) playerWalk(5);


                // player1
                // Speed
                if(chunk[player1.Y][player1.X].isBoot) { 
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isBoot = false;
                    if(player1.speed < 7) {
                        player1.speed++;
                        controller.speed1.setText(Integer.toString(player1.speed));
                    }
                }
                // Blast Range
                else if(chunk[player1.Y][player1.X].isPotion) {
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isPotion = false;
                    if(player1.blastRange < 5) {
                        player1.blastRange++;
                        controller.blastRange1.setText(Integer.toString(player1.blastRange));
                    }
                }
                // Bomb Number
                else if(chunk[player1.Y][player1.X].isBomb) {
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isBomb = false;
                    if(player1.bombNumber < 3) { 
                        player1.bombNumber++;
                        controller.bombNumber1.setText(Integer.toString(player1.bombNumber));
                    }
                }
                // Heart
                else if(chunk[player1.Y][player1.X].isHeart) {
                    chunk[player1.Y][player1.X].setImageView(null);
                    chunk[player1.Y][player1.X].isHeart = false;
                    if(player1.heart < 5) {
                        player1.heart++;
                        controller.heart1.setText(Integer.toString(player1.heart));
                    }
                }


                // player2
                if(isTwoPlayer) {
                    // Speed
                    if(chunk[player2.Y][player2.X].isBoot) { 
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isBoot = false;
                        if(player2.speed < 7) {
                            player2.speed++;
                            controller.speed2.setText(Integer.toString(player2.speed));
                        }
                    }
                    // Blast Range
                    else if(chunk[player2.Y][player2.X].isPotion) {
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isPotion = false;
                        if(player2.blastRange < 5) {
                            player2.blastRange++;
                            controller.blastRange2.setText(Integer.toString(player2.blastRange));
                        }
                    }
                    // Bomb Number
                    else if(chunk[player2.Y][player2.X].isBomb) {
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isBomb = false;
                        if(player2.bombNumber < 3) {
                            player2.bombNumber++;
                            controller.bombNumber2.setText(Integer.toString(player2.bombNumber));
                        }
                    }
                    // Heart
                    else if(chunk[player2.Y][player2.X].isHeart) {
                        chunk[player2.Y][player2.X].setImageView(null);
                        chunk[player2.Y][player2.X].isHeart = false;
                        if(player2.heart < 5) {
                            player2.heart++;
                            controller.heart2.setText(Integer.toString(player2.heart));
                        }
                    }
                }

                // AI1
                // Speed
                if(chunk[AI1.Y][AI1.X].isBoot) { 
                    chunk[AI1.Y][AI1.X].setImageView(null);
                    chunk[AI1.Y][AI1.X].isBoot = false;
                    if(AI1.speed < 4) AI1.speed++;
                }
                // Blast Range
                else if(chunk[AI1.Y][AI1.X].isPotion) {
                    chunk[AI1.Y][AI1.X].setImageView(null);
                    chunk[AI1.Y][AI1.X].isPotion = false;
                    if(AI1.blastRange < 4) AI1.blastRange++;
                }
                // Bomb Number
                else if(chunk[AI1.Y][AI1.X].isBomb) {
                    chunk[AI1.Y][AI1.X].setImageView(null);
                    chunk[AI1.Y][AI1.X].isBomb = false;
                    if(AI1.bombNumber < 2) AI1.bombNumber++;
                }
                // Heart
                else if(chunk[AI1.Y][AI1.X].isHeart) {
                    chunk[AI1.Y][AI1.X].setImageView(null);
                    chunk[AI1.Y][AI1.X].isHeart = false;
                    AI1.heart++;
                }

                // AI2
                // Speed
                if(chunk[AI2.Y][AI2.X].isBoot) { 
                    chunk[AI2.Y][AI2.X].setImageView(null);
                    chunk[AI2.Y][AI2.X].isBoot = false;
                    if(AI2.speed < 4) AI1.speed++;
                }
                // Blast Range
                else if(chunk[AI2.Y][AI2.X].isPotion) {
                    chunk[AI2.Y][AI2.X].setImageView(null);
                    chunk[AI2.Y][AI2.X].isPotion = false;
                    if(AI2.blastRange < 4) AI1.blastRange++;
                }
                // Bomb Number
                else if(chunk[AI2.Y][AI2.X].isBomb) {
                    chunk[AI2.Y][AI2.X].setImageView(null);
                    chunk[AI2.Y][AI2.X].isBomb = false;
                    if(AI2.bombNumber < 2) AI2.bombNumber++;
                }
                // Heart
                else if(chunk[AI2.Y][AI2.X].isHeart) {
                    chunk[AI2.Y][AI2.X].setImageView(null);
                    chunk[AI2.Y][AI2.X].isHeart = false;
                    AI2.heart++;
                }

                if(isOnePlayer) {
                    // AI3
                    // Speed
                    if(chunk[AI3.Y][AI3.X].isBoot) { 
                        chunk[AI3.Y][AI3.X].setImageView(null);
                        chunk[AI3.Y][AI3.X].isBoot = false;
                        if(AI3.speed < 4) AI1.speed++;
                    }
                    // Blast Range
                    else if(chunk[AI3.Y][AI3.X].isPotion) {
                        chunk[AI3.Y][AI3.X].setImageView(null);
                        chunk[AI3.Y][AI3.X].isPotion = false;
                        if(AI3.blastRange < 4) AI1.blastRange++;
                    }
                    // Bomb Number
                    else if(chunk[AI3.Y][AI3.X].isBomb) {
                        chunk[AI3.Y][AI3.X].setImageView(null);
                        chunk[AI3.Y][AI3.X].isBomb = false;
                        if(AI3.bombNumber < 2) AI3.bombNumber++;
                    }
                    // Heart
                    else if(chunk[AI3.Y][AI3.X].isHeart) {
                        chunk[AI3.Y][AI3.X].setImageView(null);
                        chunk[AI3.Y][AI3.X].isHeart = false;
                        AI3.heart++;
                    }
                }
                
                if(isOnePlayer) {
                    if(!player1.isInvincible) try { damage(1); } catch (IOException e) { e.printStackTrace();} 
                    if(!AI1.isInvincible) try { damage(3); } catch (IOException e) { e.printStackTrace();} 
                    if(!AI2.isInvincible) try { damage(4); } catch (IOException e) { e.printStackTrace();} 
                    if(!AI3.isInvincible) try { damage(5); } catch (IOException e) { e.printStackTrace();} 
                }
                else {
                    if(!player1.isInvincible) try { damage(1); } catch (IOException e) { e.printStackTrace();}
                    if(!player2.isInvincible) try { damage(2); } catch (IOException e) { e.printStackTrace();}
                    if(!AI1.isInvincible) try { damage(3); } catch (IOException e) { e.printStackTrace();}
                    if(!AI2.isInvincible) try { damage(4); } catch (IOException e) { e.printStackTrace();}
                }
            }
        };

        gameLoop.start();
    }

    private void playerGo(int num, char dir) {

        if(num == 1) {
            if(dir == 'N') {
                playerIsPress1 = true;
                north1 = true;

                stepPause(1);
            }
            else if(dir == 'S') {
                playerIsPress1 = true;
                south1 = true;

                stepPause(1);
            }
            else if(dir == 'E') {
                playerIsPress1 = true;
                east1 = true;

                stepPause(1);
            }
            else if(dir == 'W') {
                playerIsPress1 = true;
                west1 = true;

                stepPause(1);
            }
        }

        else if(num == 2) {
            if(dir == 'N') {
                playerIsPress2 = true;
                north2 = true;

                stepPause(2);
            }
            else if(dir == 'S') {
                playerIsPress2 = true;
                south2 = true;

                stepPause(2);
            }
            else if(dir == 'E') {
                playerIsPress2 = true;
                east2 = true;

                stepPause(2);
            }
            else if(dir == 'W') {
                playerIsPress2 = true;
                west2 = true;

                stepPause(2);
            }
        }
    
        else if(num == 3) {
            if(dir == 'P') return;
            else if(dir == 'N') {
                if(!chunk[AI1.Y-1][AI1.X].getBlocked()) {
                    AIIsPress1 = true;
                    north3 = true;

                    stepPause(3);
                }
            }
            else if(dir == 'S') {
                if(!chunk[AI1.Y+1][AI1.X].getBlocked()) {
                    AIIsPress1 = true;
                    south3 = true;

                    stepPause(3);
                }
            }
            else if(dir == 'E') {
                if(!chunk[AI1.Y][AI1.X+1].getBlocked()) {
                    AIIsPress1 = true;
                    east3 = true;

                    stepPause(3);
                }
            }
            else if(dir == 'W') {
                if(!chunk[AI1.Y][AI1.X-1].getBlocked()) {
                    AIIsPress1 = true;
                    west3 = true;

                    stepPause(3);
                }
            }
            else if(dir == 'B') {
                setBomb(3);
            }
        }
    
        else if(num == 4) {
            if(dir == 'P') return;
            else if(dir == 'N') {
                if(!chunk[AI2.Y-1][AI2.X].getBlocked()) {
                    AIIsPress2 = true;
                    north4 = true;

                    stepPause(4);
                }
            }
            else if(dir == 'S') {
                if(!chunk[AI2.Y+1][AI2.X].getBlocked()) {
                    AIIsPress2 = true;
                    south4 = true;

                    stepPause(4);
                }
            }
            else if(dir == 'E') {
                if(!chunk[AI2.Y][AI2.X+1].getBlocked()) {
                    AIIsPress2 = true;
                    east4 = true;

                    stepPause(4);
                }
            }
            else if(dir == 'W') {
                if(!chunk[AI2.Y][AI2.X-1].getBlocked()) {
                    AIIsPress2 = true;
                    west4 = true;

                    stepPause(4);
                }
            }
            else if(dir == 'B') {
                setBomb(4);
            }
        }
    
        else if(num == 5) {
            if(dir == 'P') return;
            else if(dir == 'N') {
                if(!chunk[AI3.Y-1][AI3.X].getBlocked()) {
                    AIIsPress3 = true;
                    north5 = true;

                    stepPause(5);
                }
            }
            else if(dir == 'S') {
                if(!chunk[AI3.Y+1][AI3.X].getBlocked()) {
                    AIIsPress3 = true;
                    south5 = true;

                    stepPause(5);
                }
            }
            else if(dir == 'E') {
                if(!chunk[AI3.Y][AI3.X+1].getBlocked()) {
                    AIIsPress3 = true;
                    east5 = true;

                    stepPause(5);
                }
            }
            else if(dir == 'W') {
                if(!chunk[AI3.Y][AI3.X-1].getBlocked()) {
                    AIIsPress3 = true;
                    west5 = true;

                    stepPause(5);
                }
            }
            else if(dir == 'B') {
                setBomb(5);
            }
        }
    }

    private void stepPause(int num) {

        if(num == 1) {
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(player1.deltaDistance >= 48) {
                        chunk[player1.Y][player1.X].setPlayer(false);
                        if(north1) player1.Y--;
                        else if(south1) player1.Y++;
                        else if(east1)  player1.X++;
                        else if(west1)  player1.X--;

                        chunk[player1.Y][player1.X].setPlayer(true);
                        playerIsPress1 = false;
                        north1 = false;  
                        east1 = false;
                        south1 = false;
                        west1 = false;
                        player1.deltaDistance = 0;
                        stop();
                    }
                }
            }.start();
        }

        else if(num == 2) {

            new AnimationTimer() {

                @Override
                public void handle(long now) {
                    if(player2.deltaDistance >= 48) {
                        chunk[player2.Y][player2.X].setPlayer(false);
                        if(north2) player2.Y--;
                        else if(south2) player2.Y++;
                        else if(east2)  player2.X++;
                        else if(west2)  player2.X--;

                        chunk[player2.Y][player2.X].setPlayer(true);
                        playerIsPress2 = false;
                        north2 = false;  
                        east2 = false;
                        south2 = false;
                        west2 = false;
                        player2.deltaDistance = 0;
                        stop();
                    }
                }

            }.start();
        }
    
        else if(num == 3) {
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(AI1.deltaDistance >= 48) {
                        chunk[AI1.Y][AI1.X].setPlayer(false);
                        if(north3) AI1.Y--;
                        else if(south3) AI1.Y++;
                        else if(east3)  AI1.X++;
                        else if(west3)  AI1.X--;

                        chunk[AI1.Y][AI1.X].setPlayer(true);
                        AIIsPress1 = false;
                        north3 = false;  
                        east3 = false;
                        south3 = false;
                        west3 = false;
                        AI1.deltaDistance = 0;
                        stop();
                    }
                }
            }.start();
        }
    
        else if(num == 4) {
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(AI2.deltaDistance >= 48) {
                        chunk[AI2.Y][AI2.X].setPlayer(false);
                        if(north4) AI2.Y--;
                        else if(south4) AI2.Y++;
                        else if(east4)  AI2.X++;
                        else if(west4)  AI2.X--;

                        chunk[AI2.Y][AI2.X].setPlayer(true);
                        AIIsPress2 = false;
                        north4 = false;  
                        east4 = false;
                        south4 = false;
                        west4 = false;
                        AI2.deltaDistance = 0;
                        stop();
                    }
                }
            }.start();
        }
        
        else if(num == 5) {
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(AI3.deltaDistance >= 48) {
                        chunk[AI3.Y][AI3.X].setPlayer(false);
                        if(north5) AI3.Y--;
                        else if(south5) AI3.Y++;
                        else if(east5)  AI3.X++;
                        else if(west5)  AI3.X--;

                        chunk[AI3.Y][AI3.X].setPlayer(true);
                        AIIsPress3 = false;
                        north5 = false;  
                        east5 = false;
                        south5 = false;
                        west5 = false;
                        AI3.deltaDistance = 0;
                        stop();
                    }
                }
            }.start();
        }
    }
//////////////////////////////////////////////////////
    private void playerWalk(int num) {

        int dx = 0, dy = 0;

        if(num == 1) {
            // determine player is walking or not

            if (north1) dy -= 1;
            else if (south1) dy += 1;
            else if (east1)  dx += 1;
            else if (west1)  dx -= 1;

            if(dx == 0 && dy == 0) player1.stopWalking();
            else {
                player1.moveX(dx);
                player1.moveY(dy);
            }
        }

        if(num == 2) {

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

        if(num == 3) {

            // determine player is walking or not
            if (north3) dy -= 1;
            if (south3) dy += 1;
            if (east3)  dx += 1;
            if (west3)  dx -= 1;

            if(dx == 0 && dy == 0) AI1.stopWalking();
            else {
                AI1.moveX(dx);
                AI1.moveY(dy);
            }
        }
        
        if(num == 4) {

            // determine player is walking or not
            if (north4) dy -= 1;
            if (south4) dy += 1;
            if (east4)  dx += 1;
            if (west4)  dx -= 1;

            if(dx == 0 && dy == 0) AI2.stopWalking();
            else {
                AI2.moveX(dx);
                AI2.moveY(dy);
            }
        }

        if(num == 5) {

            // determine player is walking or not
            if (north5) dy -= 1;
            if (south5) dy += 1;
            if (east5)  dx += 1;
            if (west5)  dx -= 1;

            if(dx == 0 && dy == 0) AI3.stopWalking();
            else {
                AI3.moveX(dx);
                AI3.moveY(dy);
            }
        }
    }

    private void setBomb(int num){

        if(num == 1) {
            int y=player1.Y, x=player1.X;

            if(player1.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            controller.bombNumber1.setText(Integer.toString(--player1.bombNumber));

            chunk[y][x].isCareFul = true;
            for(int i=1 ; i<=player1.blastRange ; i++) {
                if(chunk[y-i][x].isWall) break;
                else chunk[y-i][x].isCareFul = true;
            }
            for(int i=1 ; i<=player1.blastRange ; i++) {
                if(chunk[y+i][x].isWall) break;
                else chunk[y+i][x].isCareFul = true;
            }
            for(int i=1 ; i<=player1.blastRange ; i++) {
                if(chunk[y][x-i].isWall) break;
                else chunk[y][x-i].isCareFul = true;
            }
            for(int i=1 ; i<=player1.blastRange ; i++) {
                if(chunk[y][x+i].isWall) break;
                else chunk[y][x+i].isCareFul = true;
            }

            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
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

            chunk[y][x].isCareFul = true;
            for(int i=1 ; i<=player2.blastRange ; i++) {
                if(chunk[y-i][x].isWall) break;
                else chunk[y-i][x].isCareFul = true;
            }
            for(int i=1 ; i<=player2.blastRange ; i++) {
                if(chunk[y+i][x].isWall) break;
                else chunk[y+i][x].isCareFul = true;
            }
            for(int i=1 ; i<=player2.blastRange ; i++) {
                if(chunk[y][x-i].isWall) break;
                else chunk[y][x-i].isCareFul = true;
            }
            for(int i=1 ; i<=player2.blastRange ; i++) {
                if(chunk[y][x+i].isWall) break;
                else chunk[y][x+i].isCareFul = true;
            }
            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        chunk[y][x].setImageView(null);
                        controller.bombNumber2.setText(Integer.toString(++player2.bombNumber));
                        bombBurst(y, x, player2.blastRange);
                        stop();
                    }
                }
                
            }.start();
        }

        if(num == 3) {

            int y=AI1.Y, x=AI1.X;

            if(chunk[y][x].imageView.getImage() == new Image("/resources/Images/elephantBomb.png")) return;
            if(AI1.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            AI1.bombNumber--;

            chunk[y][x].isCareFul = true;
            for(int i=1 ; i<=AI1.blastRange ; i++) {
                if(chunk[y-i][x].isWall) break;
                else chunk[y-i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI1.blastRange ; i++) {
                if(chunk[y+i][x].isWall) break;
                else chunk[y+i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI1.blastRange ; i++) {
                if(chunk[y][x-i].isWall) break;
                else chunk[y][x-i].isCareFul = true;
            }
            for(int i=1 ; i<=AI1.blastRange ; i++) {
                if(chunk[y][x+i].isWall) break;
                else chunk[y][x+i].isCareFul = true;
            }
            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        chunk[y][x].setImageView(null);
                        AI1.bombNumber++;
                        bombBurst(y, x, AI1.blastRange);
                        stop();
                    }
                }
                
            }.start();
        }
    
        if(num == 4) {

            int y=AI2.Y, x=AI2.X;

            if(chunk[y][x].imageView.getImage() == new Image("/resources/Images/elephantBomb.png")) return;
            if(AI2.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            AI2.bombNumber--;

            chunk[y][x].isCareFul = true;
            for(int i=1 ; i<=AI2.blastRange ; i++) {
                if(chunk[y-i][x].isWall) break;
                else chunk[y-i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI2.blastRange ; i++) {
                if(chunk[y+i][x].isWall) break;
                else chunk[y+i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI2.blastRange ; i++) {
                if(chunk[y][x-i].isWall) break;
                else chunk[y][x-i].isCareFul = true;
            }
            for(int i=1 ; i<=AI2.blastRange ; i++) {
                if(chunk[y][x+i].isWall) break;
                else chunk[y][x+i].isCareFul = true;
            }
            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        chunk[y][x].setImageView(null);
                        AI2.bombNumber++;
                        bombBurst(y, x, AI2.blastRange);
                        stop();
                    }
                }
                
            }.start();
        }
        
        if(num == 5) {

            int y=AI3.Y, x=AI3.X;

            if(chunk[y][x].imageView.getImage() == new Image("/resources/Images/elephantBomb.png")) return;
            if(AI3.bombNumber <= 0) return;

            chunk[y][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
            chunk[y][x].setBlocked(true);
            chunk[y][x].isFiringBomb = true;
            AI3.bombNumber--;

            chunk[y][x].isCareFul = true;
            for(int i=1 ; i<=AI3.blastRange ; i++) {
                if(chunk[y-i][x].isWall) break;
                else chunk[y-i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI3.blastRange ; i++) {
                if(chunk[y+i][x].isWall) break;
                else chunk[y+i][x].isCareFul = true;
            }
            for(int i=1 ; i<=AI3.blastRange ; i++) {
                if(chunk[y][x-i].isWall) break;
                else chunk[y][x-i].isCareFul = true;
            }
            for(int i=1 ; i<=AI3.blastRange ; i++) {
                if(chunk[y][x+i].isWall) break;
                else chunk[y][x+i].isCareFul = true;
            }
            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        chunk[y][x].setImageView(null);
                        AI3.bombNumber++;
                        bombBurst(y, x, AI3.blastRange);
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
            double duration = 0.5e9;

            @Override
            public void handle(long now) {
                if(previousTime == 0) {
                    previousTime = now;
                    chunk[y][x].setImageView(new Image("/resources/Images/fire.jpg"));
                    chunk[y][x].isDangered = true;
                }

                // Blast
                if(now-previousTime < r*(duration/range)) {

                    // UP
                    for(int i=1 ; canUp && i<=r ; i++) {
                        if(chunk[y-i][x].isWall) canUp = false;
                        else if(!chunk[y-i][x].getBlocked() || chunk[y-i][x].getCreatedItem() || chunk[y-i][x].isFiringBomb) {
                            chunk[y-i][x].clearItem();
                            chunk[y-i][x].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y-i][x].isDangered = true;
                        }
                    }
                    // DOWN
                    for(int i=1 ; canDown && i<=r ; i++) {
                        if(chunk[y+i][x].isWall) canDown = false;
                        else if(!chunk[y+i][x].getBlocked() || chunk[y+i][x].getCreatedItem() || chunk[y+i][x].isFiringBomb) {
                            chunk[y+i][x].clearItem();
                            chunk[y+i][x].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y+i][x].isDangered = true;
                        }
                    }
                    // LEFT
                    for(int i=1 ; canLeft && i<=r ; i++) {
                        if(chunk[y][x-i].isWall) canLeft = false;
                        else if(!chunk[y][x-i].getBlocked() || chunk[y][x-i].getCreatedItem() || chunk[y][x-i].isFiringBomb) {
                            chunk[y][x-i].clearItem();
                            chunk[y][x-i].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y][x-i].isDangered = true;
                        }
                    }
                    // RIGHT
                    for(int i=1 ; canRight && i<=r ; i++) {
                        if(chunk[y][x+i].isWall) canRight = false;
                        else if(!chunk[y][x+i].getBlocked() || chunk[y][x+i].getCreatedItem() || chunk[y][x+i].isFiringBomb) {
                            chunk[y][x+i].clearItem();
                            chunk[y][x+i].setImageView(new Image("/resources/Images/fire.jpg"));
                            chunk[y][x+i].isDangered = true;
                        }
                    }

                    if(r < range) r++;
                }

                // Show item
                if(now-previousTime > duration) {

                    // CENTER
                    chunk[y][x].setImageView(null);
                    chunk[y][x].isCareFul = false;
                    chunk[y][x].isDangered = false;
                    chunk[y][x].isFiringBomb = false;

                    // UP
                    for(int i=1 ; i<=range && !chunk[y-i][x].isWall; i++) {
                        chunk[y-i][x].isCareFul = false;
                        chunk[y-i][x].isDangered = false;
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
                        chunk[y+i][x].isCareFul = false;
                        chunk[y+i][x].isDangered = false;
                        if(!chunk[y+i][x].getBlocked()) chunk[y+i][x].setImageView(null);
                        if(chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y+i][x].getCreatedItem()) {
                            chunk[y+i][x].setBlocked(false);
                            chunk[y+i][x].setCreatedItem(false);
                            chunk[y+i][x].createItem();
                        }
                    }
                    // LEFT
                    for(int i=1 ; i<=range && !chunk[y][x-i].isWall ; i++) {
                        chunk[y][x-i].isCareFul = false;
                        chunk[y][x-i].isDangered = false;
                        if(!chunk[y][x-i].getBlocked()) chunk[y][x-i].setImageView(null);
                        if(chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y][x-i].getCreatedItem()) {
                            chunk[y][x-i].setBlocked(false);
                            chunk[y][x-i].setCreatedItem(false);
                            chunk[y][x-i].createItem();
                        }
                    }
                    // RIGHT
                    for(int i=1 ; i<=range && !chunk[y][x+i].isWall ; i++) {
                        chunk[y][x+i].isCareFul = false;
                        chunk[y][x+i].isDangered = false;
                        if(!chunk[y][x+i].getBlocked()) chunk[y][x+i].setImageView(null);
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
                if(player1.heart > 1) {
                    player1.heart--;
                    player1.isInvincible = true;
                    controller.heart1.setText(Integer.toString(player1.heart));

                    new AnimationTimer() {
                        double previousTime = 0;

                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now-previousTime >= 2.5e9) {
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
    
        if(num == 3) {
            if(chunk[AI1.Y][AI1.X].isDangered) {
                if(AI1.heart > 1) {
                    AI1.heart--;
                    AI1.isInvincible = true;

                    new AnimationTimer() {
                        double previousTime = 0;

                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now-previousTime >= 2.5e9) {
                                AI1.isInvincible = false;
                                stop();
                            }
                        }
                    }.start();
                }
                else {
                    AI1.imageView.setVisible(false);
                    AI1.animation.stop();
                    AI1.isDead = true;
                    playerAlive--;
                }
            }
            else return; 
        }
    
        if(num == 4) {
            if(chunk[AI2.Y][AI2.X].isDangered) {
                if(AI2.heart > 1) {
                    AI2.heart--;
                    AI2.isInvincible = true;

                    new AnimationTimer() {
                        double previousTime = 0;

                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now-previousTime >= 2.5e9) {
                                AI2.isInvincible = false;
                                stop();
                            }
                        }
                    }.start();
                }
                else {
                    AI2.imageView.setVisible(false);
                    AI2.animation.stop();
                    AI2.isDead = true;
                    playerAlive--;
                }
            }
            else return; 
        }
    
        if(num == 5) {
            if(chunk[AI3.Y][AI3.X].isDangered) {
                if(AI3.heart > 1) {
                    AI3.heart--;
                    AI3.isInvincible = true;

                    new AnimationTimer() {
                        double previousTime = 0;

                        @Override
                        public void handle(long now) {
                            if(previousTime == 0) previousTime = now; 
                            if(now-previousTime >= 2.5e9) {
                                AI3.isInvincible = false;
                                stop();
                            }
                        }
                    }.start();
                }
                else {
                    AI3.imageView.setVisible(false);
                    AI3.animation.stop();
                    AI3.isDead = true;
                    playerAlive--;
                }
            }
            else return; 
        }
    
    // if(isOnePlayer && playerAlive == 1) {

    // }
    }

}