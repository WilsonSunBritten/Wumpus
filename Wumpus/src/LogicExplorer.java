
import java.util.ArrayList;

public class LogicExplorer extends Agent {

    private final KnowledgeBase kb;
    private int previousAction;
    private ArrayList<Location> frontier = new ArrayList<>();
    private boolean[][] searchedPositions;
    private boolean navigatingToSafePosition;
    private Location safeSpace;
    private Location wumpusSpace;
    private ArrayList<Integer> moveHistory = new ArrayList<>();
    boolean notFirstMove = false;

    public LogicExplorer(World world, int startingArrows, int startingX, int startingY, int direction) {
        super(world, startingArrows, startingX, startingY, direction);
        kb = new KnowledgeBase();
        kb.initializeRules();
        this.searchedPositions = new boolean[World.size][World.size];
        searchedPositions[location.x][location.y] = true;
        percepts = world.getPercepts();
        processPercepts();
        //initializeFrontier();
        run();
    }

    public void initializeFrontier() {
        if (location.x > 0) {
            frontier.add(new Location(location.x - 1, location.y));
        }
        if (location.x < World.size - 1) {
            frontier.add(new Location(location.x + 1, location.y));
        }
        if (location.y > 0) {
            frontier.add(new Location(location.x, location.y - 1));
        }
        if (location.y < World.size - 1) {
            frontier.add(new Location(location.x, location.y + 1));
        }
    }

    @Override
    public void updateLocation() {
        if (notFirstMove) {
            super.updateLocation();
        } else {
            notFirstMove = true;
        }
        searchedPositions[location.x][location.y] = true;
        expandFrontier();
    }

    public void addToFrontier(Location loc) {
        if (!searchedPositions[loc.x][loc.y]) {
            if (inFrontier(loc)) {
                removeFromFrontier(loc);
            }
            frontier.add(loc);
        }
    }

    public void expandFrontier() {
        if (location.x > 0 && !searchedPositions[location.x - 1][location.y]) {
            addToFrontier(new Location(location.x - 1, location.y));
        }
        if (location.x < World.size - 1 && !searchedPositions[location.x + 1][location.y]) {
            addToFrontier(new Location(location.x + 1, location.y));
        }
        if (location.y > 0 && !searchedPositions[location.x][location.y - 1]) {
            addToFrontier(new Location(location.x, location.y - 1));
        }
        if (location.y < World.size - 1 && !searchedPositions[location.x][location.y + 1]) {
            addToFrontier(new Location(location.x, location.y + 1));
        }
    }

    private void run() {

        while (true) {
            if (!notFirstMove) {
                percepts = world.getPercepts();
                processPercepts();
            }
            decideNextAction();
        }
    }

    private void move(int action) {

        switch (action) {
            case GRAB:
                System.out.println("Explorer Action:Grabbing");
                world.action(GRAB);
                System.out.println("Game failed to end after action(GRAB).");
                break;
            case MOVE:
                moveHistory.add(MOVE);
                System.out.println("Explorer Action: Moving");
                percepts = (byte) world.action(MOVE);
                if (getForward().x >= 0 && getForward().x < World.size && getForward().y >= 0 && getForward().y < World.size) {
                    searchedPositions[getForward().x][getForward().y] = true;
                }
                processPercepts();
                break;
            case TURN_LEFT:
                moveHistory.add(TURN_LEFT);
                System.out.println("Explorer Action: Turning left");
                direction = (direction + 3) % 4;
                world.action(TURN_LEFT);
                break;
            case TURN_RIGHT:
                moveHistory.add(TURN_RIGHT);
                System.out.println("Explorer Action: turning right");
                direction = (direction + 1) % 4;
                world.action(TURN_RIGHT);
                break;
            case SHOOT:
                System.out.println("Explorer Action: shooting");
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
                return;
            }
        }
    }

    private void processPercepts() {        //there might still be an issue with wumpus death since its a seperate percept
        if ((percepts & DEATH_PIT) == DEATH_PIT || (percepts & DEATH_WUMPUS) != 0) {
            System.out.println("Explorer died after moving");
            Clause clause = new Clause(new Fact("Wumpus", getForward().x, false, getForward().y, false, false, null, null));
            clause.facts.add(new Fact("Pit", getForward().x, false, getForward().y, false, false, null, null));
            kb.tell(clause);
            if (kb.ask(new Fact("Wumpus", getForward().x, false, getForward().y, false, true, null, null))) {
                removeFromFrontier(getForward()); //we want to keep it in frontier so we can kill wumpus
            }
            moveHistory.remove(moveHistory.size() - 1);//why die again?
        }
        if ((((percepts & BUMP) != BUMP) && (percepts & DEATH_PIT) != DEATH_PIT) && ((percepts & DEATH_WUMPUS) != DEATH_WUMPUS) && ((percepts & SCREAM) == 0)) {
            updateLocation();
            kb.tell(new Fact("Wumpus", location.x, false, location.y, false, true, null, null));
            kb.tell(new Fact("Pit", location.x, false, location.y, false, true, null, null));
            removeFromFrontier(location);
        }
        if ((percepts & BUMP) == BUMP) {       //theres soemthing funky here, hes bumping when there arent obsticales
            System.out.println("Explorer bumped after moving");
            moveHistory.remove(moveHistory.size() - 1);//why bump again?
            removeFromFrontier(getForward());
            kb.tell(new Fact("Obsticle", getForward().x, false, getForward().y, false, false, null, null));
        } else if ((percepts & DEATH_PIT) == 0 && (percepts & DEATH_WUMPUS) == 0) {//you if you bump than the only inputted percept should've been bump
            if ((percepts & STENCH) != 0) {
                kb.tell(new Fact("Stench", location.x, false, location.y, false, false, null, null));
            } else {
                kb.tell(new Fact("Stench", location.x, false, location.y, false, true, null, null));
            }
            if ((percepts & BREEZE) != 0) {
                kb.tell(new Fact("Breeze", location.x, false, location.y, false, false, null, null));
            } else {
                kb.tell(new Fact("Breeze", location.x, false, location.y, false, true, null, null));
            }
            if ((percepts & SCREAM) != 0) {
                kb.tell(new Fact("DeadWumpus", getForward().x, false, getForward().y, false, false, null, null));
            }
        }
    }

    private boolean inFrontier(Location loc) {
        for (Location location : frontier) {
            if (location.x == loc.x && location.y == loc.y) {
                return true;
            }
        }
        return false;
    }

    private void decideNextAction() {
        if ((percepts & GLITTER) != 0) {
            move(GRAB);
        }

        if (frontier.isEmpty()) {
            move(World.QUIT);
        }
        if (getForward().x >= 0 && getForward().x < World.size && getForward().y >= 0 && getForward().y < World.size) {
            if (kb.ask(new Fact("Wumpus", getForward().x, false, getForward().y, false, true, null, null))) {
                if (kb.ask(new Fact("Pit", getForward().x, false, getForward().y, false, true, null, null))) {
                    if (inFrontier(getForward())) {
                        move(MOVE);
                        removeFromFrontier(getForward());
                        return;
                    }

                }
            }
        }
        if (safeSpaceInFrontier()) {
            if (!adjacent(safeSpace)) {
                goTo(safeSpace);
            }
            turnToSpace(safeSpace);
            move(MOVE);
            removeFromFrontier(safeSpace);
        } else if (arrowCount > 0 && wumpusInFrontier()) {
            if (!adjacent(wumpusSpace)) {
                goTo(wumpusSpace);
            }
            turnToSpace(wumpusSpace);
            move(SHOOT);
            move(MOVE);
            removeFromFrontier(wumpusSpace);
        } else if (!frontier.isEmpty()) {
            goTo(frontier.get(frontier.size() - 1));
            turnToSpace(frontier.get(frontier.size() - 1));
            //frontier.remove(frontier.size() - 1);
            move(MOVE);
            //if(!kb.ask(new Fact("Wumpus",getForward().x,false,getForward().y,false,false,null,null)))
//                frontier.remove(frontier.size() - 1);
            if (kb.ask(new Fact("Wumpus", getForward().x, false, getForward().y, false, false, null, null))) {
                addToFrontier(getForward());//since there is definitely a wumpus forward, put in frontier so we can kill it later maybe.
            }
        }
    }

    public void turnToSpace(Location loc) {
        if (loc.x < location.x) {
            switch (direction) {
                case WEST:
                    break;
                case NORTH:
                    move(TURN_LEFT);
                    break;
                case SOUTH:
                    move(TURN_RIGHT);
                    break;
                case EAST:
                    move(TURN_RIGHT);
                    move(TURN_RIGHT);
                    break;
                default:
                    break;
            }
        } else if (loc.x > location.x) {
            switch (direction) {
                case EAST:
                    return;
                case SOUTH:
                    move(TURN_LEFT);
                    break;
                case NORTH:
                    move(TURN_RIGHT);
                    break;
                case WEST:
                    move(TURN_RIGHT);
                    move(TURN_RIGHT);
                    break;
                default:
                    break;
            }
        } else if (loc.y < location.y) {
            switch (direction) {
                case SOUTH:
                    return;
                case WEST:
                    move(TURN_LEFT);
                    break;
                case EAST:
                    move(TURN_RIGHT);
                    break;
                case NORTH:
                    move(TURN_RIGHT);
                    move(TURN_RIGHT);
                    break;
                default:
                    break;
            }
        } else if (loc.y > location.y) {
            switch (direction) {
                case NORTH:
                    return;
                case WEST:
                    move(TURN_RIGHT);
                    break;
                case EAST:
                    move(TURN_LEFT);
                    break;
                case SOUTH:
                    move(TURN_RIGHT);
                    move(TURN_RIGHT);
                default:
                    break;
            }
        }
    }

//    private Location neighborSafeSpace(Location location) {
//        Location loc = null;
//        if (location.x > 0 && searchedPositions[location.x - 1][location.y]) {
//            loc = new Location(location.x - 1, location.y);
//
//        } else if (location.x < World.size - 1 && searchedPositions[location.x + 1][location.y]) {
//            loc = new Location(location.x + 1, location.y);
//        } else if (location.y > 0 && searchedPositions[location.x][location.y - 1]) {
//            loc = new Location(location.x, location.y - 1);
//        } else if (location.y < World.size - 1 && searchedPositions[location.x][location.y + 1]) {
//            loc = new Location(location.x, location.y + 1);
//        }
//        return loc;
//    }
    private boolean wumpusInFrontier() {
        for (Location loc : frontier) {
            if (kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, false, null, null))) {
                wumpusSpace = loc;
                return true;
            }
        }
        return false;
    }

//    private void moveHistoryTraversal(Location loc) {
//        if (adjacent(loc)) {
//            return;
//        }
//        for (int i = moveHistory.size() - 1; i >= 0; i--) {
//            if (adjacent(loc)) {
//                return;
//            }
//            int move = moveHistory.get(i);
//            switch (move) {
//                case MOVE:
//                    move(TURN_LEFT);
//                    move(TURN_LEFT);
//                    move(MOVE);
//                    move(TURN_LEFT);
//                    move(TURN_LEFT);
//                    break;
//                case TURN_LEFT:
//                    move(TURN_RIGHT);
//                    break;
//                case TURN_RIGHT:
//                    move(TURN_LEFT);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//    private void rhwTraversal(Location location) {
//   //      moveHistoryTraversal(location);
//        //if (!this.location.equals(location) && !adjacent(location)) {
//            goTo(location.x, location.y);
//        //}
//        //go to location zach NOOOO!
//    }
    private boolean safeSpaceInFrontier() {
        for (int i = frontier.size() - 1; i >= 0; i--) {
            Location loc = frontier.get(i);
            if (kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, true, null, null))) {
                if (kb.ask(new Fact("Pit", loc.x, false, loc.y, false, true, null, null))) {
                    //hopefully no obsticle is in frontier as it should be removed when found...
                    safeSpace = new Location(loc.x, loc.y);
                    return true;

                }
            }
            if (kb.ask(new Fact("Pit", loc.x, false, loc.y, false, false, null, null)) || kb.ask(new Fact("Obsticle", loc.x, false, loc.y, false, false, null, null))) {
                //there is specifically a wumpus, pit, or obsticle at this position, don't navigate to it.
                frontier.remove(i);
            } //else if (arrowCount == 0 && kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, false, null, null))) {
            //frontier.remove(i);
            //}  We want wumpus so we can kill it...
        }
        return false;
    }

    private void goTo(Location target) {

        if (!adjacent(target)) {

            System.out.println("Traversing to " + target.x + ", " + target.y);
            ArrayList<Location> path = new ArrayList<>();
            path = searchForPath(location.x, location.y, target.x, target.y, path);
            //path.add(new Location(target.x, y));
            printPath(path);
            traversePath(path);
            System.out.println("Done traversing to " + target.x + ", " + target.y);
            System.out.println("Actual location: " + this.location.x + ", " + this.location.y);
        }
    }

    private ArrayList<Location> searchForPath(int curX, int curY, int goalX, int goalY, ArrayList<Location> path) {

        boolean[][] traversed = new boolean[World.size][World.size];
        if (searchNext(curX, curY, goalX, goalY, path, traversed)) {
            return path;
        } else {
            System.out.println("Path finding error, no path found.");
            return path;
        }
    }

    private boolean searchNext(int curX, int curY, int goalX, int goalY, ArrayList<Location> path, boolean[][] traversed) {

        Location curLoc = new Location(curX, curY);
        if (!this.location.equals(curLoc)) {
            path.add(curLoc);
        }
        printPath(path);
        traversed[curX][curY] = true;
//        if (curX == goalX && curY == goalY) {
//            return true;
//        }
        if (checkAdjacent(curX, curY, goalX, goalY)) {
            return true;
        }
        if (isValid(curX, curY + 1) && !traversed[curX][curY + 1]) {    //north is valid
            return searchNext(curX, curY + 1, goalX, goalY, path, traversed);
        }
        if (isValid(curX + 1, curY) && !traversed[curX + 1][curY]) {    //east is valid
            return searchNext(curX + 1, curY, goalX, goalY, path, traversed);
        }
        if (isValid(curX, curY - 1) && !traversed[curX][curY - 1]) {    //south is valid
            return searchNext(curX, curY - 1, goalX, goalY, path, traversed);
        }
        if (isValid(curX - 1, curY) && !traversed[curX - 1][curY]) {    //west is valid
            return searchNext(curX - 1, curY, goalX, goalY, path, traversed);
        }
       // System.out.println("path length: " + path.size());
        if (!path.isEmpty()) {
            path.remove(path.size() - 1);
        }
        
        traversed[curX][curY] = false;
        return false;
    }

    private void printPath(ArrayList<Location> path) {
        if (!path.isEmpty()) {
            System.out.print("Path: ");
            for (Location l : path) {
                System.out.print("[" + l.x + ", " + l.y + "] ");
            }
            System.out.println();
        } else {
            System.out.println("Path: [ ]");
        }

    }

    private boolean isValid(int x, int y) {

        if (x >= 0 && y >= 0 && x < World.size && y < World.size) {
            boolean wumpus = !kb.ask(new Fact("Wumpus", x, false, y, false, true, null, null));
            boolean pit = !kb.ask(new Fact("Pit", x, false, y, false, true, null, null));
            boolean bump = kb.ask(new Fact("Obsticle", x, false, y, false, false, null, null));
           // boolean bump = kb.ask(new Fact("Obsticle", x, false, y, false, false, null, null))
            //System.out.println("Pit: " + pit + " Wumpus: " + wumpus + " Obsticle: " + bump);
            return searchedPositions[x][y] && !bump && !pit && !wumpus;
        } else {
            return false;
        }
    }

    private boolean checkAdjacent(int curX, int curY, int goalX, int goalY) {

        if (curX == goalX) {
            if (Math.abs(curY - goalY) == 1) {
                return true;
            }
        }
        else if (curY == goalY) {
            if (Math.abs(curX - goalX) == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean adjacent(Location location) {

        if (location.x == this.location.x) {
            if (Math.abs(location.y - this.location.y) == 1) {
                return true;
            }
        }
        else if (location.y == this.location.y) {
            if (Math.abs(location.x - this.location.x) == 1) {
                return true;
            }
        }
        return false;
    }

    private void traversePath(ArrayList<Location> path) {

        while (path.size() > 0) {
            Location next = path.remove(0);
            if (next.x > this.location.x) {
                //go east
                turnToSpace(next);
                move(MOVE);
            } else if (next.x < this.location.x) {
                //go west
                turnToSpace(next);
                move(MOVE);
            } else if (next.y > this.location.y) {
                //go north
                turnToSpace(next);
                move(MOVE);
            } else if (next.y < this.location.y) {
                //go south
                turnToSpace(next);
                move(MOVE);
            } else {
                System.out.println("Error traversing path.");
            }
        }
    }
}
