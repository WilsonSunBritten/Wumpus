
public class MinusFunction implements IFunction {

    @Override
    public int process(int value) {
        return value - 1;
    }

    @Override
    public Variable processVariable(Variable variable) {
        Variable processedVariable = new Variable();
        processedVariable.function = variable.function;
        processedVariable.isVariable = variable.isVariable;
        processedVariable.modifier = variable.modifier - 1;//This is the important one
        processedVariable.value = variable.value;
        processedVariable.variableId = variable.variableId;
        return processedVariable;
    }
}
