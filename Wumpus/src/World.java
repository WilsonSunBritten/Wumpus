
import java.io.BufferedReader;
import java.io.FileReader;

public class World {

    protected int arrowCount;
    public int size;
    private int x;
    private int y;
    protected int direction = 0;
    private int[][] perceptMap;

    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
    private final int BREEZE = 1;
    private final int STENTCH = 2;
    private final int BUMP = 4;
    private final int GLITTER = 8;
    private final int DEATH_BY_WUMPUS = 16;
    private final int DEATH_BY_PIT = 32;
    private final int SCREAM = 64;

    static final int GRAB = 1;
    static final int MOVE = 2;
    static final int TURN_LEFT = 3;
    static final int TURN_RIGHT = 4;
    static final int SHOOT = 5;
    final int END = 6;//needed?

    public World(String fileName) {
        x = 0;
        y = 0;
        importMap(fileName);
        //read in file
    }

    public void importMap(String fileName) {
        try {
            FileReader in = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(in);
            String next;
            int size = Integer.parseInt(reader.readLine());
            perceptMap = new int[size][size];
            int i = 0;
            while ((next = reader.readLine()) != null) {//((Integer) reader.read()).toString()).equals("-1")) {
                int j = 0;
                while (next.contains(" ") && !next.equals(" ")) {
                    perceptMap[i][j] = Integer.parseInt(next.substring(0, next.indexOf(" ")));
                    
                    next = next.substring(next.indexOf(" ") + 1, next.length());
                    j++;
                }
                i++;
            }
            System.out.println("");
            for (int k = 0; k < perceptMap.length; k++) {
                for (int j = 0; j < perceptMap[k].length; j++) {
                    System.out.print(perceptMap[k][j] + " ");
                }
                System.out.println("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getLocation() {
        int[] location = {x, y};
        return location;
    }

    public int getPercepts() {
        return perceptMap[x][y];
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
                if (arrowCount < 1) {
                    return -1;      //out of arrows, which shouldn't be possible
                }
                arrowCount--;
                switch (direction) {
                    case 1: //shoot north
                        for (int i = y; i < perceptMap.length; i++) {
                            if (perceptMap[x][i] == 16) {       //hits Wumpus
                                return SCREAM;
                            } else if (perceptMap[x][i] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 2: //shoot east
                        for (int i = y; i < perceptMap.length; i++) {
                            if (perceptMap[i][y] == 16) {       //hits Wumpus
                                return SCREAM;
                            } else if (perceptMap[i][y] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 3: //shoot south
                        for (int i = y; i > 0; i--) {
                            if (perceptMap[x][i] == 16) {       //hits Wumpus
                                return SCREAM;
                            } else if (perceptMap[x][i] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 4: //shoot west
                        for (int i = y; i > 0; i--) {
                            if (perceptMap[i][y] == 16) {       //hits Wumpus
                                return SCREAM;
                            } else if (perceptMap[i][y] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    default:
                        System.out.println("Error in shooting logic.");
                }

            case END:
            //needed?
        }

        System.out.println("Error shouldn't ever get here");
        return -1;
    }
}
