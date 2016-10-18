
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
            //action(Grab);
        } else if (kb.ask("Safe(Result(Move,CurrentPosition))&&!Explored(Result(Move,CurrentPosition))")) {
            //action(Move);
        } else if (kb.ask("Safe(Result(Move,Result(TurnLeft,CurrentPosition))&&!Explored(Result(Move,Result(TurnLeft,CurrentPosition))")) {
            //action(TurnLeft)
        } else if (kb.ask("Safe(Result(Move,Result(TurnRight,CurrentPosition))&&!Explored(Result(Move,Result(TurnRight,CurrentPosition))")) {
            //action(TurnLeft)
        } else if (kb.ask("EXIST(s), Safe(s) && !Explored(s)")) {
            //follow right hand wall
        } else {
            if (arrowCount > 0) {
                if (kb.ask("EXIST(s), WUMPUS(s)")) {
                    //Find and kill wumpus via RHW
                } else {
                    //explore potentially safe space    
                }
            }
        }
    }

}
