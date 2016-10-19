
import java.util.Random;

public class ReactiveExplorer extends Agent {

    private final World world;
    private Position currentPosition, previousPosition;
    private boolean currentSafe, previousSafe;
    private int percepts, arrowCount;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    public ReactiveExplorer(World world, Position startPosition, int percepts, int arrowCount) {
        this.world = world;
        this.arrowCount = arrowCount;
        this.previousPosition = startPosition;
        this.currentPosition = startPosition;
        this.percepts = percepts;
        if (((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE)) {
            previousSafe = true;
            currentSafe = true;
        }
    }

    private void move(int action) {

        if (action == 1) {                              //grab gold, game ends
            world.action(action);
        } else if (action == 2) {                       //move forward
            percepts = world.action(action);
            if ((percepts & BUMP) != BUMP) {            //did not bump into anything
                previousPosition = currentPosition;
                previousSafe = currentSafe;
                //update location
                int direction = currentPosition.direction;
                if (direction == World.NORTH) {         //go north
                    currentPosition.y++;
                } else if (direction == World.EAST) {   //go east
                    currentPosition.x++;
                } else if (direction == World.SOUTH) {  //go south
                    currentPosition.y--;
                } else {                                //go west
                    currentPosition.x--;
                }
            }
        } else if (action == 3 || action == 4) {        //turn
            world.action(action);
            previousPosition = currentPosition;         //there might be a conflict with recording previous states here..
            if (action == 3) {
                currentPosition.direction = ++currentPosition.direction % 4;        //turn left
            } else {
                currentPosition.direction = --currentPosition.direction % 4;        //turn right
            }
        } else if (action == 5) {       //shoot arrow
            world.action(action);
            arrowCount--;
        } else {                        //should never reach here
            System.out.println("Invalid action: " + action);
        }
    }

    public void decideNextAction(byte percepts) {

        //select safe neighboring cell else select unsafe neighboring cell
        if (((percepts & STENTCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
            Random random = new Random();
            switch (random.nextInt(3)) {
                case 1:     //go forward
                    move(1);
                    break;
                case 2:     //turn left and go forward
                    move(2);
                    move(1);
                    break;
                case 3:     //turn right and go forward
                    move(3);
                    move(1);
                    break;
                default:
                    System.out.println("Should not reach this line.");
            }
        } else {    //neighboring cells may not be safe
            //if previous cell was safe, return and pick new direction
            if (previousSafe) {
                move(3);
                move(3);
                move(1);
            } else {        //pick random move
                Random random = new Random();
                switch (random.nextInt(3)) {
                    case 1:     //go forward
                        move(1);
                        break;
                    case 2:     //turn left and go forward
                        move(2);
                        move(1);
                        break;
                    case 3:     //turn right and go forward
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

        currentPosition = previousPosition;

<<<<<<< HEAD
        currentLocation = previousLocation;
        currentSafe = previousSafe;
        return null;
=======
>>>>>>> 1a3066d3de5868b6f3d53ad8184e8eaf76909da5
    }
}
