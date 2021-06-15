import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chunk {
    ImageView imageView = null;

    private int X;
    private int Y;
    
    private boolean isBlocked = false;
    private boolean isWall = false;
    private boolean canCreateItem = false;
    private boolean isCareFul = false;
    private boolean isDangered = false;
    private boolean isFiringBomb = false;
    private boolean isPlayer = false;

    boolean isBoot = false;
    boolean isPotion = false;
    boolean isBomb = false;
    boolean isHeart = false;

    public void createItem() {
        int random = (int)(Math.random()*100)+1;

        if(random <= 65) {              // 65% nothing
            setImageView(null); 
            return;        
        }
        else if(random <= 75) {         // 10% speed up
            setImageView(new Image("/resources/Images/boot.png")); 
            isBoot = true;
        }
        else if(random <= 85) {         // 10% blast range increase
            setImageView(new Image("/resources/Images/potion.png")); 
            isPotion = true;
        }
        else if(random <= 93) {         // 8% bomb number increase
            setImageView(new Image("/resources/Images/bomb.png")); 
            isBomb = true;
        }
        else if(random <= 100) {        // 7% heart up
            setImageView(new Image("/resources/Images/heart.png")); 
            isHeart = true;
        }
    }

    public int getX() {
        return X;
    }
    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }
    public void setY(int y) {
        Y = y;
    }

    public void setImageView(Image image) {
        if(imageView == null) imageView = new ImageView();
        imageView.setImage(image);
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void setBlocked(boolean b) {
        isBlocked = b;
    }
    public boolean getBlocked() {
        return isBlocked;
    }

    public void setWall(boolean b) {
        isWall = b;
    }
    public boolean getWall() {
        return isWall;
    }

    public void setCreatedItem(boolean b) {
        canCreateItem = b;
    }
    public boolean getCreatedItem() {
        return canCreateItem;
    }

    public void setCareFul(boolean b) {
        isCareFul = b;
    }
    public boolean getCareFul() {
        return isCareFul;
    }

    public void setDangered(boolean b) {
        isDangered = b;
    }
    public boolean getDangered() {
        return isDangered;
    }

    public void setFiringBomb(boolean b) {
        isFiringBomb = b;
    }
    public boolean getFiringBomb() {
        return isFiringBomb;
    }
    
    public void setPlayer(boolean b) {
        isPlayer = b;
    }
    public boolean getPlayer() {
        return isPlayer;
    }
    
    public void clearItem() {
        isBoot = false; 
        isBomb = false;
        isPotion = false;
        isHeart = false;
    }
}
