
import java.util.ArrayList;


public class LogicExplorer extends Agent{

    private final World world;
    private final KnowledgeBase kb;
    private int arrowCount;
    private int t = 0;
    private Position currentPos;
    private int currentDirection;
    private ArrayList<Position> frontier = new ArrayList<>();
    boolean[][] searchedPositions;
    boolean currentlyNavigatingToSafeSquare;
    int worldSize;
    private Position goalPosition;
    

    private final byte BREEZE = 0b00000001;
    private final byte STENCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    public LogicExplorer(World world) {
        this.world = world;
        kb = new KnowledgeBase();
        kb.initializeRules();
        this.arrowCount = world.arrowCount;
        this.searchedPositions = new boolean[world.size][world.size];
        this.worldSize = world.size;
    }

    private void move(int action) {

        switch (action) {
            case 2:
                //kb.tell(encodePercepts(world.action(action)));
                break;
            case 5:
                arrowCount--;
                world.action(action);
                break;
            default:
                world.action(action);
                break;
        }
    }

    private String encodePercepts(int percepts) {

        String sentence = "";

        //conversion logic goes here
        return sentence;
    }

    public int decideAction(byte percepts) {
        processPosition(percepts);
        updateKB(percepts);
        if ((percepts & GLITTER) != 0) {//maybe just kb.ask("Holding(Gold,Result(Grab,CurrentPosition))"): is better, no percept based logic within agent.
            return World.GRAB;    //grab gold and end game
        } 
        else if(currentlyNavigatingToSafeSquare){
            return continueNavigatingToSafeSquare();
        } 
        else if(kb.ask("!Wumpus(forwardspot)AND!Pit(forwardSpot)&!Obstical(forwardSpot)")){
            
        }
        else if("safeSpotInFrontier?" == ""){
            return continueNavigatingToSafeSquare();
        }
        else if("KnownWumpusSpotInFrontier" == ""){
            //kill wumpus
        }
        else{
            //go to random spot in frontier that is not definite death
        }
        
        return -1;
    }
    
    private int continueNavigatingToSafeSquare(){
        //this should basically be RHW Traversal until adjacent to to goal state, then turn to face it
        return -1;
    }
    
    private void processPosition(byte percepts){
        
    }
    
    private void updateKB(byte percepts){
        if((percepts & STENCH) != 0){
            Clause clause = new Clause();
            kb.tell(new Clause(new Fact("Stench", currentPos.x,currentPos.y,t,true)));//Stench(x,y,t)
        }
        else{
            kb.tell(new Clause(new Fact("Stench", currentPos.x, currentPos.y,t,false)));//!Stench(x,y,t)
        }
        if((percepts & BREEZE) != 0){
            kb.tell(new Clause(new Fact("Breeze", currentPos.x,currentPos.y,t,true)));
        }
        else{
            kb.tell(new Clause(new Fact("Breeze", currentPos.x, currentPos.y,t,false)));
        }
        if((percepts & SCREAM) != 0){
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
            //if space to the right is safe --> turn right
            //if space to right is unsafe && forward is safe --> go forward
            //if space to right is unsafe && forward is unsafe --> turn left
        } while (!kb.ask(stopCondition));

        //face stop condition
        while (!kb.ask("am i facing the stop condition?")) {
            move(3);   //turn until facing stp condition
        }
        move(2);
    }
}
