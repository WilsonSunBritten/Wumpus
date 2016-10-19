/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class SkolemFunction implements IFunction {
    Variable var;

    public SkolemFunction(Variable variable) {
        var = variable;
    }

    public int process(int value) {
        return value;//idk yet
    }

    public Variable processVariable(Variable variable) {
        return var;

    }
}
