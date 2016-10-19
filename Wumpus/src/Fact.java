
import java.util.ArrayList;
import java.util.Iterator;

public class Fact extends Variable implements Iterable {

    ArrayList<Variable> variables = new ArrayList<>();
    String predicate;
    boolean isEvaluation;
    int operator;
    boolean not;
    
    public Fact(){}
    public Fact(String predicate, int var1Val, int var2Val, int var3Val, boolean not){
        Variable var1 = new Variable(var1Val);
        Variable var2 = new Variable(var2Val);
        Variable var3 = new Variable(var3Val);
        this.predicate = predicate;
        this.not = not;
    }
    public void printFact() {
        System.out.print(predicate + "(");
        for (Variable var : variables) {
            var.printVariable();
            System.out.print(", ");
        }
        System.out.print(") ");
    }
    
    @Override
    public boolean isUnary() {
        return false;
    }
    
    @Override
    public int getOp() {
        return operator;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
