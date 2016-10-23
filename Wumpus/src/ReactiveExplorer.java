
import java.util.ArrayList;

public class ReactiveExplorer extends Agent {

    private Location prevLocation;
    private State curState, prevState;
    private boolean safeMap[][];
    private static final int FORWARD = 0, LEFT = 1, BACK = 2, RIGHT = 3;

    public ReactiveExplorer(World world, int arrows, int x, int y, int direction) {
        
        super(world, arrows, x, y, direction);
        prevLocation = location;
        percepts = world.getPercepts();
        if (((percepts & STENCH) != STENCH) && ((percepts & BREEZE) != BREEZE)) {
            curState = State.SAFE;
            prevState = State.SAFE;
        }
        safeMap = new boolean[World.size][World.size];
        safeMap[location.x][location.y] = true;
        run();
    }

    private void run() {
        
        int i = 0;
        while (i < 10) {
            move();
            i++;
        }
        world.action(QUIT);
    }

    private void move() {

        if ((percepts & GLITTER) == GLITTER) {
            world.action(GRAB);
        }
        
        System.out.println("Making new move");

        if ((percepts & STENCH) != STENCH && (percepts & BREEZE) != BREEZE) {       //all adjacent spaces are safe
            updateSafe();
            //go in random direction
            int rand = random.nextInt(3);
            System.out.println("Safe space, action is : " + rand);
            switch (rand) {
                case 0:     //try to go forward
                    percepts = world.action(MOVE);
                    break;
                case 1:     //try to go left
                    turnLeft();
                    percepts = world.action(MOVE);
                    turnRight();
                    break;
                case 2:     //try go right
                    turnRight();
                    percepts = world.action(MOVE);
                    turnLeft();
                    break;
                default:    //turn around
                    turnRight();
                    turnRight();
                    percepts = world.action(MOVE);
                    break;
            }
        } else {    //if forward is safe, go forward
            
            ArrayList<Integer> safeMoves = new ArrayList();
            if (getSafe(FORWARD)) {
                safeMoves.add(FORWARD);
            }
            if (getSafe(LEFT)) {
                safeMoves.add(LEFT);
            }
            if (getSafe(RIGHT)) {
                safeMoves.add(RIGHT);
            }
            if (safeMoves.size() > 0) {
                int rand = random.nextInt(safeMoves.size());
                percepts = world.action(safeMoves.get(rand) + 1);
            } else {
                int rand = random.nextInt(3);
                System.out.println("Unsafe space, action is : " + rand);
                switch (rand) {
                    case 0:
                        percepts = world.action(MOVE);
                        if ((percepts & BUMP) != BUMP) {
                            return;
                        }
                        break;
                    case 1:
                        turnLeft();
                        percepts = world.action(MOVE);
                        if ((percepts & BUMP) != BUMP) {
                            return;
                        }
                        turnRight();
                        break;
                    case 2:
                        turnRight();
                        percepts = world.action(MOVE);
                        if ((percepts & BUMP) != BUMP) {
                            return;
                        }
                        turnLeft();
                        break;
                    default:
                        turnRight();
                        turnRight();
                        percepts = world.action(MOVE);
                        break;
                }
            }
        }
    }

    private boolean getSafe(int direction) {

        try {
            int trueDirection = (this.direction - direction + 4) % 4;
            switch (trueDirection) {
                case NORTH:
                    return safeMap[location.x][location.y + 1];
                case SOUTH:
                    return safeMap[location.x][location.y - 1];
                case EAST:
                    return safeMap[location.x + 1][location.y];
                case WEST:
                    return safeMap[location.x - 1][location.y];
            }
            System.out.println("Invalid direction mapping, getSafe(): " + direction + "     " + trueDirection);
            return true;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private void updateSafe() {

        if (location.x < World.size - 1) {
            safeMap[location.x + 1][location.y] = true;
        }
        if (location.x > 0) {
            safeMap[location.x - 1][location.y] = true;
        }
        if (location.y < World.size - 1) {
            safeMap[location.x][location.y + 1] = true;
        }
        if (location.y > 0) {
            safeMap[location.x][location.y - 1] = true;
        }
    }

//    private byte move(int action) {
//
//        switch (action) {
//            case GRAB:
//                return world.action(GRAB);
//            case MOVE:
//                percepts = world.action(MOVE);
//                if ((percepts & BUMP) != BUMP) {                            //did not bump into anything
//                    prevLocation = location;
//                    prevState = curState;
//                    updateLocation();
//                } else if ((percepts & DEATH_WUMPUS) == DEATH_WUMPUS) {     //killed by a wumpus, therefore use revive potion and take revenge
//                    killWumpus();
//                } else if ((percepts & DEATH) == DEATH) {                   //killed by a pit
//                    Location temp = location;
//                    location = prevLocation;
//                    prevLocation = temp;
//                    prevState = State.UNSAFE;
//                    curState = State.EXPLORED;
//                }
//                return percepts;
//            case TURN_LEFT:
//                turnLeft();
//                return world.action(TURN_LEFT);
//            case TURN_RIGHT:
//                turnRight();
//                return world.action(TURN_RIGHT);
//            case SHOOT:
//                arrowCount--;
//                return world.action(SHOOT);
//            case QUIT:
//                return world.action(QUIT);
//        }
//        return 0b00000000;
//    }
//    public void decideNextAction() {    //select safe neighboring cell else select unsafe neighboring cell
//
//        if (((percepts & STENCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
//            int rand = random.nextInt(3);
//            switch (rand) {
//                case 0:                 //go forward
//                    move(MOVE);
//                    break;
//                case 1:                 //turn left and go forward
//                    move(TURN_LEFT);
//                    move(MOVE);
//                    break;
//                case 2:                 //turn right and go forward
//                    move(TURN_RIGHT);
//                    move(MOVE);
//                    break;
//                default:
//                    System.out.println("Invalid case: random action, reactive explorer (safe) rand = " + rand);
//            }
//        } else {                                    //neighboring cells may not be safe
//
//            if (prevState == State.SAFE) {          //there might be a situation where the agent move back and forth between 3 safe safe spaces here we might need to account for
//                move(TURN_LEFT);
//                move(TURN_LEFT);
//                move(MOVE);
//            } else {                                //pick random move
//                int rand = random.nextInt(3);
//                switch (random.nextInt(3)) {
//                    case 0:                         //go forward
//                        move(MOVE);
//                        break;
//                    case 1:                         //turn left and go forward
//                        move(TURN_LEFT);
//                        move(MOVE);
//                        break;
//                    case 2:                         //turn right and go forward
//                        move(TURN_RIGHT);
//                        move(MOVE);
//                        break;
//                    default:
//                        System.out.println("Invalid case: random action, reactive explorer (unsafe) rand = " + rand);
//                }
//            }
//        }
//    }
    private void killWumpus() {
        System.out.println("kill wumpus");
        location = prevLocation;
        percepts = world.getPercepts();
        world.action(SHOOT);
        world.action(MOVE);
    }
}
