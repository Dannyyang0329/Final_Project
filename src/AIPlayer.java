import java.util.Arrays;
import java.util.Stack;
import javafx.scene.image.ImageView;

public class AIPlayer extends Character{

    private int aiMap[][] = new int[15][17];
    private int[][] brain = new int[15][17];
    private int[][] scanBrain = new int[15][17];
    
    private int[][] dir = new int[][]{
        {-1,0}, {1,0}, {0,-1}, {0,1}
    };

    private Stack<String> stack = new Stack<String>();
    private Stack<String> runStack = new Stack<String>();
    private int[] destination = new int[]{-1,-1};
    private char lastChar = 'P';

    // constructor
    public AIPlayer(ImageView imageView, Chunk[][] chunk) {
        super(imageView);
        setAiMap(chunk);
        clearBrain();
        clearScanBrain();
    }

    public void setAiMap(Chunk[][] chunk) {

        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {

                aiMap[i][j] = 0;
                if(chunk[i][j].getWall() && chunk[i][j].getBlocked() aiMap[i][j] = 1;
                if(chunk[i][j].getCreatedItem() && chunk[i][j].getBlocked()) aiMap[i][j] = 2;
                if(chunk[i][j].getCareFul()) aiMap[i][j] = 3;
                if(chunk[i][j].getDangered()) aiMap[i][j] = 4;
                if(chunk[i][j].getFiringBomb()) aiMap[i][j] = 5;
                if(chunk[i][j].isBoot || chunk[i][j].isBomb || chunk[i][j].isPotion || chunk[i][j].isHeart) aiMap[i][j] = 9;

            }
        }
    }

    public char getMoveDir() {
        clearScanBrain();

        //  Step on danger place.
        if(aiMap[Y][X]==3 || aiMap[Y][X]==4 || aiMap[Y][X]==5) {

            stack.removeAllElements();

            if(!runStack.isEmpty()) {
                char tmp = runStack.pop().charAt(0);
                if( (tmp=='N' && lastChar=='S') ||
                    (tmp=='S' && lastChar=='N') ||
                    (tmp=='W' && lastChar=='E') ||
                    (tmp=='E' && lastChar=='W')  ) return 'P';

                else return tmp;
            }
            else {
                do
                {
                    destination = scan(0);
                    
                    clearBrain();
                    if(isArrivable(Y, X, destination[0], destination[1], true)) {
                        lastChar = runStack.pop().charAt(0);
                        return lastChar;
                    }
                }while(!(destination[0]==-1 && destination[1]==-1));

                // There are not save place for AI to go
                return 'P';
            }
        }

        // Step on save place
        if(aiMap[Y][X] == 0) {
            if(!stack.isEmpty()) {
                return stack.pop().charAt(0);
            }
            else {
                do
                {
                    destination = scan(9);
                    
                    clearBrain();
                    if(isArrivable(Y, X, destination[0], destination[1], false)) {
                        return stack.pop().charAt(0);
                    }
                }while(!(destination[0]==-1 && destination[1]==-1));

                // No special item to get -> set bomb -> danger -> run
                clearScanBrain();
                do
                {
                    destination = scan(0);
                    
                    clearBrain();
                    if(isArrivable(Y, X, destination[0], destination[1], false)) {
                        return 'B';
                    }
                }while(!(destination[0]==-1 && destination[1]==-1));

                return 'P';
            }
        }
        
        return 'P';
    }

    private int[] scan(int num) {

        for(int radius=1; radius<=15 ; radius++) {
            for(int i=Y-radius ; i<=Y+radius ; i++) {

                if(i<=0 || i>13) continue;
                for(int j=X-radius ; j<=X+radius ; j++) {
                    if(j<=0 || j>15) continue;
                    if(scanBrain[i][j] == 1) continue;

                    if(aiMap[i][j] != num) {
                        scanBrain[i][j] = 1;
                        continue;                   
                    }
                    else {
                        scanBrain[i][j] = 1;
                        return new int[]{i,j};
                    }
                }
            }
        }

        // scan failed
        return new int[]{-1,-1};
    }

    private boolean isArrivable(int playerY, int playerX, int desY, int desX, boolean canPassCareFul) {

        brain[playerY][playerX] = 1;

        if(playerY == desY && playerX == desX) return true;       

        // four direction
        for(int cnt=0; cnt<4 ; cnt++) {
            int moveY=playerY+dir[cnt][0], moveX=playerX+dir[cnt][1];

            if(!canPassCareFul) {
                if((aiMap[moveY][moveX] != 0 && aiMap[moveY][moveX] != 9) || brain[moveY][moveX] == 1) continue;
            }
            else {
                if((aiMap[moveY][moveX] != 0 && aiMap[moveY][moveX] != 9 && aiMap[moveY][moveX] != 3) || brain[moveY][moveX] == 1) continue;
            }

            if(isArrivable(moveY, moveX, desY, desX, canPassCareFul)) {
                if(canPassCareFul) {
                    if(cnt == 0) runStack.push("N");        // North
                    if(cnt == 1) runStack.push("S");        // South
                    if(cnt == 2) runStack.push("W");        // West
                    if(cnt == 3) runStack.push("E");        // East
                }
                else {
                    if(cnt == 0) stack.push("N");           // North
                    if(cnt == 1) stack.push("S");           // South    
                    if(cnt == 2) stack.push("W");           // West
                    if(cnt == 3) stack.push("E");           // East
                }

                return true;
            }
        }

        brain[playerY][playerX] = 0;
        return false;
    }

    public void showMap() {
        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {
                // System.out.print(aiMap[i][j]+" ");
                if(brain[i][j]==0) System.out.print("  ");
                else System.out.print(brain[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void clearBrain() {
        Arrays.fill(brain, 0);
        // for(int i=0 ; i<15 ; i++) {
        //     for(int j=0 ; j<17 ; j++) {
        //         brain[i][j] = 0;
        //     }
        // }
    }

    private void clearScanBrain() {
        Arrays.fill(scanBrain, 0);
        // for(int i=0 ; i<15 ; i++) {
        //     for(int j=0 ; j<17 ; j++) {
        //         scanBrain[i][j] = 0;
        //     }
        // }
    }
}
