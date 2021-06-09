import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
    ImageView imageView;
    int count = 3;
    int column = 3;
    int offsetX = 0;
    int offsetY = 0;
    int width = 48;
    int height = 48;
    int speed = 2;

    final int DISTANCE = 48;
    double deltaDistance = 0;
    char direction = 'S';
    int X=0;
    int Y=0;
    
    SpriteAnimation animation;

    public Character(ImageView imageView) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new SpriteAnimation(imageView, Duration.millis(500), count, column, offsetX, offsetY, width, height);
        getChildren().addAll(imageView);
    }

    public void moveX(int dx) {
        boolean isGoingRight = (dx >= 0) ? true : false;

        for(int i=0 ; i<Math.abs(dx) && deltaDistance<DISTANCE; i++) {

            // going east
            if(isGoingRight) {
                direction = 'D';
                this.animation.setCount(3);
                this.animation.setOffsetX(0);
                this.animation.setOffsetY(96);
                this.setTranslateX(this.getTranslateX() + speed);
                deltaDistance += speed;
            }

            // going west
            else{
                direction = 'A';
                this.animation.setCount(3);
                this.animation.setOffsetX(0);
                this.animation.setOffsetY(48);
                this.setTranslateX(this.getTranslateX() - speed);
                deltaDistance += speed;
            }
        }
    }

    public void moveY(int dy) {
        boolean isGoingDown = (dy >= 0) ? true : false;

        for(int i=0 ; i<Math.abs(dy) && deltaDistance<DISTANCE; i++) {
            // going south
            if(isGoingDown) 
            {
                direction = 'S';
                this.animation.setCount(3);
                this.animation.setOffsetX(0);
                this.animation.setOffsetY(0);
                this.setTranslateY(this.getTranslateY() + speed);
                deltaDistance += speed;
            }

            // going north
            else
            {
                direction = 'W';
                this.animation.setCount(3);
                this.animation.setOffsetX(0);
                this.animation.setOffsetY(144); 
                this.setTranslateY(this.getTranslateY() - speed);
                deltaDistance += speed;
            }
        }
    }

    public void stopWalking() {
        if(direction == 'W') {
            this.animation.setCount(1);
            this.animation.setOffsetX(48);
            this.animation.setOffsetY(144);
        }
        else if(direction == 'A') {
            this.animation.setCount(1);
            this.animation.setOffsetX(48);
            this.animation.setOffsetY(48);
        }
        else if(direction == 'S') {
            this.animation.setCount(1);
            this.animation.setOffsetX(48);
            this.animation.setOffsetY(0);
        }
        else if(direction == 'D') {
            this.animation.setCount(1);
            this.animation.setOffsetX(48);
            this.animation.setOffsetY(96);
        }
    }

    public void setChunk(int y, int x) {
        this.X = x;
        this.Y = y;
    }

}
