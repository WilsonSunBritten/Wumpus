
import java.util.ArrayList;

public class Fact extends Variable {

    ArrayList<Variable> variables = new ArrayList<>();
    String predicate;
    boolean not;

    public Fact(Fact fact) {
        not = fact.not;
        
        for(Variable var : fact.variables){
            variables.add(new Variable(var));
        }
        predicate = fact.predicate;

    }

    public Fact() {
    }

    public Fact(String predicate, int var1Val, boolean var1Var, int var2Val, boolean var2Var, boolean not, IFunction var1Function, IFunction var2Function) {
        Variable var1 = new Variable(var1Val, var1Var, var1Function);
        Variable var2 = new Variable(var2Val, var2Var, var2Function);
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
    public boolean contains(Variable var) {
        for (Variable v : variables) {
            if (v.equals(var)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList getArgs() {
        return variables;
    }
}
