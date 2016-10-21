
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class LogicExplorer extends Agent {

    private final KnowledgeBase kb;
    private int previousAction;
    private ArrayList<Location> frontier = new ArrayList<>();
    private boolean[][] searchedPositions;
    private boolean navigatingToSafePosition;
    private Location safeSpace;
    private Location wumpusSpace;
    private ArrayList<Integer> moveHistory = new ArrayList<>();
    boolean notFirstMove =  false;

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
        if (location.x < world.size-1) {
            frontier.add(new Location(location.x + 1, location.y));
        }
        if (location.y > 0) {
            frontier.add(new Location(location.x, location.y - 1));
        }
        if (location.y < world.size-1) {
            frontier.add(new Location(location.x, location.y + 1));
        }
    }

    public void updateLocation(){
        if(notFirstMove)
            super.updateLocation();
        else
            notFirstMove = true;
        expandFrontier();
    }

    public void expandFrontier() {
        if (location.x > 0 && !searchedPositions[location.x-1][location.y]) {
            frontier.add(new Location(location.x - 1, location.y));
        }
        if (location.x < world.size -1&& !searchedPositions[location.x+1][location.y]) {
            frontier.add(new Location(location.x + 1, location.y));
        }
        if (location.y > 0 && !searchedPositions[location.x][location.y-1]) {
            frontier.add(new Location(location.x, location.y - 1));
        }
        if (location.y < world.size -1&& !searchedPositions[location.x][location.y+1]) {
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
                moveHistory.add(MOVE);
                System.out.println("Moving");
                percepts = (byte) world.action(MOVE);
                processPercepts();
                break;
            case TURN_LEFT:
                moveHistory.add(TURN_LEFT);
                System.out.println("Turning left");
                direction = (direction + 3)%4;
                world.action(TURN_LEFT);
                break;
            case TURN_RIGHT:
                moveHistory.add(TURN_RIGHT);
                System.out.println("turning right");
                direction = (direction+1)%4;
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
        if((percepts&BUMP)!=0)
            kb.tell(new Fact("Obsticle",getForward().x,false,getForward().y,false,false,null,null));
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
                        return;
                    }
                }
            }
        }
        if (safeSpaceInFrontier()) {
            rhwTraversal(neighborSafeSpace(safeSpace));
            turnToSpace(safeSpace);
            move(MOVE);
        } 
        else if(arrowCount > 0 && wumpusInFrontier()){
            rhwTraversal(neighborSafeSpace(wumpusSpace));
            turnToSpace(wumpusSpace);
            move(SHOOT);
            move(MOVE);
        }
        else {
            rhwTraversal(neighborSafeSpace(frontier.get(0)));
            turnToSpace(frontier.get(0));
            move(MOVE);
        }
    }
    
    public void turnToSpace(Location loc){
        if(location.x > loc.x){
            if(direction == EAST)
                return;
            else if(direction == NORTH)
                move(TURN_LEFT);
            else if(direction == SOUTH)
                move(TURN_RIGHT);
        }
        else if(loc.x > location.x){
            if(direction == WEST)
                return;
            else if(direction == NORTH)
                move(TURN_RIGHT);
            else if(direction == SOUTH)
                move(TURN_LEFT);
        }
        else if(loc.y < location.y){
            if(direction == SOUTH)
                return;
            else if(direction == WEST)
                move(TURN_LEFT);
            else if(direction == EAST)
                move(TURN_RIGHT);
        }
        else if(loc.y > location.y){
            if(direction == NORTH){
                return;
            }
            else if(direction == WEST){
                move(TURN_RIGHT);
            }
            else if(direction == EAST)
                move(TURN_LEFT);
        }
    }
    
    private Location neighborSafeSpace(Location location){
        Location loc = null;
        if(location.x > 0 && searchedPositions[location.x-1][location.y]){
                loc = new Location(location.x-1,location.y);
            
        }
        else if(location.x < World.size -1 && searchedPositions[location.x+1][location.y])
            loc = new Location(location.x+1,location.y);
        else if(location.y > 0 && searchedPositions[location.x][location.y-1])
            loc = new Location(location.x,location.y-1);
        else if(location.y < World.size -1 && searchedPositions[location.x][location.y+1])
            loc = new Location(location.x,location.y+1);
        
        return loc;
    }
    
    private boolean wumpusInFrontier(){
        for(Location loc : frontier){
            if(kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, false, null, null))){
                wumpusSpace = loc;
                return true;
            }
        }
        
        return false;
    }

    private void moveHistoryTraversal(Location loc){
        if(loc.x == location.x && loc.y == location.y){
            return;
        }
        for(int i = moveHistory.size()-1;i>=0; i--){
            if(loc.x == location.x && loc.y == location.y){
                return;
            }
            int move = moveHistory.get(i);
            if(move == MOVE){
                move(TURN_LEFT);
                move(TURN_LEFT);
                move(MOVE);
            }
            else if(move == TURN_LEFT)
                move(TURN_RIGHT);
            else if(move == TURN_RIGHT)
                move(TURN_LEFT);
        }
    }
    private void rhwTraversal(Location location) {
        moveHistoryTraversal(location);
        //go to location zach NOOOO!
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
            if ( kb.ask(new Fact("Pit", loc.x, false, loc.y, false, false, null, null)) || kb.ask(new Fact("Obsticle", loc.x, false, loc.y, false, false, null, null))) {
                //there is specifically a wumpus, pit, or obsticle at this position, don't navigate to it.
                frontier.remove(i);
            }
            else if(arrowCount == 0 && kb.ask(new Fact("Wumpus", loc.x, false, loc.y, false, false, null, null))){
                frontier.remove(i);
            }
        }
        return false;
    }

    private void RHWTraversal(int x, int y) {

        //ArrayList<Location> path = PathFinder.getPath(x, y, location.x, location.y, searchedPositions);
        //traverse path
    }
}
