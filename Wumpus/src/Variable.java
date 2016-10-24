
public class Variable {

    boolean isVariable;
    int value = -1;//default to avoid mixups
    IFunction function;
    int variableId;
    int modifier;

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
