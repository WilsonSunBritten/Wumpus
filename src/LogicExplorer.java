public class LogicExplorer {

    private World world;
    private KnowledgeBase kb;
    int arrowCount;

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

    public void decideAction(byte percepts) {
        if ((percepts & GLITTER) != 0) {//maybe just kb.ask("Holding(Gold,Result(Grab,CurrentPosition))"): is better, no percept based logic within agent.
            world.action(1);    //grab gold and end game
        } else if (kb.ask("Safe(Result(Move,CurrentPosition))&&!Explored(Result(Move,CurrentPosition))")) {
            world.action(2);    //move forward
        } else if (kb.ask("Safe(Result(Move,Result(TurnLeft,CurrentPosition))&&!Explored(Result(Move,Result(TurnLeft,CurrentPosition))")) {
            world.action(3);     //turn left
            world.action(2);     //move forward
        } else if (kb.ask("Safe(Result(Move,Result(TurnRight,CurrentPosition))&&!Explored(Result(Move,Result(TurnRight,CurrentPosition))")) {
            world.action(4);    //turn right
            world.action(2);    //move forward
        } else if (kb.ask("EXIST(s), Safe(s) && !Explored(s)")) {
            RHWTraversal("I am adjacent to a space that is unexplored and safe");
            world.action(2);
        } else {
            if (arrowCount > 0) {
                if (kb.ask("EXIST(s), WUMPUS(s)")) {
                    RHWTraversal("Facing Wumpus");
                    world.action(5);    //shoot arrow
                    world.action(2);    //move forward
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
                world.action(4);        //turn right
            } else {
                if (kb.ask("forward is safe")) {
                    world.action(2);    //go forward
                } else {
                    world.action(3);    //turn left
                }
            }
            //if space to the right is safe --> turn right
            //if space to right is unsafe && forward is safe --> go forward
            //if space to right is unsafe && forward is unsafe --> turn left
        } while (!kb.ask(stopCondition));

        //face stop condition
        while (!kb.ask("am i facing the stop condition?")) {
            world.action(3);    //turn until facing stp condition
        }
    }

}
