
import java.util.Random;

public class ReactiveExplorer extends Agent {

    private World world;
    private int[] currentLocation, previousLocation;
    private boolean currentSafe, previousSafe;
    private int percepts, direction, arrowCount;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    public ReactiveExplorer(World world, int[] location, int direction, int percepts, int arrowCount) {
        this.world = world;
        this.arrowCount = arrowCount;
        this.previousLocation = location;
        this.currentLocation = location;
        this.percepts = percepts;
        this.direction = direction;
        if (((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE)) {
            previousSafe = true;
            currentSafe = true;
        }
    }

    private ReactiveExplorer() {

    }

    private void move(int action) {

        if (action == 1) {
            percepts = world.action(action);
            if ((percepts & GLITTER) == GLITTER) {      //found gold

            } else if ((percepts & DEATH_BY_PIT) == DEATH_BY_PIT) {
                death(DEATH_BY_PIT);
            } else if ((percepts & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                death(DEATH_BY_WUMPUS);
            }
            if ((percepts & BUMP) != BUMP) {
                previousSafe = currentSafe;
                previousLocation = currentLocation;
            }
            currentLocation = world.getLocation();
            currentSafe = ((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE);
        } else {
            world.action(action);
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

    private Agent death(byte killer) {

        currentLocation = previousLocation;
        currentSafe = previousSafe;
        
    }
}
