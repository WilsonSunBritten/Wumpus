
import java.util.ArrayList;

public class Fact {

    ArrayList<Variable> variables = new ArrayList<>();
    String predicate;
    boolean isEvaluation;
    int operator;

    public void printFact() {
        System.out.print(predicate + "(");
        for (Variable var : variables) {
            var.printVariable();
            System.out.print(", ");
        }
        System.out.print(") ");
    }
}
