
import java.util.ArrayList;

public class Fact {

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
}
