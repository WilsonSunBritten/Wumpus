
public class MinusFunction implements IFunction {
    public int process(int value) {
        return value - 1;
    }

    public Variable processVariable(Variable variable) {
        Variable processedVariable = new Variable();
        processedVariable.function = variable.function;
        processedVariable.isSkolemConstant = variable.isSkolemConstant;
        processedVariable.isVariable = variable.isVariable;
        processedVariable.modifier = variable.modifier - 1;//This is the important one
        processedVariable.value = variable.value;
        processedVariable.variableId = variable.variableId;
        return processedVariable;
    }
}
