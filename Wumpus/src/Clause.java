
import java.util.ArrayList;

/*
 A clause contains a list of predicates
 */
public class Clause {

    ArrayList<Fact> facts = new ArrayList<>();

    public Clause() {
    }

    public Clause(Clause clause) {
        for (Fact tempFact : clause.facts) {
            facts.add(new Fact(tempFact));
        }
    }

    public Clause(Fact fact) {

        facts.add(fact);
    }

    public static void printClause(Clause clause) {
        for (Fact fact : clause.facts) {
            fact.printFact();
            if (fact != clause.facts.get(clause.facts.size() - 1)) {
                System.out.print(" v ");
            }
        }
        System.out.println("");
    }
}
