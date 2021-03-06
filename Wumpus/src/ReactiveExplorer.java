
import java.util.ArrayList;

public class ReactiveExplorer extends Agent {

    private Location prevLocation;
    private State safeMap[][];
    private static final int FORWARD = 0, LEFT = 1, BACK = 2, RIGHT = 3;

    public ReactiveExplorer(World world, int arrows, int x, int y, int direction) {

        super(world, arrows, x, y, direction);
        prevLocation = location;
        percepts = world.getPercepts();
        safeMap = new State[World.size][World.size];
        safeMap[location.x][location.y] = State.SAFE;
        run();
    }

    private void run() {

        int i = 0;
        while (i < 50000) {
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
            switch (rand) {
                case FORWARD:     //try to go forward
                    System.out.println("Safe space, action is : " + "Go forward");
                    doAction(FORWARD, true);
                    break;
                case LEFT:     //try to go left
                    System.out.println("Safe space, action is : " + "Go left");
                    doAction(LEFT, true);
                    break;
                case 2:     //try go right
                    
                    System.out.println("Safe space, action is : " + "Go right");
                    doAction(RIGHT, true);
                    break;
                default:    //turn around
                    System.out.println("Safe space, action is : " + "Move backwards");
                    turnRight();
                    turnRight();
                    percepts = world.action(MOVE);
                    processPercepts();
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
                doAction(safeMoves.get(rand), true);
                percepts = world.action(safeMoves.get(rand) + 1);
            } else {
                int rand = random.nextInt(3);
                System.out.println("No safe action, action is : " + rand);
                doAction(rand, true);
            }
        }
    }

    public void doAction(int rand, boolean processPercepts){
        switch (rand) {
                    case FORWARD:
                        percepts = world.action(MOVE);
                        break;
                    case LEFT:
                        turnLeft();
                        percepts = world.action(MOVE);
                        break;
                    case RIGHT:
                        turnRight();
                        percepts = world.action(MOVE);
                        break;
                    case BACK:
                        turnRight();
                        turnRight();
                        percepts = world.action(MOVE);
                        break;
                }
        if(processPercepts)
            processPercepts();
    }
    private void processPercepts() {

        if ((percepts & BUMP) == BUMP) {
            Location forward = getForward();
            if (forward.x >= 0 && forward.x < World.size && forward.y >= 0 && forward.y < World.size) {
                safeMap[forward.x][forward.y] = State.UNSAFE;
            }
        } else if ((percepts & DEATH_WUMPUS) == DEATH_WUMPUS) {
            killWumpus();
        } else if ((percepts & DEATH_PIT) == DEATH_PIT) {
            Location forward = getForward();
            if (forward.x >= 0 && forward.x < World.size && forward.y >= 0 && forward.y < World.size) {
                safeMap[forward.x][forward.y] = State.UNSAFE;
            }
        } else {
            goForward();
        }
    }

    private boolean getSafe(int direction) {

        try {
            int trueDirection = (this.direction - direction + 4) % 4;
            switch (trueDirection) {
                case NORTH:
                    return (safeMap[location.x][location.y + 1] == State.SAFE);
                case SOUTH:
                    return safeMap[location.x][location.y - 1] == State.SAFE;
                case EAST:
                    return safeMap[location.x + 1][location.y] == State.SAFE;
                case WEST:
                    return safeMap[location.x - 1][location.y] == State.SAFE;
            }
            System.out.println("Invalid direction mapping, getSafe(): " + direction + "     " + trueDirection);
            return true;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private void updateSafe() {

        try {
            if (location.x < World.size - 1) {
                safeMap[location.x + 1][location.y] = State.SAFE;
            }
            if (location.x > 0) {
                safeMap[location.x - 1][location.y] = State.SAFE;
            }
            if (location.y < World.size - 1) {
                safeMap[location.x][location.y + 1] = State.SAFE;
            }
            if (location.y > 0) {
                safeMap[location.x][location.y - 1] = State.SAFE;
            }
        } catch (Exception e) {
            System.out.println("Location: " + location.x + ", " + location.y);
            throw e;
        }

    }

    private void goForward() {

        Location forward = getForward();
        if (forward.x >= 0 && forward.x < World.size && forward.y >= 0 && forward.y < World.size) {
            this.location = getForward();
            this.prevLocation = this.location;
        }

    }

    private void killWumpus() {

        System.out.println("Killing wumpus..");
        percepts = world.getPercepts();
        world.action(SHOOT);
        world.action(MOVE);
        this.prevLocation = this.location;
        this.location = getForward();
    }
}
