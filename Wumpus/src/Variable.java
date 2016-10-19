
import java.util.Iterator;


public class Variable implements Iterable {

    boolean isVariable;
    int value;
    IFunction function;
    int variableId;
    int modifier;
    boolean isSkolemConstant;//Skolem function would just be the function, a skolem constant will have this and it's varId making a unique identifier

    public Variable(){}
    public Variable(int value){
        this.value = value;
    }
    public void printVariable() {
        
        if (isVariable) {
            System.out.print((char) (variableId + 97));
        }
    }
    
    public int getValue() {
        return value;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
