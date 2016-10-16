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
        //Stentch(x,y)=>Wumpus(x-1,y)ORWumpus(x+1,y)ORWumpus(x,y-1)ORWumpus(x,y+1)
        //Breeze mimics this
        //Glitter(x,y)=>Gold(x,y)
        //Bump(x,y)=>Obsticle(x,y)
        //action(Move
        //(!Wumpus(x,y)&&!Pit(x,y))=>Safe(x,y)
        //
    }
    
    public boolean ask(String placeholder){
        return true;
    }
    
    public void tell(String placeholder){
    }
    
    public void untell(String placeholder){
        //we need this for when say, a Wumpus is killed.
    }
}
