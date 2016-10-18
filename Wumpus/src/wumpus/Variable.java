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
    
    public void printVariable(){
        if(isVariable){
            System.out.print((char)(variableId + 32));
        }
    }
}
