
import java.util.ArrayList;


public abstract class Agent {
    
    
    private void move(int action) {
        
    }
    
    public void decideNextAction() {
        
    }
    
    class Position{
        int x;
        int y;
        int direction;
        
        public void moveDidMove(){
            switch(direction){
                case World.NORTH:
                    y+=1;
                    break;
                case World.EAST:
                    x-=1;
                    break;
                case World.SOUTH:
                    y--;
                    break;
                case World.WEST:
                    x++;
            }
        }
    }
}
