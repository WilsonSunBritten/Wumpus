
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LogicExplorer extends Agent {

    private final KnowledgeBase kb;
    private int previousAction;
    private ArrayList<Location> frontier = new ArrayList<>();
    private boolean[][] searchedPositions;
    private boolean navigatingToSafePosition;
    private Location safeSpace;
    boolean notFirstMove = false;

    public LogicExplorer(World world, int startingArrows, int startingX, int startingY, int direction) {
        super(world, startingArrows, startingX, startingY, direction);
        kb = new KnowledgeBase();
        kb.initializeRules();
        this.searchedPositions = new boolean[World.size][World.size];
        searchedPositions[location.x][location.y] = true;
        initializeFrontier();
        run();
    }

    public void initializeFrontier() {
        if (location.x > 0) {
            frontier.add(new Location(location.x - 1, location.y));
        }
        if (location.x < world.size - 1) {
            frontier.add(new Location(location.x + 1, location.y));
        }
        if (location.y > 0) {
            frontier.add(new Location(location.x, location.y - 1));
        }
        if (location.y < world.size - 1) {
            frontier.add(new Location(location.x, location.y + 1));
        }
    }

    public void updateLocation() {
        if (notFirstMove) {
            super.updateLocation();
        } else {
            notFirstMove = true;
        }
        expandFrontier();
    }

    public void expandFrontier() {
        if (location.x > 0 && !searchedPositions[location.x - 1][location.y]) {
            frontier.add(new Location(location.x - 1, location.y));
        }
        if (location.x < world.size - 1 && !searchedPositions[location.x + 1][location.y]) {
            frontier.add(new Location(location.x + 1, location.y));
        }
        if (location.y > 0 && !searchedPositions[location.x][location.y - 1]) {
            frontier.add(new Location(location.x, location.y - 1));
        }
        if (location.y < world.size - 1 && !searchedPositions[location.x][location.y + 1]) {
            frontier.add(new Location(location.x, location.y + 1));
        }
    }

    private void run() {

        while (true) {
            percepts = world.getPercepts();
            processPercepts();
            decideNextAction();
        }
    }

    private void move(int action) {

        switch (action) {
            case GRAB:
                System.out.println("Grabbing");
                world.action(GRAB);
                System.out.println("Game failed to end after action(GRAB).");
                break;
            case MOVE:
                System.out.println("Moving");
                percepts = (byte) world.action(MOVE);
                processPercepts();
                break;
            case TURN_LEFT:
                System.out.println("Turning left");
                world.action(TURN_LEFT);
                break;
            case TURN_RIGHT:
                System.out.println("turning right");
                world.action(TURN_RIGHT);
                break;
            case SHOOT:
                System.out.println("shooting");
                arrowCount--;
                percepts = (byte) world.action(SHOOT);
                processPercepts();
                break;
            case QUIT:
                System.out.println("no possible solution");
                world.action(QUIT);
                System.out.println("Game failed to end after action(QUIT).");
                break;
            default:
                System.out.println("Error processing movement.");
        }
    }

    public void removeFromFrontier(Location locToRemove) {
        for (Location loc : frontier) {
            if (loc.x == locToRemove.x && loc.y == locToRemove.y) {
                frontier.remove(loc);
            }
        }
    }

    private void processPercepts() {
        if ((percepts & DEATH) != 0) {
            removeFromFrontier(getForward());
        }
        if (((percepts & BUMP) != BUMP) && (percepts & DEATH) != DEATH) {
            updateLocation();
            searchedPositions[location.x][location.y] = true;
        }
        if ((percepts & BUMP) != 0) {
            kb.tell(new Fact("Obsticle", getForward().x, false, getForward().y, false, false, null, null));
        }
        if ((percepts & STENCH) != 0) {
            kb.tell(new Fact("Stench", location.x, false, location.y, false, true, null, null));
        } else {
            kb.tell(new Fact("Stench", location.x, false, location.y, false, false, null, null));
        }
        if ((percepts & BREEZE) != 0) {
            kb.tell(new Fact("Breeze", location.x, false, location.y, false, true, null, null));
        } else {
            kb.tell(new Fact("Breeze", location.x, false, location.y, false, false, null, null));
        }
        if ((percepts & SCREAM) != 0) {
            kb.tell(new Fact("Scream", location.x, false, location.y, false, true, null, null));
        }
    }

    private void decideNextAction() {
        if (frontier.isEmpty()) {
            move(World.QUIT);
        }
        if ((percepts & GLITTER) != 0) {
            move(World.GRAB);
        }

        //check if adjacent to any unexplored and safe spaces
        //check forward
        //check left
        //check right
        if (getForward().x >= 0 && getForward().x < world.size && getForward().y >= 0 && getForward().y < world.size) {
            if (kb.ask(new Fact("Wumpus", getForward().x, false, getForward().y, false, true, null, null))) {
                if (kb.ask(new Fact("Pit", getForward().x, false, getForward().y, false, true, null, null))) {
                    if (kb.ask(new Fact("Obsticle", getForward().x, false, getForward().y, false, true, null, null))) {
                        move(World.MOVE);
                    }
                }
            }
        } else if (safeSpaceInFrontier()) {
            rhwTraversal(safeSpace);
        } else {
            rhwTraversal(frontier.get(0));
        }
    }

    private void rhwTraversal(Location location) {
        for (int i = 0; i < World.size; i++) {
            for (int j = 0; j < World.size; j++) {
                if (searchedPositions[i][j]) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = 1;
                }
            }
        }
        grid[location.x][location.y] = 2;
        grid[this.location.x][this.location.y] = 3;
    }

    private ArrayList<Location> getPath(Location goal, Location current, ArrayList<Location> path) {

        solve(goal, current, path);
        return path;
    }

    private boolean solve(Location goal, Location current, ArrayList<Location> path) {

        boolean done = false;
        if (valid(current.x, current.y)) {
            //grid[current.x][current.y] = 3;
            path.add(new Location(current.x, current.y));
            if (current.x == goal.x && current.y == goal.y) {
                return true;
            } else {
                solve(goal, new Location(current.x + 1, current.y), path);
                done = solve(goal, new Location(current.x + 1, current.y), path);
                if (!done) {
                    done = solve(goal, new Location(current.x, current.y + 1), path);
                }
                if (!done) {
                    done = solve(goal, new Location(current.x - 1, current.y), path);
                }
                if (!done) {
                    done = solve(goal, new Location(current.x, current.y - 1), path);
                }
            }
            if (done) {
                //  grid[current][column] = 7;
            }

        } else {
            return false;
        }
        return false;
    }

    private boolean valid(int row, int column) {
        boolean result = false;
        // check if cell is in the bounds of the matrix
        if (row >= 0 && row < grid.length && column >= 0 && column < grid[0].length) {
            if (grid[row][column] == 0) {
                result = true;
            }
        }
        return result;
    }

    //searchedpositions
    int[][] grid = new int[World.size][World.size];

    private void search(Location location) {
        for (int i = 0; i < World.size; i++) {
            for (int j = 0; j < World.size; j++) {
                if (searchedPositions[i][j]) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = 1;
                }
            }
        }
        grid[location.x][location.y] = 2;
        grid[this.location.x][this.location.y] = 3;
        search(this.location.x, this.location.y);
    }

    private boolean search(int x, int y) {
        //0 = safe, 1 = unsafe, 2 = goal, 3 = visited
        if (grid[x][y] == 2) {
            return true;
        } else if (grid[x][y] == 1) {
            return false;
        } else if (grid[x][y] == 3) {
            return false;
        }
        grid[x][y] = 3;
        //explore neighbors clockwise
        if ((x < grid.length - 1 && search(x + 1, y)) || (y > 0 && search(x, y - 1)) || (x > 0 && search(x - 1, y)) || (y < grid.length - 1 && search(x, y + 1))) {
            return true;
        }
        return false;
    }

    private ArrayList<Location> getSafeAdjacent(Location location) {

        ArrayList<Location> adjacent = new ArrayList<>();

        if (location.x < World.size - 1) {
            if (searchedPositions[location.x + 1][location.y]) {
                adjacent.add(new Location(location.x + 1, location.y));
            }
        }
        if (location.x > 0) {
            if (searchedPositions[location.x - 1][location.y]) {
                adjacent.add(new Location(location.x - 1, location.y));
            }
        }
        if (location.y < World.size - 1) {
            if (searchedPositions[location.x][location.y + 1]) {
                adjacent.add(new Location(location.x, location.y + 1));
            }
        }
        if (location.y > 0) {
            if (searchedPositions[location.x][location.y - 1]) {
                adjacent.add(new Location(location.x, location.y - 1));
            }
        }
        return adjacent;
    }

    private boolean safeSpaceInFrontier() {
        for (int i = frontier.size() - 1; i >= 0; i--) {
            Location loc = frontier.get(i);
            if (kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, true, null, null))) {
                if (kb.ask(new Fact("Pit", loc.x, false, loc.y, false, true, null, null))) {
                    if (kb.ask(new Fact("Obsticle", loc.x, false, loc.y, false, true, null, null))) {
                        safeSpace = new Location(loc.x, loc.y);
                        return true;
                    }
                }
            }
            if (kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, false, null, null)) || kb.ask(new Fact("Pit", loc.x, false, loc.y, false, false, null, null)) || kb.ask(new Fact("Obsticle", loc.x, false, loc.y, false, false, null, null))) {
                //there is specifically a wumpus, pit, or obsticle at this position, don't navigate to it.
                frontier.remove(i);
            }
        }
        return false;
    }

}
