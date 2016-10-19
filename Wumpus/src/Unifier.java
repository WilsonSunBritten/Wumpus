
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
            return unify(x.getArgs(), y.getArgs(), UnifyOps(x.getOp(), y.getOp(), theta));
        } else {
            return null;
        }
    }
    
    private Map<Clause, Fact> unifyClause(Clause clause, Variable var, Map<Clause, Fact> theta) {
        
        if (!Fact.class.isInstance(var)) {
            return null;
        } else if (theta.keySet().contains(clause)) {
            return unify(theta.get(clause), var, theta);
        } else if (theta.keySet().contains(var)) {
            return unify(clause, theta.get(var), theta);
        } else if (occurCheck(theta, clause, var)) {
            return null;
        } else {
            cascadeSubstittution(theta, clause, (Fact) var);
            return theta;
        }   
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
    
    private ArrayList args(Variable x) {
        
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
    
    
    
    
    
    
    
    
    
    
    public static SubstitutionString unify(Variable x, Variable y, SubstitutionString theta) {

        if (theta == null) {
            return null;
        } else if (x.equals(y)) {
            return theta;
        } else if (x instanceof Variable) {
            return unifyVariable(x, y, theta);
        } else if (y instanceof Variable) {
            return unifyVariable(y, x, theta);
        } //else if (x) {
            
        //}
        return null;
    }

    private static SubstitutionString unifyVariable(Variable x, Variable y, SubstitutionString theta) {

        if (theta.contains(x)) {
            return unify(theta.get(x), y, theta);
        } else if (occurCheck(x, y)) {
            return null;
        } else {
            return extend(theta, x, y);
        }
    }

    
    private static boolean occurCheck(Variable x, Variable y) {
        
//        if (x.equals(y)) {
//            return true;
//        } else if (x instanceof Sentence) {
//            return x.getOp() == y.getOp() || occurCheck(x, y.getArgs());
////        } else if (!(x instanceof Clause)) {
////            for ( x1 : x) {
////                
////            }
//        } else {
//            return false;
//        }
        return true;
    }
    
    private static SubstitutionString extend(SubstitutionString theta, Variable x, Variable y) {
        
        SubstitutionString temp = theta;
        temp.set(x, y);
        return temp;
    }
    public static String Unify(String p, String q) {

        String substitution = "";

        return substitution;
    }

    public static boolean unify(Object x, Object y) {

        x = deref(x);
        y = deref(y);

        if (x.equals(y)) {
            return true;
        } else if (x instanceof Variable) {
            setBinding((Variable) x, y);
            return true;
        } else if (y instanceof Variable) {
            setBinding((Variable) y, x);
            return true;
        } else {
            return false;
        }
    }

    private static Object deref(Object object) {

//        while (object instanceof Variable && ((Variable) object).binding != null) {
//            object = ((Variable) object).binding;
//        }
        return object;
    }

    private static void setBinding(Variable var, Object val) {
//        var.binding = val;
//        trail.push(var);
    }

    private static void undoBindings(Object token) {

//        Object var;
//        while ((var = trail.pop()) != token) {
//            if (var instanceof Variable) {
//                ((Variable) v).binding = null;
//            }
//        }
    }
//
//    public static SubstitutionString unify(Variable x, Variable y) {
//        return unify(x, y, new SubstitutionString());
//    }
//
//    private static SubstitutionString unify(Variable x, Variable y, SubstitutionString theta) {
//        if (theta == null) {            //return null in case of failure to find substitution
//            return null;
//        } else if (x.equals(y)) {       //if sentences are equal theta contains the complete substitution
//            return theta;
//        } else if (x instanceof Variable) {
//            // else if VARIABLE?(x) then return UNIVY-VAR(x, y, theta)
//            return unifyVariables((Variable) x, (Variable) y, theta);
//        } else if (y instanceof Variable) {
//            // else if VARIABLE?(y) then return UNIFY-VAR(y, x, theta)
//            return unifyVariables((Variable) y, (Variable) x, theta);
//            // } else if () {      //if x and y are compound
//            //    return unify(x.getArguments(), y.getArguments(), unifyOperators(getOperator(x), y.getValue(), theta));
//        } else {
//            return null;
//        }
//    }
//
//    private static SubstitutionString unifyVariables(Variable x, Variable y, SubstitutionString theta) {
//        return null;
//    }
//
//    private static SubstitutionString unifyOperators(String x, String y, SubstitutionString theta) {
//
//        if (theta == null) {
//            return theta;
//        } else if (x.equals(y)) {
//            return theta;
//        } else {
//            return null;
//        }
//    }
//}
}