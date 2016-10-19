
public class IdentityFunction implements IFunction {

    public int process(int value) {
        return value;
    }

    public Variable processVariable(Variable variable) {
        return variable;
    }
}
