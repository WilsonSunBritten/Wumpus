
import java.util.HashMap;
import java.util.Map;

public class Unifier {

    public Unifier() {

    }

    public static String Unify(String p, String q) {

        String substitution = "";

        return substitution;
    }

    public static Map<Variable, Variable> unify(Sentence x, Sentence y) {
        return (Map<Variable, Variable>) unify(x, y, new HashMap<>());
    }

    private static Map<Variable, Variable> unify(Sentence x, Sentence y,
            Map<Variable, Variable> theta) {
        if (theta == null) {            //return null in case of failure to find substitution
            return null;
        } else if (x.equals(y)) {       //if sentences are equal theta contains the complete substitution
            return theta;
        } else if (x instanceof Variable) {
            // else if VARIABLE?(x) then return UNIVY-VAR(x, y, theta)
            return unifyVariables((Variable) x, (Variable) y, theta);
        } else if (y instanceof Variable) {
            // else if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
            return unifyVariables((Variable) y, (Variable) x, theta);
       // } else if () {      //if x and y are compound
        //    return unify(x.getArguments(), y.getArguments(), unifyOperators(getOperator(x), y.getValue(), theta));
        } else {
            return null;
        }
    }
    
    private static Map<Variable, Variable> unifyVariables(Variable x, Variable y, Map<Variable, Variable> theta) {
        return null;
    }
    
    private static Map<Variable, Variable> unifyOperators(String x, String y, Map<Variable, Variable> theta) {
        
        if (theta == null) {
            return theta;
        } else if (x.equals(y)) {
            return theta;
        } else {
            return null;
        }
    }
}
