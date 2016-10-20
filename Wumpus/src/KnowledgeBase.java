
import java.util.ArrayList;


public class KnowledgeBase {

    ArrayList<Clause> clauses;
    public void initializeRules() {
        //Special predicate: Evaluate
        
        //Stench(x,y)=>(Wumpus(x-1,y) v Wumpus(x+1,y) v Wumpus(x,y-1) v Wumpus(x,y+1)
        //!Stench(x,y) v Wumpus(x-1,y) v Wumpus(x+1,y) v Wumpus(x,y-1) v Wumpus(x,y+1)
        Fact fact = new Fact();
        fact.predicate = "Stench";
        fact.not = true;
        
        //Breeze(x,y)=>(Pit(x-1,y) v Pit(x-1,y) v Pit(x,y-1) v Pit(x, y+1)
        //!Breeze(x,y) v Pit(x-1,y) v Pit(x-1,y) v Pit(x,y-1) v Pit(x, y+1)
        
        //!Wumpus(-1,y) ^ !Wumpus(x,-1) ^ !Wumpus(x,World.size)^ !Wumpus(World.size,y)
        //!Wumpus(-1,y), !Wumpus(x,-1), !Wumpus(x,World.size), !Wumpus(World.size,y)
        
        //!Pit(-1,y), !Pit(x,-1), !Pit(x,World.size), !Wumpus(World.size,y)
        
        //Wumpus(x,y)<=>!Pit(x,y)
        //(!Wumpus(x,y) v !Pit(x,y)) ^ (Pit(x,y) v Wumpus(x,y))
        
        
    }

    public boolean ask(String question) {
        
        //if question follows from known facts ==> return true
        //else return false
        
        return true;
    }

    public void tell(Clause clause) {
        //If told DeadWumpus delete the wumpus entry at that position and add !Wumpus(x,y)
        //and check if there is a stench at any adjacent position, remove those facts too
    }
}
