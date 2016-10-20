
import java.util.ArrayList;

public class LogicExplorer extends Agent {

    private final KnowledgeBase kb;
    private int previousAction;
    private ArrayList<Location> frontier = new ArrayList<>();
    private boolean[][] searchedPositions;

    public LogicExplorer(World world) {
        super(world);
        kb = new KnowledgeBase();
        kb.initializeRules();
        this.searchedPositions = new boolean[World.size][World.size];
        run();
    }

    private void run() {

        while (true) {
            decideNextAction();
        }
    }

    private void move(int action) {

        switch (action) {
            case GRAB:
                world.action(GRAB);
                System.out.println("Game failed to end after action(GRAB).");
                break;
            case MOVE:
                percepts = (byte) world.action(MOVE);
                processPercepts();
                break;
            case TURN_LEFT:
                world.action(TURN_LEFT);
                break;
            case TURN_RIGHT:
                world.action(TURN_RIGHT);
                break;
            case SHOOT:
                arrowCount--;
                percepts = (byte) world.action(SHOOT);
                processPercepts();
                break;
            case QUIT:
                world.action(QUIT);
                System.out.println("Game failed to end after action(QUIT).");
                break;
            default:
                System.out.println("Error processing movement.");
        }
    }

    private void processPercepts() {

        if (((percepts & BUMP) != BUMP) && (percepts & DEATH) != DEATH) {
            updateLocation();
        }
        if ((percepts & STENCH) != 0) {
            kb.tell(new Clause(new Fact("Stench", location.x, false, location.y, false, true, null, null)));
        } else {
            kb.tell(new Clause(new Fact("Stench", location.x, false, location.y, false, false, null, null)));
        }
        if ((percepts & BREEZE) != 0) {
            kb.tell(new Clause(new Fact("Breeze", location.x, false, location.y, false, true, null, null)));
        } else {
            kb.tell(new Clause(new Fact("Breeze", location.x, false, location.y, false, false, null, null)));
        }
        if ((percepts & SCREAM) != 0) {
            kb.tell(new Clause(new Fact("Screem", location.x, false, location.y, false, true, null, null)));
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
        if (kb.ask("!Wumpus(forward)AND!Pit(forward)")) {
            if (kb.ask("Is ")) {

            } else if (kb.ask("!Wumpus(forwardspot)AND!Pit(forwardSpot)&!Obstical(forwardSpot)")) {
                move(World.MOVE);
            } else if ("safeSpotInFrontier?" == "") {
                RHWTraversal("!Wumpus(adjacent)AND!Pit(adjacent)");
            } else if ("KnownWumpusSpotInFrontier" == "") {
                //kill wumpus
                RHWTraversal("Wumpus(adjacent)");
                move(World.SHOOT);
            } else {
                //go to random spot in frontier that is not definite death
                Location target = frontier.get(random.nextInt(frontier.size()));
                RHWTraversal("At(target)");
            }
        }
    }

    private void RHWTraversal(String stopCondition) {

        do {
            if (kb.ask("right is safe")) {
                move(4);        //turn right
            } else {
                if (kb.ask("forward is safe")) {
                    move(2);   //go forward
                } else {
                    move(3);    //turn left
                }
            }
        } while (!kb.ask(stopCondition));

        //face stop condition
        while (!kb.ask("am i facing the stop condition?")) {
            move(3);   //turn until facing stp condition
        }
        move(2);
    }
}
