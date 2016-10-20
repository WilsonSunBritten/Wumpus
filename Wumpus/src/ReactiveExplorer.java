
public class ReactiveExplorer extends Agent {

    private Location prevLocation;
    private State curState, prevState;
    private boolean stench = false, breeze = false, bump = false, wumpus = false;
    private boolean safeMap[][];
    private static final int FORWARD = 0, LEFT = 1, BACK = 2, RIGHT = 3;

    public ReactiveExplorer(World world) {
        super(world);
        prevLocation = location;
        percepts = world.getPercepts();
        if (((percepts & STENCH) != STENCH) && ((percepts & BREEZE) != BREEZE)) {
            curState = State.SAFE;
            prevState = State.SAFE;
        }
        safeMap = new boolean[world.size][world.size];
        safeMap[location.x][location.y] = true;
        run();
    }

    private void run() {

        int i = 0;
        while (i < 10) {
            decideNextAction();
            i++;
        }
    }

    private void move() {

        if ((percepts & STENCH) != STENCH && (percepts & BREEZE) != BREEZE) {
            updateSafe();
            //go in random direction 
            int rand = random.nextInt(3);
            switch (rand) {
                case 0:     //try to go forward
                    percepts = world.action(MOVE);
                    if ((percepts & BUMP) != BUMP) {
                        return;
                    }
                case 1:     //try to go left
                    turnLeft();
                    percepts = world.action(MOVE);
                    if ((percepts & BUMP) != BUMP) {
                        return;
                    }
                case 2:     //try go right
                    turnRight();
                    percepts = world.action(MOVE);
                    if ((percepts & BUMP) != BUMP) {
                        return;
                    }
                default:
                    System.out.println("Move error, invalid case: " + rand);
            }
        }
        //getpercepts

        //need check boundries
        if (getSafe(FORWARD)) {
            //go forward
            move(MOVE);
        } else if (getSafe(LEFT)) {
            //go left

        } else if (getSafe(RIGHT)) {
            //go right
        } else if (getSafe(BACK)) { //back should always be safe?
            //idk this bits weird
        }
    }

    private boolean getSafe(int direction) {

        int trueDirection = (this.direction - direction) % 4;
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
        System.out.println("Invalid direction mapping, getSafe()");
        return true;
    }

    private void updateSafe() {

        safeMap[location.x + 1][location.y] = true;
        safeMap[location.x - 1][location.y] = true;
        safeMap[location.x][location.y + 1] = true;
        safeMap[location.x][location.y - 1] = true;
    }

    private byte move(int action) {

        switch (action) {
            case GRAB:
                return world.action(GRAB);
            case MOVE:
                percepts = world.action(MOVE);
                if ((percepts & BUMP) != BUMP) {                            //did not bump into anything
                    prevLocation = location;
                    prevState = curState;
                    updateLocation();
                } else if ((percepts & DEATH_WUMPUS) == DEATH_WUMPUS) {     //killed by a wumpus, therefore use revive potion and take revenge
                    killWumpus();
                } else if ((percepts & DEATH) == DEATH) {                   //killed by a pit
                    Location temp = location;
                    location = prevLocation;
                    prevLocation = temp;
                    prevState = State.UNSAFE;
                    curState = State.EXPLORED;
                }
                return percepts;
            case TURN_LEFT:
                turnLeft();
                return world.action(TURN_LEFT);
            case TURN_RIGHT:
                turnRight();
                return world.action(TURN_RIGHT);
            case SHOOT:
                arrowCount--;
                return world.action(SHOOT);
            case QUIT:
                return world.action(QUIT);
        }
        return 0b00000000;
    }

    public void decideNextAction() {    //select safe neighboring cell else select unsafe neighboring cell

        if (((percepts & STENCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
            int rand = random.nextInt(3);
            switch (rand) {
                case 0:                 //go forward
                    move(MOVE);
                    break;
                case 1:                 //turn left and go forward
                    move(TURN_LEFT);
                    move(MOVE);
                    break;
                case 2:                 //turn right and go forward
                    move(TURN_RIGHT);
                    move(MOVE);
                    break;
                default:
                    System.out.println("Invalid case: random action, reactive explorer (safe) rand = " + rand);
            }
        } else {                                    //neighboring cells may not be safe

            if (prevState == State.SAFE) {          //there might be a situation where the agent move back and forth between 3 safe safe spaces here we might need to account for
                move(TURN_LEFT);
                move(TURN_LEFT);
                move(MOVE);
            } else {                                //pick random move
                int rand = random.nextInt(3);
                switch (random.nextInt(3)) {
                    case 0:                         //go forward
                        move(MOVE);
                        break;
                    case 1:                         //turn left and go forward
                        move(TURN_LEFT);
                        move(MOVE);
                        break;
                    case 2:                         //turn right and go forward
                        move(TURN_RIGHT);
                        move(MOVE);
                        break;
                    default:
                        System.out.println("Invalid case: random action, reactive explorer (unsafe) rand = " + rand);
                }
            }
        }
    }

    private void killWumpus() {
        System.out.println("kill wumpus");
        location = prevLocation;
        percepts = world.getPercepts();
        move(SHOOT);
        move(MOVE);
    }
}
