
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class World {

    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    public static final int GRAB = 1, MOVE = 2, TURN_LEFT = 3, TURN_RIGHT = 4, SHOOT = 5, QUIT = 6;
    protected final byte BREEZE = 0b00000001, STENCH = 0b0000010, BUMP = 0b00000100, GLITTER = 0b00001000, DEATH_PIT = 0b00010000, DEATH_WUMPUS = 0b00100000, SCREAM = 0b01000000;

    private int arrowCount, x, y, direction = 0, score = 0, numMoves = 0, pitDeaths = 0, wumpusDeaths = 0, killedWumpus = 0;
    public static int size;
    private byte[][] perceptMap;

    public World(String fileName) {
        importMap(fileName);
        for (int i = 0; i < perceptMap.length; i++) {
            for (int j = 0; j < perceptMap.length; j++) {
                if ((perceptMap[i][j] & DEATH_WUMPUS) != 0) {
                    arrowCount++;
                }
            }

        }
        System.out.println("Starting world:");
        printWorld();
    }

    public void startGame(String id) {
        Agent explorer;
        switch (id) {
            case "LogicExplorer":
                explorer = new LogicExplorer(this, arrowCount, x, y, direction);
                break;
            case "ReactiveExplorer":
                explorer = new ReactiveExplorer(this, arrowCount, x, y, direction);
                break;
        }

    }

    public void importMap(String fileName) {
        try {

            FileReader in1 = new FileReader(fileName);
            BufferedReader reader1 = new BufferedReader(in1);

            FileReader in = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(in);
            String next = reader.readLine();
            size = Integer.parseInt(next.substring(0, next.indexOf(" ")));
            next = next.substring(next.indexOf(" ") + 1);
            x = Integer.parseInt(next.substring(0, next.indexOf(" ")));
            next = next.substring(next.indexOf(" ") + 1);
            y = Integer.parseInt(next.substring(0, next.indexOf(" ")));
            perceptMap = new byte[size][size];
            int i = 0;
            while ((next = reader.readLine()) != null) {//((Integer) reader.read()).toString()).equals("-1")) {
                int j = 0;
                while (next.contains(" ") && !next.equals(" ")) {
                    perceptMap[i][j] = (byte) Integer.parseInt(next.substring(0, next.indexOf(" ")));

                    next = next.substring(next.indexOf(" ") + 1, next.length());
                    j++;
                }
                i++;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Exception caught: " + e);
        }
    }

    public byte getPercepts() {
        return perceptMap[x][y];
    }

    public void printStats() {
        System.out.println("Number of Actions: " + numMoves);
        System.out.println("Final score: " + score);
        System.out.println("Wumpus Deaths: " + wumpusDeaths);
        System.out.println("Pit Deaths: " + pitDeaths);
        System.out.println("Killed Wumpus's: " + killedWumpus);
        System.out.println("Leftover arrows: " + arrowCount);
    }

    public void printWorld() {

        for (int i = perceptMap.length - 1; i >= 0; i--) {
            for (int j = 0; j < perceptMap.length; j++) {
                if (x == j && y == i) {
                    System.out.print("\u001B[31m" + "A  " + "\u001B[0m");
                } else {
                    if ((perceptMap[j][i] & DEATH_WUMPUS) == DEATH_WUMPUS) {
                        System.out.print("W  ");
                    } else if ((perceptMap[j][i] & DEATH_PIT) == DEATH_PIT) {
                        System.out.print("P  ");
                    } else if ((perceptMap[j][i] & GLITTER) == GLITTER) {
                        System.out.print("G  ");
                    } else if ((perceptMap[j][i] & BUMP) == BUMP) {
                        System.out.print("B  ");
                    } else if (perceptMap[j][i] > 9) {
                        System.out.print(perceptMap[j][i] + " ");
                    } else {
                        System.out.print(perceptMap[j][i] + "  ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public byte action(int action) {
        byte percepts = action(action, true);
        printWorld();
        return percepts;
    }

    public byte action(int action, boolean thing) {
        System.out.println("Action: " + action);
        System.out.println("");
        numMoves++;
        switch (action) {
            case GRAB:
                score--;
                if ((perceptMap[x][y] & GLITTER) != 0) {
                    perceptMap[x][y] -= GLITTER;
                    score += 1000;
                    System.out.println("Gold found!\n");
                    printStats();
                    System.exit(0);
                } else {
                    System.out.println("Error: Gold not found");
                }
                break;
            case MOVE:
                score--;
                switch (direction) {
                    case NORTH:
                        System.out.println("Moved north");
                        if (y + 1 < size && (perceptMap[x][y + 1] & BUMP) == 0) {
                            if ((perceptMap[x][y + 1] & DEATH_WUMPUS) == DEATH_WUMPUS) {
                                score -= 1000;
                                wumpusDeaths++;
                                System.out.println("Death to wumpus: arrows remaining = " + arrowCount);
                                return DEATH_WUMPUS;
                            }
                            if ((perceptMap[x][y + 1] & DEATH_PIT) == DEATH_PIT) {
                                score -= 1000;
                                pitDeaths++;
                                System.out.println("Death to pit.");
                                return DEATH_PIT;
                            }
                            y = y + 1;
                            return perceptMap[x][y];
                        } else {
                            return BUMP;
                        }
                    case EAST:
                        System.out.println("Moved east");
                        if (x + 1 < size && (perceptMap[x + 1][y] & BUMP) == 0) {
                            if ((perceptMap[x + 1][y] & DEATH_WUMPUS) == DEATH_WUMPUS) {
                                score -= 1000;
                                wumpusDeaths++;
                                System.out.println("Death to wumpus: arrows remaining = " + arrowCount);
                                return DEATH_WUMPUS;
                            }
                            if ((perceptMap[x + 1][y] & DEATH_PIT) == DEATH_PIT) {
                                score -= 1000;
                                pitDeaths++;
                                return DEATH_PIT;
                            }
                            x = x + 1;
                            return perceptMap[x][y];
                        } else {
                            return BUMP;
                        }
                    case SOUTH:
                        System.out.println("Moved south");
                        if (y > 0 && (perceptMap[x][y - 1] & BUMP) == 0) {
                            if ((perceptMap[x][y - 1] & DEATH_WUMPUS) == DEATH_WUMPUS) {
                                score -= 1000;
                                wumpusDeaths++;
                                System.out.println("Death to wumpus: arrows remaining = " + arrowCount);
                                return DEATH_WUMPUS;
                            }
                            if ((perceptMap[x][y - 1] & DEATH_PIT) == DEATH_PIT) {
                                score -= 1000;
                                pitDeaths++;
                                System.out.println("Death to pit.");
                                return DEATH_PIT;
                            }
                            y -= 1;
                            return perceptMap[x][y];
                        } else {
                            return BUMP;
                        }
                    case WEST:
                        System.out.println("Moved west");
                        if (x > 0 && (perceptMap[x - 1][y] & BUMP) == 0) {
                            if ((perceptMap[x - 1][y] & DEATH_WUMPUS) == DEATH_WUMPUS) {
                                score -= 1000;
                                wumpusDeaths++;
                                System.out.println("Death to wumpus: arrows remaining = " + arrowCount);
                                return DEATH_WUMPUS;
                            }
                            if ((perceptMap[x - 1][y] & DEATH_PIT) == DEATH_PIT) {
                                score -= 1000;
                                pitDeaths++;
                                System.out.println("Death to pit.");
                                return DEATH_PIT;
                            }
                            x -= 1;
                            return perceptMap[x][y];
                        } else {
                            return BUMP;
                        }
                    default:
                        System.out.println("Defaulted");
                        System.out.println(direction);
                        break;
                }
                break;
            case TURN_LEFT:
                score--;
                direction = (direction + 3) % 4;
                return perceptMap[x][y];
            case TURN_RIGHT:
                score--;
                direction = (direction + 1) % 4;
                return perceptMap[x][y];
            case SHOOT:
                //shoot logic
                if (arrowCount == 0) {
                    System.out.println("Error: Out of arrows");
                    return -1;      //out of arrows, which shouldn't be possible
                }
                arrowCount--;
                score -= 10;
                switch (direction) {
                    case NORTH: //shoot north
                        for (int i = y + 1; i < perceptMap.length; i++) {
                            if ((perceptMap[x][i] & DEATH_WUMPUS) != 0) {       //hits Wumpus
                                removeWumpus(x, i);
                                return SCREAM;
                            } else if ((perceptMap[x][i] & BUMP) != 0) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case EAST: //shoot east
                        for (int i = x + 1; i < perceptMap.length; i++) {
                            if ((perceptMap[i][y] & DEATH_WUMPUS) != 0) {       //hits Wumpus
                                removeWumpus(i, y);
                                return SCREAM;
                            } else if ((perceptMap[i][y] & BUMP) != 0) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case SOUTH: //shoot south
                        for (int i = y - 1; i >= 0; i--) {
                            if ((perceptMap[x][i] & DEATH_WUMPUS) != 0) {       //hits Wumpus
                                removeWumpus(x, i);
                                return SCREAM;
                            } else if ((perceptMap[x][i] & BUMP) != 0) { //hits Obstacle
                                return perceptMap[x][y];
                            }
                        }
                        return perceptMap[x][y];

                    case WEST: //shoot west
                        for (int i = x - 1; i >= 0; i--) {
                            if ((perceptMap[i][y] & DEATH_WUMPUS) != 0) {       //hits Wumpus
                                removeWumpus(i, y);
                                return SCREAM;
                            } else if ((perceptMap[i][y] & BUMP) != 0) { //hits Obstacle
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
                break;
            default:
                System.out.println("Error with world movement, no case exists: " + action);
                return -1;
        }
        return -1;
    }

    private void removeWumpus(int x, int y) {

        System.out.println("Wumpus slain at " + x + ", " + y + "!!!");

        //remove death_by_wumpus percepts from x, y
        perceptMap[x][y] = (byte) (perceptMap[x][y] & ~DEATH_WUMPUS);

        //remove stench percepts form spaces adjacent to wumpus
        if (x > 0) {
            //remove stentch to left
            perceptMap[x - 1][y] = (byte) (perceptMap[x - 1][y] & ~STENCH);
        }

        if (x < size - 1) {
            //remove stentch to right
            perceptMap[x + 1][y] = (byte) (perceptMap[x + 1][y] & ~STENCH);
        }
        if (y > 0) {
            //remove stentch below
            perceptMap[x][y - 1] = (byte) (perceptMap[x][y - 1] & ~STENCH);
        }
        if (y < size - 1) {
            //remove stentch above
            perceptMap[x][y + 1] = (byte) (perceptMap[x][y + 1] & ~STENCH);
        }
        remakeStenches();
        killedWumpus++;
        score += 10;
    }
    
    public void remakeStenches(){
        for (int i = 0; i < perceptMap.length; i++) {
            for (int j = 0; j < perceptMap.length; j++) {
                if((perceptMap[i][j] & DEATH_WUMPUS) != 0){
                    if(i>0){
                        perceptMap[i-1][j] |= STENCH;
                    }
                    if(i < size-1)
                        perceptMap[i+1][j] |= STENCH;
                    if(j > 0)
                        perceptMap[i][j-1] |= STENCH;
                    if(j < size-1)
                        perceptMap[i][j+1] |= STENCH;
                }
            }
        }
    }
}
