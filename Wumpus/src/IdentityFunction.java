
public class IdentityFunction implements IFunction {

    @Override
    public int process(int value) {
        return value;
    }

    @Override
    public Variable processVariable(Variable variable) {
        return variable;
    }
}
