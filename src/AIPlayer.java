import java.util.Stack;
import javafx.scene.image.ImageView;

public class AIPlayer extends Character{

    int aiMap[][] = new int[15][17];
    private int[][] brain = new int[15][17];
    private int[][] scanBrain = new int[15][17];
    
    private int[][] dir = new int[][]{
        {-1,0}, {1,0}, {0,-1}, {0,1}
    };
    private Stack<String> stack = new Stack<String>();
    private Stack<String> runStack = new Stack<String>();

    public AIPlayer(ImageView imageView, Chunk[][] chunk) {
        super(imageView);
        setAiMap(chunk);
        clearBrain();
        clearScanBrain();
    }

    public void setAiMap(Chunk[][] chunk) {
        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {
                if(!chunk[i][j].getBlocked()) {
                    if(!chunk[i][j].isCareFul && !chunk[i][j].isDangered) {
                        if(chunk[i][j].isBoot || chunk[i][j].isBomb || chunk[i][j].isPotion || chunk[i][j].isHeart) aiMap[i][j]=9;
                        else aiMap[i][j] = 0;
                    }
                    if(chunk[i][j].isCareFul) aiMap[i][j] = 3;
                    if(chunk[i][j].isDangered || chunk[i][j].isFiringBomb) aiMap[i][j] = 4;
                    continue; 
                }
                if(chunk[i][j].isWall) { aiMap[i][j] = 1; continue; }
                if(chunk[i][j].isFiringBomb) {aiMap[i][j] = 5; continue; }
                if(chunk[i][j].getBlocked() && chunk[i][j].getCreatedItem()) { aiMap[i][j] = 2; continue; }
            }
        }
    }

    public char getMoveDir() {
        clearScanBrain();

        int[] destination = new int[]{-1,-1};

        if(aiMap[Y][X]==3 || aiMap[Y][X]==4 || aiMap[Y][X]==5) {
            if(!runStack.isEmpty()) {
                System.out.println(runStack.toString());
                runStack.pop().charAt(0);
            }

            do
            {
                clearBrain();
                destination = scan(0);
                
                if(isArrivable(Y, X, destination[0], destination[1], true)) {
                    // clearScanBrain();
                    return runStack.pop().charAt(0);
                }
            }while(!(destination[0]==-1 && destination[1]==-1));
            return 'P';
        }

        if(aiMap[Y][X]==0) {
            if(!stack.isEmpty()) {
                System.out.println(stack.toString());
                return stack.pop().charAt(0);
            }

            do
            {
                clearBrain();
                destination = scan(9);
                
                if(isArrivable(Y, X, destination[0], destination[1], false)) {
                    return stack.pop().charAt(0);
                }
            }while(!(destination[0]==-1 && destination[1]==-1));
            return 'P';
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

                    scanBrain[i][j] = 1;
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    private boolean isArrivable(int playerY, int playerX, int desY, int desX, boolean isTimeToGo) {

        brain[playerY][playerX] = 1;

        if(playerY == desY && playerX == desX) return true;       

        // four direction
        for(int cnt=0; cnt<4 ; cnt++) {
            int moveY=playerY+dir[cnt][0], moveX=playerX+dir[cnt][1];

            if(!isTimeToGo) { 
                if((aiMap[moveY][moveX] != 0 && aiMap[moveY][moveX] != 9) || brain[moveY][moveX] == 1) continue;
            }
            else if((aiMap[moveY][moveX] != 0 && aiMap[moveY][moveX] != 9 && aiMap[moveY][moveX] != 3) || brain[moveY][moveX] == 1) continue;

            if(aiMap[moveY][moveX] == 0 || aiMap[moveY][moveX] == 9 || aiMap[moveY][moveX] == 3) {
                if(isArrivable(moveY, moveX, desY, desX, isTimeToGo)) {
                    if(isTimeToGo) {
                        if(cnt == 0) runStack.push("N");
                        if(cnt == 1) runStack.push("S");
                        if(cnt == 2) runStack.push("W");
                        if(cnt == 3) runStack.push("E");
                    }
                    else {
                        if(cnt == 0) stack.push("N");
                        if(cnt == 1) stack.push("S");
                        if(cnt == 2) stack.push("W");
                        if(cnt == 3) stack.push("E");
                    }

                    return true;
                }
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
        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {
                brain[i][j] = 0;
            }
        }
    }

    private void clearScanBrain() {
        for(int i=0 ; i<15 ; i++) {
            for(int j=0 ; j<17 ; j++) {
                scanBrain[i][j] = 0;
            }
        }
    }
}
