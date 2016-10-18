
public class MinusFunction implements IFunction{
    public int process(int value){
        return value-1;
    }
    public void processVariable(Variable variable){
        variable.modifier -=1;
    }
}
