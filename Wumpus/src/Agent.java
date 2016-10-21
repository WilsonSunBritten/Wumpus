
import java.util.Random;

public class Agent {

    protected Location location;
    protected int direction, arrowCount;
    protected final byte BREEZE = 0b00000001, STENCH = 0b0000010, BUMP = 0b00000100, GLITTER = 0b00001000, DEATH = 0b00010000, DEATH_WUMPUS = 0b00100000, SCREAM = 0b01000000;
    protected static final int GRAB = 1, MOVE = 2, TURN_LEFT = 3, TURN_RIGHT = 4, SHOOT = 5, QUIT = 6;
    protected static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    protected byte percepts;
    protected World world;
    protected Random random = new Random();

    public Agent(World world, int startingArrowCount, int startingX, int startingY, int startingDirection) {
        this.world = world;
        this.arrowCount = startingArrowCount;
        this.location = new Location(startingX, startingY);
        this.direction = startingDirection;
        this.percepts = world.getPercepts();
    }

    public Location getForward() {
        if (direction == NORTH) {
            return new Location(location.x, location.y + 1);
        } else if (direction == EAST) {
            return new Location(location.x + 1, location.y);
        } else if (direction == SOUTH) {
            return new Location(location.x, location.y - 1);
        } else {
            return new Location(location.x - 1, location.y);
        }
    }

    public void turnRight() {
        direction = (direction + 1) % 4;
        world.action(TURN_RIGHT);
    }

    public void turnLeft() {
        direction = (direction + 3) % 4;
        world.action(TURN_LEFT);
    }

    public int getRight() {
        return (direction + 1) % 4;
    }

    public int getLeft() {
        return (direction + 3) % 4;
    }

    public enum State { //im not sure were going to need this

        SAFE,
        UNSAFE,
        EXPLORED,
        UNEXPLORED;
    }

    public void updateLocation() {
        switch (direction) {
            case NORTH:
                if (location.y < World.size - 1) {
                    location.y += 1;
                }
                break;
            case EAST:
                if (location.x > 0) {
                    location.x -= 1;
                }
                break;
            case SOUTH:
                if (location.y > 0) {
                    location.y--;
                }
                break;
            case WEST:
                if (location.x < World.size - 1) {
                    location.x++;
                }
        }
    }

    protected class Location {

        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
