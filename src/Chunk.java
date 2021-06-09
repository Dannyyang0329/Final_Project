import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chunk {
    ImageView imageView = null;

    private int X;
    private int Y;
    
    private boolean isBlocked = false;
    private boolean canCreateItem = false;

    public void createItem() {
        double random = Math.random()*100+1;

        if(random <= 50) return;        // 50% nothing
        else if(random <= 68) {         // 18% speed up
            
        }
        else if(random <= 83) {         // 15% blast range increase

        }
        else if(random <= 95) {         // 12% bomb number increase

        }
        else if(random <= 100) {        // 5% heart up

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
}
