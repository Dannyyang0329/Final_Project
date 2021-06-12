import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class GameView {

    GameController controller;
    AnchorPane pane;
    Chunk chunk[][];
    Character player1;
    Character player2;
    private int playerNumber1 = 1;
    private int playerNumber2 = 1;
    private int mapNum;

    public static boolean isTwoPlayer = false;    
    public static boolean isOnePlayer = false;

    private boolean north1, south1, west1, east1;
    private boolean north2, south2, west2, east2;
    private boolean playerIsPress1;
    private boolean playerIsPress2;

    long previousTime1 = 0;
    long previousTime2 = 0;
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

    public GameView(int mapNum, GameController controller, int playerNumber1) {
        this.mapNum = mapNum;
        this.controller = controller;
        pane = controller.pane;
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
                    chunk[i][j].setImageView(new Image("resources/Images/sand.png"));
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
                        else {
                            playerIsPress1 = true;
                            north1 = true;
                            player1.Y--;
                            player1.deltaDistance = 0;

                            stepPause(1);
                        }
                    }
                    if(in == KeyCode.S) {
                        if(chunk[player1.Y+1][player1.X].getBlocked()) {
                            player1.direction = 'S';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            south1 = true;
                            player1.Y++;
                            player1.deltaDistance = 0;

                            stepPause(1);
                        }
                    }
                    if(in == KeyCode.A) {
                        if(chunk[player1.Y][player1.X-1].getBlocked()) {
                            player1.direction = 'A';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            west1 = true;
                            player1.X--;
                            player1.deltaDistance = 0;

                            stepPause(1);
                        }
                    }
                    if(in == KeyCode.D) {
                        if(chunk[player1.Y][player1.X+1].getBlocked()) {
                            player1.direction = 'D';
                            return;
                        }
                        else {
                            playerIsPress1 = true;
                            east1 = true;
                            player1.X++;
                            player1.deltaDistance = 0;

                            stepPause(1);
                        }
                    }

                    if(in == KeyCode.SPACE) {
                        setBomb(1);
                    }
                }

                if(isTwoPlayer && !playerIsPress2) {
                    KeyCode in = e.getCode();

                    if(in == KeyCode.UP) {
                        if(chunk[player2.Y-1][player2.X].getBlocked()) {
                            player2.direction = 'W';
                            return;
                        }
                        else {
                            playerIsPress2 = true;
                            north2 = true;
                            player2.Y--;
                            player2.deltaDistance = 0;

                            stepPause(2);
                        }
                    }
                    if(in == KeyCode.DOWN) {
                        if(chunk[player2.Y+1][player2.X].getBlocked()) {
                            player2.direction = 'S';
                            return;
                        }
                        else {
                            playerIsPress2 = true;
                            south2 = true;
                            player2.Y++;
                            player2.deltaDistance = 0;

                            stepPause(2);
                        }
                    }
                    if(in == KeyCode.LEFT) {
                        if(chunk[player2.Y][player2.X-1].getBlocked()) {
                            player2.direction = 'A';
                            return;
                        }
                        else {
                            playerIsPress2 = true;
                            west2 = true;
                            player2.X--;
                            player2.deltaDistance = 0;

                            stepPause(2);
                        }
                    }
                    if(in == KeyCode.RIGHT) {
                        if(chunk[player2.Y][player2.X+1].getBlocked()) {
                            player2.direction = 'D';
                            return;
                        }
                        else {
                            playerIsPress2 = true;
                            east2 = true;
                            player2.X++;
                            player2.deltaDistance = 0;

                            stepPause(2);
                        }
                    }
                    if(in == KeyCode.ENTER) {
                        setBomb(2);
                    }
                }
            }
        });
    }

    private void stepPause(int num) {

        if(num == 1) {
            pauseTimer1 = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(previousTime1 == 0) previousTime1 = now;
                    if(now-previousTime1 > 900000000/player1.speed) {
                        playerIsPress1 = false;
                        north1 = false;  
                        east1 = false;
                        south1 = false;
                        west1 = false;
                        previousTime1 = 0;
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
                    if(previousTime2 == 0) previousTime2 = now;
                    if(now-previousTime2 > 900000000/player2.speed) {
                        playerIsPress2 = false;
                        north2 = false;  
                        east2 = false;
                        south2 = false;
                        west2 = false;
                        previousTime2 = 0;
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
                playerWalk(1);
                if(isTwoPlayer) playerWalk(2);

                // Speed
                if(chunk[player1.Y][player1.X].isBoot) { 
                    chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].isBoot = false;
                    player1.speed++;
                    controller.speed1.setText(Integer.toString(player1.speed));
                }

                // Blast Range
                if(chunk[player1.Y][player1.X].isPotion) {
                    chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].isPotion = false;
                    player1.blastRange++;
                    controller.blastRange1.setText(Integer.toString(player1.blastRange));
                }

                // Bomb Number
                if(chunk[player1.Y][player1.X].isBomb) {
                    chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].isBomb = false;
                    player1.bombNumber++;
                    controller.bombNumber1.setText(Integer.toString(player1.bombNumber));
                }

                // Heart
                if(chunk[player1.Y][player1.X].isHeart) {
                    chunk[player1.Y][player1.X].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[player1.Y][player1.X].isHeart = false;
                    player1.heart++;
                    controller.heart1.setText(Integer.toString(player1.heart));
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
                        chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
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
            controller.bombNumber2.setText(Integer.toString(--player2.bombNumber));

            new AnimationTimer() {
                private long time = 0;

                @Override
                public void handle(long now) {
                    if(time == 0) time = now;
                    if(now-time >= 2.5e9) {
                        chunk[y][x].setBlocked(false);
                        chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
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
            double duration = 0.5e9;

            @Override
            public void handle(long now) {
                if(previousTime == 0) {
                    previousTime = now;
                    chunk[y][x].setImageView(new Image("/resources/Images/fire.jpg"));
                }

                // Blast
                if(now-previousTime>lastTimeShow && now-previousTime<=r*(duration/range)) {
                    if(now-previousTime%10 <= 1) return;

                    // UP
                    if(r<=range && canUp) {
                        if(chunk[y-r][x].isWall) canUp = false;
                        else if(!chunk[y-r][x].getBlocked() || chunk[y-r][x].getCreatedItem() || chunk[y-r][x].isFiringBomb) {
                            chunk[y-r][x].setImageView(new Image("/resources/Images/fire.jpg"));
                            // damage();
                        }
                    }
                    // DOWN
                    if(r<=range && canDown) {
                        if(chunk[y+r][x].isWall) canDown = false;
                        else if(!chunk[y+r][x].getBlocked() || chunk[y+r][x].getCreatedItem() || chunk[y+r][x].isFiringBomb) chunk[y+r][x].setImageView(new Image("/resources/Images/fire.jpg"));
                    }
                    // LEFT
                    if(r<=range && canLeft) {
                        if(chunk[y][x-r].isWall) canLeft = false;
                        else if(!chunk[y][x-r].getBlocked() || chunk[y][x-r].getCreatedItem() || chunk[y][x-r].isFiringBomb) chunk[y][x-r].setImageView(new Image("/resources/Images/fire.jpg"));
                    }
                    // RIGHT
                    if(r<=range && canRight) {
                        if(chunk[y][x+r].isWall) canRight = false;
                        else if(!chunk[y][x+r].getBlocked() || chunk[y][x+r].getCreatedItem() || chunk[y][x+r].isFiringBomb) chunk[y][x+r].setImageView(new Image("/resources/Images/fire.jpg"));
                    }

                    if(r+1 <= range) r++;
                }

                // Show item
                if(now-previousTime >= range*(duration/range)) {

                    // CENTER
                    chunk[y][x].setImageView(new Image("/resources/Images/sand.png"));
                    chunk[y][x].isFiringBomb = false;

                    // UP
                    for(int i=1 ; i<=range && !chunk[y-i][x].isWall; i++) {
                        if(!chunk[y-i][x].getBlocked()) chunk[y-i][x].setImageView(new Image("/resources/Images/sand.png"));
                        if(chunk[y-i][x].isFiringBomb) chunk[y-i][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y-i][x].getCreatedItem()) {
                            chunk[y-i][x].setBlocked(false);
                            chunk[y-i][x].setCreatedItem(false);
                            chunk[y-i][x].createItem();
                        }
                    }
                    // DOWN
                    for(int i=1 ; i<=range && !chunk[y+i][x].isWall; i++) {
                        if(!chunk[y+i][x].getBlocked() || chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(new Image("/resources/Images/sand.png"));
                        if(chunk[y+i][x].isFiringBomb) chunk[y+i][x].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y+i][x].getCreatedItem()) {
                            chunk[y+i][x].setBlocked(false);
                            chunk[y+i][x].setCreatedItem(false);
                            chunk[y+i][x].createItem();
                        }
                    }
                    // LEFT
                    for(int i=1 ; i<=range && !chunk[y][x-i].isWall ; i++) {
                        if(!chunk[y][x-i].getBlocked() || chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(new Image("/resources/Images/sand.png"));
                        if(chunk[y][x-i].isFiringBomb) chunk[y][x-i].setImageView(new Image(getClass().getResource("/resources/Images/elephantBomb.gif").toExternalForm()));
                        if(chunk[y][x-i].getCreatedItem()) {
                            chunk[y][x-i].setBlocked(false);
                            chunk[y][x-i].setCreatedItem(false);
                            chunk[y][x-i].createItem();
                        }
                    }
                    // RIGHT
                    for(int i=1 ; i<=range && !chunk[y][x+i].isWall ; i++) {
                        if(!chunk[y][x+i].getBlocked() || chunk[y][x+i].isFiringBomb) chunk[y][x+i].setImageView(new Image("/resources/Images/sand.png"));
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
}