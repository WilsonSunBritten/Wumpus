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
public class IdentityFunction implements IFunction{
    public int process(int value){
        return value;
    }
    
    public void processVariable(Variable variable){
    }
}
