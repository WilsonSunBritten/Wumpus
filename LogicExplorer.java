public class LogicExplorer {

    private World world;
    private KnowledgeBase kb;
    private int arrowCount;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
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
    }

    private void move(int action) {

        if (action == 2) {
            kb.tell(encodePercepts(world.action(action)));
        } else if (action == 5){
            arrowCount--;
            world.action(action);
        } else {
            world.action(action);
        }
    }

    private String encodePercepts(int percepts) {

        String sentence = "";

        //conversion logic goes here

        return sentence;
    }

    public void decideAction(byte percepts) {
        if ((percepts & GLITTER) != 0) {//maybe just kb.ask("Holding(Gold,Result(Grab,CurrentPosition))"): is better, no percept based logic within agent.
            move(1);    //grab gold and end game
        } else if (kb.ask("Safe(Result(Move,CurrentPosition))&&!Explored(Result(Move,CurrentPosition))")) {
            move(2);    //move forward
        } else if (kb.ask("Safe(Result(Move,Result(TurnLeft,CurrentPosition))&&!Explored(Result(Move,Result(TurnLeft,CurrentPosition))")) {
            move(3);    //turn left
            move(2);    //move forward
        } else if (kb.ask("Safe(Result(Move,Result(TurnRight,CurrentPosition))&&!Explored(Result(Move,Result(TurnRight,CurrentPosition))")) {
            move(4);    //turn right
            move(2);    //move forward
        } else if (kb.ask("EXIST(s), Safe(s) && !Explored(s)")) {
            RHWTraversal("I am adjacent to a space that is unexplored and safe");
            move(2);
        } else {
            if (arrowCount > 0) {
                if (kb.ask("EXIST(s), WUMPUS(s)")) {
                    RHWTraversal("Facing Wumpus");
                    move(5);    //shoot arrow
                    move(2);    //move forward
                } else {
                    //explore potentially unsafe space
                    //we should never reach this step since were only shooting at a Wumpus who's location we know?
                }
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
