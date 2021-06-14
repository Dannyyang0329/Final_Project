import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chunk {
    ImageView imageView = null;

    private int X;
    private int Y;
    
    private boolean isBlocked = false;
    private boolean canCreateItem = false;

    boolean isCareFul = false;
    boolean isDangered = false;
    boolean isFiringBomb = false;
    boolean isWall = false;
    boolean isPlayer = false;

    boolean isBoot = false;
    boolean isPotion = false;
    boolean isBomb = false;
    boolean isHeart = false;

    public void createItem() {
        int random = (int)(Math.random()*100)+1;

        if(random <= 50) {              // 50% nothing
            setImageView(null); 
            return;        
        }
        else if(random <= 68) {         // 18% speed up
            setImageView(new Image("/resources/Images/boot.png")); 
            isBoot = true;
        }
        else if(random <= 83) {         // 15% blast range increase
            setImageView(new Image("/resources/Images/potion.png")); 
            isPotion = true;
        }
        else if(random <= 95) {         // 12% bomb number increase
            setImageView(new Image("/resources/Images/bomb.png")); 
            isBomb = true;
        }
        else if(random <= 100) {        // 5% heart up
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

    public void setCreatedItem(boolean b) {
        canCreateItem = b;
    }
    public boolean getCreatedItem() {
        return canCreateItem;
    }



    public void clearItem() {
        isBoot = false; 
        isBomb = false;
        isPotion = false;
        isHeart = false;
    }
}
