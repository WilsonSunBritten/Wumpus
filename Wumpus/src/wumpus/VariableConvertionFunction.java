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
public class VariableConvertionFunction implements IFunction{
    int convertedVariableId;
    
    public VariableConvertionFunction(int returnVariableValue){
        convertedVariableId = returnVariableValue;
    }
    public int process(int value){
        return -1;
    }
    public void processVariable(Variable variable){
        Variable variable2 = new Variable();
        variable2.variableId = convertedVariableId;
        variable2.modifier = variable.modifier;
        variable = variable2;
    }
}
