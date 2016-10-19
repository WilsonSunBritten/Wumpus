
import java.util.Random;

public class ReactiveExplorer extends Agent {

    private final World world;
    private Position prevPos;
    private State curState, prevState;
    private int percepts, arrowCount;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    enum State {

        SAFE,
        UNSAFE,
        EXPLORED;
    }

    public ReactiveExplorer(World world) {
        this.world = world;
        arrowCount = world.arrowCount;
        curPos = new Position(world.getLocation()[0], world.getLocation()[1], world.direction);
        prevPos = curPos;
        percepts = world.getPercepts();
        if (((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE)) {
            curState = State.SAFE;
            prevState = State.SAFE;
        }
        run();
    }

    private void run() {

        while (true) {
            decideNextAction((byte) percepts);
        }
    }

    private void move(int action) {

        if (action == 1) {                              //grab gold, game ends
            world.action(action);
        } else if (action == 2) {                       //move forward
            percepts = world.action(action);
            if ((percepts & BUMP) != BUMP) {            //did not bump into anything
                prevPos = this.curPos;
                prevState = curState;
                curPos.moveDidMove();
            } else if ((percepts & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {       //killed by a wumpus, therefore use revive potion and take revenge
                move(5);
                move(2);
            } else if ((percepts & DEATH_BY_PIT) == DEATH_BY_PIT) {             //killed by a pit
                Position temp = curPos;
                curPos = prevPos;
                prevPos = temp;
                prevState = State.UNSAFE;
                curState = State.EXPLORED;
            }
        } else if (action == 3 || action == 4) {        //turn
            world.action(action);
            prevPos = curPos;
            if (action == 3) {
                curPos.direction = ++curPos.direction % 4;        //turn left
            } else {
                curPos.direction = --curPos.direction % 4;        //turn right
            }
        } else if (action == 5) {       //shoot arrow
            world.action(action);
            arrowCount--;
        } else {                        //should never reach here
            System.out.println("Invalid action: " + action);
        }
    }

    public void decideNextAction(byte percepts) {       //select safe neighboring cell else select unsafe neighboring cell

        if (((percepts & STENTCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
            Random random = new Random();
            switch (random.nextInt(3)) {
                case 1:             //go forward
                    move(1);
                    break;
                case 2:             //turn left and go forward
                    move(2);
                    move(1);
                    break;
                case 3:             //turn right and go forward
                    move(3);
                    move(1);
                    break;
                default:
                    System.out.println("Should not reach this line.");
            }
        } else {                                    //neighboring cells may not be safe
            //if previous cell was safe, return and pick new direction
            if (prevState == State.SAFE) {                     //there might be a situation where the agent move back and forth between 3 safe safe spaces here we might need to account for
                move(3);
                move(3);
                move(1);
            } else {                                //pick random move
                Random random = new Random();
                switch (random.nextInt(3)) {
                    case 1:                         //go forward
                        move(1);
                        break;
                    case 2:                         //turn left and go forward
                        move(2);
                        move(1);
                        break;
                    case 3:                         //turn right and go forward
                        move(3);
                        move(1);
                        break;
                    default:
                        System.out.println("Should not reach this line.");
                }
            }
        }
    }

    private void death(byte killer) {       //there needs to be some record of what killed the agent in this space so it doesnt go back again..


    }
}
