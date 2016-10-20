
import java.util.ArrayList;


public class KnowledgeBase {

    ArrayList<Clause> clauses;
    public void initializeRules() {
        //Fact(String predicate, int var1Val, boolean var1Var, int var2Val, boolean var2Var, boolean not, IFunction var1Function, IFunction var2Function)
        //Special predicate: Evaluate
        
        //Stench(x,y)=>(Wumpus(x-1,y) v Wumpus(x+1,y) v Wumpus(x,y-1) v Wumpus(x,y+1)
        //!Stench(x,y) v Wumpus(x-1,y) v Wumpus(x+1,y) v Wumpus(x,y-1) v Wumpus(x,y+1)
        Clause stench = new Clause();
        Fact stenchXY = new Fact("Stench",0,true,1,true,true,null,null);
        Fact wumpusxminy = new Fact("Wumpus",0,true,1,true,false,new MinusFunction(),null);
        Fact wumpusxplusy = new Fact("Wumpus",0,true,1,true,false,new PlusFunction(),null);
        Fact wumpusxymin = new Fact("Wumpus",0,true,1,true,false,null,new MinusFunction());
        Fact wumpusxyplus = new Fact("Wumpus",0,true,1,true,false,null,new PlusFunction());
        stench.facts.add(stenchXY);
        stench.facts.add(wumpusxminy);
        stench.facts.add(wumpusxplusy);
        stench.facts.add(wumpusxymin);
        stench.facts.add(wumpusxyplus);
        clauses.add(stench);
        
        
        //Breeze(x,y)=>(Pit(x-1,y) v Pit(x-1,y) v Pit(x,y-1) v Pit(x, y+1)
        //!Breeze(x,y) v Pit(x-1,y) v Pit(x-1,y) v Pit(x,y-1) v Pit(x, y+1)
        Clause breeze = new Clause();
        Fact breezehXY = new Fact("Breeze",0,true,1,true,true,null,null);
        Fact pitxminy = new Fact("Pit",0,true,1,true,false,new MinusFunction(),null);
        Fact pitxplusy = new Fact("Pit",0,true,1,true,false,new PlusFunction(),null);
        Fact pitxymin = new Fact("Pit",0,true,1,true,false,null,new MinusFunction());
        Fact pitxyplus = new Fact("Pit",0,true,1,true,false,null,new PlusFunction());
        breeze.facts.add(stenchXY);
        breeze.facts.add(wumpusxminy);
        breeze.facts.add(wumpusxplusy);
        breeze.facts.add(wumpusxymin);
        breeze.facts.add(wumpusxyplus);
        clauses.add(breeze);
        
        //!Wumpus(-1,y) ^ !Wumpus(x,-1) ^ !Wumpus(x,World.size)^ !Wumpus(World.size,y)
        //!Wumpus(-1,y), !Wumpus(x,-1), !Wumpus(x,World.size), !Wumpus(World.size,y)
        clauses.add(new Clause(new Fact("Wumpus",-1,false,1,true,true,null,null)));
        clauses.add(new Clause(new Fact("Wumpus",0,true,-1,false,true,null,null)));
        clauses.add(new Clause(new Fact("Wumpus",0,true,World.size,false,true,null,null)));
        clauses.add(new Clause(new Fact("Wumpus",World.size,false,1,true,true,null,null)));
        //!Pit(-1,y), !Pit(x,-1), !Pit(x,World.size), !Wumpus(World.size,y)
        clauses.add(new Clause(new Fact("Pit",-1,false,1,true,true,null,null)));
        clauses.add(new Clause(new Fact("Pit",0,true,-1,false,true,null,null)));
        clauses.add(new Clause(new Fact("Pit",0,true,World.size,false,true,null,null)));
        clauses.add(new Clause(new Fact("Pit",World.size,false,1,true,true,null,null)));
        
        //Wumpus(x,y)=>!Pit(x,y)
        //!Wumpus(x,y) v !Pit(x,y)
        Clause notWumpusOrNotPit = new Clause();
        Fact notWumpus = new Fact("Wumpus",0,true,1,true,true,null,null);
        Fact notPit = new Fact("Pit",0,true,1,true,true,null,null);
        notWumpusOrNotPit.facts.add(notWumpus);
        notWumpusOrNotPit.facts.add(notPit);
        clauses.add(notWumpusOrNotPit);
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
