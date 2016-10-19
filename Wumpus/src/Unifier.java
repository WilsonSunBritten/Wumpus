
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Unifier {

    public Unifier() {

    }

    public SubstitutionString unify(Variable x, Variable y) {
        return unify(x, y, (SubstitutionString) new LinkedHashMap<>());
    }

    private Map<Clause, Fact> unify(Variable x, Variable y, Map<Clause, Fact> theta) {

        if (theta == null) {
            return null;
        } else if (x.equals(y)) {
            return theta;
        } else if (x instanceof Clause) {
            return unifyClause((Clause) x, y, theta);
        } else if (y instanceof Clause) {
            return unifyClause((Clause) y, x, theta);
        } else if (!x.isUnary() && !y.isUnary()) {
            return unify(x.getArgs(), y.getArgs(), unifyOps(x.getOp(), y.getOp(), theta));
        } else {
            return null;
        }
    }

    private Map<Clause, Fact> unifyClause(Clause c, Variable v, Map<Clause, Fact> theta) {

        if (!Fact.class.isInstance(v)) {
            return null;
        } else if (theta.keySet().contains(c)) {
            return unify(theta.get(c), v, theta);
        } else if (theta.keySet().contains(v)) {
            return unify(c, theta.get(v), theta);
        } else if (occurCheck(theta, c, v)) {
            return null;
        } else {
            cascadeSubstitution(theta, c, (Fact) v);
            return theta;
        }
    }

    private Map<Clause, Fact> cascadeSubstitution(Map<Clause, Fact> theta, Clause c, Fact f) {
            
        
    }

    private Map<Clause, Fact> unifyOps(String x, String y, Map<Clause, Fact> theta) {

        if (theta == null) {
            return null;
        } else if (x.equals(y)) {
            return theta;
        } else {
            return null;
        }
    }

    private boolean occurCheck(Map<Clause, Fact> theta, Clause clause, Variable var) {

        if (clause.equals(var)) {
            return true;
        } else if (!theta.containsKey(var)) {
            if (!var.isVariable) {       //is function
                //recursivly perform occursCheck on each of functions arguments?
                if (true) {     //tem
                    return true;
                }
            }
        } else {
            return occurCheck(theta, clause, theta.get(var));
        }
        return false;
    }
}
