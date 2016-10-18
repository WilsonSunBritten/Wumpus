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
public class PlusFunction implements IFunction{
    public int process(int value){
        return value+1;
    }
    public Variable processVariable(Variable variable){
        Variable processedVariable = new Variable();
        processedVariable.function = variable.function;
        processedVariable.isSkolemConstant = variable.isSkolemConstant;
        processedVariable.isVariable = variable.isVariable;
        processedVariable.modifier = variable.modifier + 1;//This is the important one
        processedVariable.value = variable.value;
        processedVariable.variableId = variable.variableId;
        return processedVariable;
    }
}
