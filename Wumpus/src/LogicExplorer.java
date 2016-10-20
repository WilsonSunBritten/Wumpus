
import java.util.ArrayList;
import java.util.Random;

public class LogicExplorer extends Agent {

    private final World world;
    private final KnowledgeBase kb;
    private int arrowCount;
    private int previousAction;
    private ArrayList<Position> frontier = new ArrayList<>();
    private boolean[][] searchedPositions;
    private final int worldSize;
    private Random random = new Random();

    private final byte BREEZE = 0b00000001;
    private final byte STENCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH = 0b00010000;
    private final byte SCREAM = 0b01000000;

    public LogicExplorer(World world) {
        this.world = world;
        kb = new KnowledgeBase();
        kb.initializeRules();
        this.arrowCount = world.arrowCount;
        this.searchedPositions = new boolean[World.size][World.size];
        this.worldSize = World.size;
        run();
    }

    private void run() {

        while (true) {
            decideNextAction((byte) world.getPercepts());
        }
    }

    private void move(int action) {

        switch (action) {
            case 1:
                world.action(action);
            case 2:
                kb.tell(encodePercepts(world.action(action)));
                break;
            case 5:
                arrowCount--;
                kb.tell(encodePercepts(world.action(action)));
                break;
            default:
                world.action(action);
                break;
        }
    }

    private Clause encodePercepts(int percepts) {

        Clause clause = new Clause();

        //conversion logic goes here
        return clause;
    }

    private void decideNextAction(byte percepts) {

        if (frontier.isEmpty()) {
            move(World.QUIT);
        }
        if ((percepts & GLITTER) != 0) {
            move(World.GRAB);
        }
        
        processPosition(percepts);
        updateKB(percepts);
        
        //check if adjacent to any unexplored and safe spaces
        
        //check forward
        //check left
        //check right
        if (kb.ask("!Wumpus(forward)AND!Pit(forward)"))
        
        
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
            Position target = frontier.get(random.nextInt(frontier.size()));
            RHWTraversal("At(target)");
        }
    }

    private void processPosition(byte percepts) {
        if ((percepts & BUMP) == 0) {//did not bump
            if (previousAction == World.MOVE) {
                curPos.moveDidMove();
            }
        }
    }

    private void updateKB(byte percepts) {
        if ((percepts & STENCH) != 0) {
            Clause clause = new Clause();
            kb.tell(new Clause(new Fact("Stench", curPos.x, false, curPos.y,false,  true, null, null)));//Stench(x,y,t)
        } else {
            kb.tell(new Clause(new Fact("Stench", curPos.x, false, curPos.y,false, false, null, null)));//!Stench(x,y,t)
        }
        if ((percepts & BREEZE) != 0) {
            kb.tell(new Clause(new Fact("Breeze", curPos.x, false, curPos.y,false, true, null, null)));
        } else {
            kb.tell(new Clause(new Fact("Breeze", curPos.x, false, curPos.y,false, false, null, null)));
        }
        if ((percepts & SCREAM) != 0) {
            //need to deal with this
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
