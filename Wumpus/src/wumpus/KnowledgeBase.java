/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wumpus;

/**
 *
 * @author Wilson
 */
public class KnowledgeBase {
    
    public void initializeRules(){
        //Special predicate: Evaluate
        
        //Percepts come in as Glitter(t) Stench(t) Breeze(t) Bump(t)
        //Vx,y,a,b Adjacent(x,y,a,b) iff [a,b] in {(x-1,y),(x+1,y),(x,y-1),(x,y+1)}
        //Vd,t Facing(d,t) => Facing(d,t+1) OR Action(Turnleft,t) OR Action(TurnRight,t)
        //Vt,v Facing(d,t) AND Action(Turnleft,t)=>Facing((d-1)%4, t+1)
        //Vt,v Facing(d,t) AND Action(TurnRight,t)=>Facing((d+1)%4,t+1)
        //Vt,x Arrows(x,t) => Arrows(x,t+1) OR Action(SHOOT,t)
        //Vt,x HasArrow(t) iff Arrows(x,t) && x>0
        //Vt,x Action(SHOOT,t) AND Arrows(x,t)AND HasArrow(t) => Arrows(x-1,t+1)
        //Vt At(Agent,x,y,t) && Facing(NORTH,t)&&Action(SHOOT,t)=>(Exists(a) st Scream(t+1) AND WumpusDead(x+a,y
        //Vt,x Action(x,t) => !(Action(y,t) AND True(EVALUATE(x,y,NOTEQUALS))
        //True(1)
        //!True(0)
        
        
        //percept rules:
        //At(Agent, x, y, t) && Stench(x,y,t) => At(Stink,x,y,t) 
        //Stink(x,y,t) iff Exists(a,b) st Wumpus(a,b,t) ^ Adjacent(x,y,a,b)
        
        //Wumpus(x,y,t) => Wumpus(x,y,t+1) XOR WumpusDead(x,y,t+1)    p XOR q is (pVq)&&(!(p&&q))
        //WumpusDead(x,y,t)=>WumpusDead(x,y,t+1)
        

        //Stench(x,y)=>Wumpus(x-1,y)ORWumpus(x+1,y)ORWumpus(x,y-1)ORWumpus(x,y+1)
        
        
        initializeStenchRule();
        
        
        //Breeze mimics this
        //Glitter(x,y,t)=>Gold(x,y,t)
        //Bump(x,y)=>Obsticle(x,y)
        //action(Move
        //(!Wumpus(x,y)&&!Pit(x,y))=>Safe(x,y)
        //!Wumpus(-1,y)&&!Wumpus(x,-1)&&!Pit(-1,z)&&!Pit(-1,a)//no boarder things
        //!Wumpus(x,size)&&!Wumpus(size,y)&&!PIT(z,size)&&!Pit(size,a)
    }
    
    public boolean ask(String placeholder){
        return true;
    }
    
    public void tell(String placeholder){
    }
    
    
    private void initializeStenchRule(){
        //Stench(x,y)=>Wumpus(x-1,y)OR(Wumpus(x+1,y)OR(Wumpus(x,y-1) OR Wumpus(x,y+1)))
        Quantifier x = new Quantifier();
       
        //ClauseFormConvertion from here...
        
    }
    
   
}
