
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class World {

    public static final int NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
    public static final int GRAB = 1, MOVE = 2, TURN_LEFT = 3, TURN_RIGHT = 4, SHOOT = 5, QUIT = 6;
    public static final int BREEZE = 1, STENTCH = 2, BUMP = 4, GLITTER = 8, DEATH_BY_WUMPUS = 16, DEATH_BY_PIT = 32, SCREAM = 64;

    protected int arrowCount, x, y, direction = 0, score = 0;
    public static int size;
    private int[][] perceptMap;

    public World(String fileName) {
        importMap(fileName);
    }

    public void importMap(String fileName) {
        try {
            FileReader in = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(in);
            String next;
            size = Integer.parseInt(reader.readLine());
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
            for (int[] row : perceptMap) {
                for (int j = 0; j < row.length; j++) {
                    System.out.print(row[j] + " ");
                }
                System.out.println("");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Exception caught: " + e);
        }
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
                score += 1000;
                System.out.println("Gold found!\nScore: " + score);
                System.exit(0);
                break;
            case MOVE:
                score--;
                if (direction == NORTH) {
                    if (y + 1 < size) {
                        if ((perceptMap[x][y] & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                            score -= 1000;
                            return DEATH_BY_WUMPUS;
                        }
                        if ((perceptMap[x][y] & DEATH_BY_PIT) == DEATH_BY_PIT) {
                            score -= 1000;
                            return DEATH_BY_PIT;
                        }
                        y = y + 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == EAST) {
                    if ((perceptMap[x][y] & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                        score -= 1000;
                        return DEATH_BY_WUMPUS;
                    }
                    if ((perceptMap[x][y] & DEATH_BY_PIT) == DEATH_BY_PIT) {
                        score -= 1000;
                        return DEATH_BY_PIT;
                    }
                    if (x + 1 < size) {
                        x = x + 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == SOUTH) {
                    if ((perceptMap[x][y] & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                        score -= 1000;
                        return DEATH_BY_WUMPUS;
                    }
                    if ((perceptMap[x][y] & DEATH_BY_PIT) == DEATH_BY_PIT) {
                        score -= 1000;
                        return DEATH_BY_PIT;
                    }
                    if (y - 1 > 0) {
                        y -= 1;
                        return perceptMap[x][y];
                    } else {
                        return BUMP;
                    }
                } else if (direction == WEST) {
                    if ((perceptMap[x][y] & DEATH_BY_WUMPUS) == DEATH_BY_WUMPUS) {
                        score -= 1000;
                        return DEATH_BY_WUMPUS;
                    }
                    if ((perceptMap[x][y] & DEATH_BY_PIT) == DEATH_BY_PIT) {
                        score -= 1000;
                        return DEATH_BY_PIT;
                    }
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
                                removeWumpus(x, i);
                                return SCREAM;
                            } else if (perceptMap[x][i] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 2: //shoot east
                        for (int i = y; i < perceptMap.length; i++) {
                            if (perceptMap[i][y] == 16) {       //hits Wumpus
                                removeWumpus(i, y);
                                return SCREAM;
                            } else if (perceptMap[i][y] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 3: //shoot south
                        for (int i = y; i > 0; i--) {
                            if (perceptMap[x][i] == 16) {       //hits Wumpus
                                removeWumpus(x, i);
                                return SCREAM;
                            } else if (perceptMap[x][i] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case 4: //shoot west
                        for (int i = y; i > 0; i--) {
                            if (perceptMap[i][y] == 16) {       //hits Wumpus
                                removeWumpus(i, y);
                                return SCREAM;
                            } else if (perceptMap[i][y] == 4) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    default:
                        System.out.println("Error in shooting logic.");
                }
            case QUIT:
                System.out.println("Agent elected to end game.");
                System.exit(0);
        }
        System.out.println("Error shouldn't ever get here");
        return -1;
    }

    private void removeWumpus(int x, int y) {
        
        System.out.println("Wumpus slain at " + x + ", " + y + "!!!");

        //remove death_by_wumpus percepts from x, y
        perceptMap[x][y] = perceptMap[x][y] & ~DEATH_BY_WUMPUS;

        //remove stench percepts form spaces adjacent to wumpus
        if (x > 0) {
            //remove stentch to left
            perceptMap[x - 1][y] = perceptMap[x - 1][y] & ~STENTCH;
            if (x < size - 1) {
                //remove stentch to right
                perceptMap[x + 1][y] = perceptMap[x + 1][y] & ~STENTCH;
            }
        }
        if (y > 0) {
                //remove stentch below
            perceptMap[x][y - 1] = perceptMap[x][y - 1] & ~STENTCH;
            if (y < size - 1) {
                //remove stentch above
                perceptMap[x][y + 1] = perceptMap[x][y + 1] & ~STENTCH;
            }
        }
    }
}
