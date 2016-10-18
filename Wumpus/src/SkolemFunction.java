
public class SkolemFunction implements IFunction {

    Variable var;

    public SkolemFunction(Variable variable) {
        var = variable;
    }

    @Override
    public int process(int value) {
        return value;//idk yet
    }

    @Override
    public Variable processVariable(Variable variable) {
        return var;

    }
}
