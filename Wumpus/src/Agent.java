
import java.util.Random;

public class Agent {

    protected Location location;
    protected Direction direction;
    protected final int BREEZE = 1, STENCH = 2, BUMP = 4, GLITTER = 8, DEATH = 16, DEATH_WUMPUS = 32, SCREAM = 64;
    protected static final int GRAB = 1, MOVE = 2, TURN_LEFT = 3, TURN_RIGHT = 4, SHOOT = 5, QUIT = 6;
    protected int percepts, arrowCount;
    protected World world;
    protected Random random = new Random();

    public Agent(World world) {
        this.world = world;
        this.arrowCount = world.arrowCount;
        this.location = new Location(world.x, world.y);
        this.direction = direction.getDirection(0);
        this.percepts = world.getPercepts();
    }

    public enum Direction {
        
        NORTH(0), EAST(1), SOUTH(2), WEST(3);

        private final int id;
        
        private Direction(int id) {
            this.id = id;
        }
        
        Direction[] terms = values();

        public Direction left() {
            return terms[(this.ordinal() + 1) % terms.length];
        }

        public Direction right() {
            return terms[(this.ordinal() - 1) % terms.length];
        }
        
        public Direction getDirection(int i) {
            return terms[i];
        }
    }

    public enum State {
        SAFE,
        UNSAFE,
        EXPLORED;
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
