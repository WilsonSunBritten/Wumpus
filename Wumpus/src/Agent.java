
import java.util.Random;

public abstract class Agent {

    Location curLoc;
    Direction direction;
    protected final byte BREEZE = 0b00000001, STENCH = 0b0000010, BUMP = 0b00000100, GLITTER = 0b00001000, DEATH = 0b00010000, DEATH_WUMPUS = 0b00100000, SCREAM = 0b01000000;
    protected int percepts, arrowCount;
    protected World world;
    protected Random random = new Random();

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        private final Direction[] terms = values();

        public Direction left() {
            return terms[(this.ordinal() + 1) % terms.length];
        }

        public Direction right() {
            return terms[(this.ordinal() - 1) % terms.length];
        }
    }

    enum State {
        SAFE,
        UNSAFE,
        EXPLORED;
    }

    public void updateLocation() {
        switch (direction) {
            case NORTH:
                if (curLoc.y < World.size - 1) {
                    curLoc.y += 1;
                }
                break;
            case EAST:
                if (curLoc.x > 0) {
                    curLoc.x -= 1;
                }
                break;
            case SOUTH:
                if (curLoc.y > 0) {
                    curLoc.y--;
                }
                break;
            case WEST:
                if (curLoc.x < World.size - 1) {
                    curLoc.x++;
                }
        }
    }

    class Location {

        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
