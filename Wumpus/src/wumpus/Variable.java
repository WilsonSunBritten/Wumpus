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
public class Variable {
    boolean isVariable;
    int value;
    IFunction function;
    int variableId;
    int modifier;
    boolean isSkolemConstant;//Skolem function would just be the function, a skolem constant will have this and it's varId making a unique identifier
    
    public void printVariable(){
        if(isVariable){
            System.out.print((char)(variableId + 97));
        }
    }
}
