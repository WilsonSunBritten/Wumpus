
import java.util.ArrayList;

/*
    A clause contains a list of predicates
 */
public class Clause extends Fact {

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
    
    public static void printClause(Clause clause){
//        for(Fact fact : clause.facts){
//            fact.printFact();
//            if(fact != clause.facts.get(clause.facts.size()-1))
//                System.out.print(" v ");
//        }
//        System.out.println("");
    }

    @Override
    public boolean contains(Variable var) {

        for (Fact f : facts) {
            if (f.contains(var) || f.equals(var)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList getArgs() {
        return facts;
    }
}
