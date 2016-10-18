
public class World {

    int arrowCount;
    int size;
    int x;
    int y;
    int direction = 0;
    int[][] perceptMap;

    final int NORTH = 1;
    final int EAST = 2;
    final int SOUTH = 3;
    final int WEST = 4;
    private final int BREEZE = 1;
    private final int STENTCH = 2;
    private final int BUMP = 4;
    private final int GLITTER = 8;
    private final int DEATH_BY_WUMPUS = 16;
    private final int DEATH_BY_PIT = 32;
    private final int SCREAM = 64;

    final int GRAB = 1;
    final int MOVE = 2;
    final int TURN_LEFT = 3;
    final int TURN_RIGHT = 4;
    final int SHOOT = 5;
    final int END = 6;//needed?

    public World(String fileName) {
        x = 0;
        y = 0;
        //read in file
    }

    public int action(int action) {
        switch (action) {
            case GRAB:
                if ((perceptMap[x][y] & GLITTER) != 0) {
                    perceptMap[x][y] -= GLITTER;
                }
                break;
            case MOVE:
                if (direction == NORTH) {
                    if (y + 1 < size) {
                        y = y + 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == EAST) {
                    if (x + 1 < size) {
                        x = x + 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == SOUTH) {
                    if (y - 1 > 0) {
                        y -= 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == WEST) {
                    if (x - 1 > 0) {
                        x -= 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                }
                break;
            case TURN_LEFT:
                direction = (direction + 3) % 4 + 1;
                return perceptMap[x][y];
            case TURN_RIGHT:
                direction = (direction + 1) % 4 + 1;
                return perceptMap[x][y];
            case SHOOT:
            //shoot logic
            case END:
            //needed?
        }

        System.out.println("Error shouldn't ever get here");
        return -1;
    }
}
