
import java.util.Random;

public class ReactiveExplorer {

    private World world;
    private int arrowCount;
    private int[] previousSpace;
    private boolean previousSafe;
    private int percepts;
    private int[] currentSpace;
    private boolean currentSafe;
    private int direction;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    public ReactiveExplorer(World world) {
        this.world = world;
        this.arrowCount = world.arrowCount;
        this.previousSpace = world.getLocation();
        this.currentSpace = previousSpace;
        this.percepts = world.getPercepts();
        this.direction = world.direction;
        if (((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE)) {
            previousSafe = true;
            currentSafe = true;
        }
    }

    private void move(int action) {

        if (action == 1) {
            percepts = world.action(action);
            if ((percepts & GLITTER) == GLITTER) {      //found gold

            } else if ((percepts & DEATH_BY_PIT) == DEATH_BY_PIT || (percepts & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                //got dicked
            }
            if ((percepts & BUMP) != BUMP) {
                previousSafe = currentSafe;
                previousSpace = currentSpace;
            }
            currentSpace = world.getLocation();
            currentSafe = ((percepts & STENTCH) != STENTCH) && ((percepts & BREEZE) != BREEZE);
        } else {
            world.action(action);
        }
    }

    public void decideAction() {

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
}
