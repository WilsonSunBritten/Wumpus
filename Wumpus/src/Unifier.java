
import java.util.HashMap;
import java.util.Map;

public class Unifier {

    public Unifier() {

    }

    public static String Unify(String p, String q) {

        String substitution = "";

        return substitution;
    }

    public static SubstitutionString unify(Sentence x, Sentence y) {
        return unify(x, y, new SubstitutionString());
    }

    private static SubstitutionString unify(Sentence x, Sentence y,SubstitutionString theta) {
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

    private static SubstitutionString unifyVariables(Variable x, Variable y, SubstitutionString theta) {

    }

    private static SubstitutionString unifyOperators(String x, String y, SubstitutionString theta) {

        if (theta == null) {
            return theta;
        } else if (x.equals(y)) {
            return theta;
        } else {
            return null;
        }
    }
}
