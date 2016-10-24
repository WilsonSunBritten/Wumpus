
public class Variable {

    protected boolean isVariable;
    protected IFunction function;
    protected int variableId, modifier, value = -1;

    public Variable(Variable var) {

        isVariable = var.isVariable;
        value = var.value;
        function = var.function;
        variableId = var.variableId;
        modifier = var.modifier;
    }

    public Variable() {
    }

    public Variable(int value, boolean isVariable, IFunction function) {

        this.isVariable = isVariable;
        if (!isVariable) {
            this.value = value;
        } else {
            this.variableId = value;
        }
        this.function = function;
    }

    public void printVariable() {

        if (isVariable) {
            System.out.print((char) (variableId + 97));
        } else {
            System.out.print(value);
        }
    }
}
