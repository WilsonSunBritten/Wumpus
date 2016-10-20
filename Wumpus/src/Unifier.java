
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Unifier {

    public Unifier() {

    }

    /*
        So...Unification sucks, ive tried a ton of different things and followed numerous textbook's psedocodes.
        I'm going to leave some notes and resources ive used here and work on something else for the time being.
        First off, in a nutshell the unify method needs to take in two sentences the kb sends it.  These sentences can be
        atomic variables, lists of variables (facts), or lists of facts (clauses).  The function needs to return a set of substitutions
        that make the two arguments the same, else it returns failure in some fassion (throw an exception maybe).  I've tried using HashMaps to store these sets
        of substitution bindings, which is what most people do online, but never got it to quite work.
    
        here are some links that may or may not be useful:
        http://web.cecs.pdx.edu/~york/cs441/Luger_Idioms.pdf (part 4, pages ~300 - 330 or so)
        https://github.com/aimacode/aima-java this is a repo provided by our textbook that has code implementations for everything.  They go about it in a very complicated way though
        https://en.wikipedia.org/wiki/Unification_(computer_science) the wiki is super technical, but I did learn a fair amount from it
        https://baylor-ir.tdl.org/baylor-ir/bitstream/handle/2104/2887/AshishArteThesis.pdf?sequence=4 this is dense af, but has some good information and explaination of pseduo code
        
    
     */
    public static ArrayList<Substitute> unify1(Fact fact1, Fact fact2) {
        if (!canUnify(fact1, fact2)) {
            return new ArrayList<Substitute>();
        }

        return new ArrayList<Substitute>();
    }

    public static boolean canUnify(Fact fact1, Fact fact2) {
        //Predicates don't match
        if (!fact1.predicate.equals(fact2.predicate)) {
            return false;
        }
        return true;
    }

    //this is the general outline of how to go about unification...
    public static Substitute unify1(Variable p, Variable q, Substitute theta) {

        if (p.equals(q)) {      //success
            theta.varIdToSubstitute = p.variableId;
            theta.valToSubstituteWith = q.variableId;
            return theta;
        } else if (p.variableId != q.variableId) {
            //bind p to q and insert (p/q) into theta

            return theta;
        } else if (q.variableId != q.variableId) {
            //bind q to p and insert (q/p) into theta
            return theta;
        } else if (true) {  //if r is a variable? Occurs checks need to be done here i think
            //theta = the union of (theta, {r/s})
            //unify(substitutionOf(theta, p), substitutionOf(theta, q), theta)
        } else if (true) {  //if s is a variable? Occurs checks need to be done here i think
            //theta = the union of (theta, {s/r})
            //unify(substitutionOf(theta, p), substitutionOf(theta, q), theta)
        } else {
            //failure
        }
        return null;
    }
    public static ArrayList<Substitute> wilsonUnify(Fact f1, Fact f2){
        ArrayList<Substitute> subs = new ArrayList<>();
        if(!f1.predicate.equals(f2.predicate))
            return subs;
        
        Fact fact1 = new Fact(f1);
        Fact fact2 = new Fact(f2);
        //check if fact1 has a variable
        for (int i = 0; i < fact1.variables.size(); i++) {
            Variable tempVar = fact1.variables.get(i);
            if(tempVar.isVariable){
                if(!fact2.variables.get(i).isVariable){
                    Substitute sub = new Substitute();
                    sub.varIdToSubstitute = tempVar.variableId;
                    sub.valToSubstituteWith = fact2.variables.get(i).value;
                    subs.add(sub);
                }
            }
        }
        for (int i = 0; i < fact2.variables.size(); i++) {
            Variable tempVar = fact2.variables.get(i);
            if(tempVar.isVariable){
                if(!fact1.variables.get(i).isVariable){
                    Substitute sub = new Substitute();
                    sub.varIdToSubstitute = tempVar.variableId;
                    sub.valToSubstituteWith = fact1.variables.get(i).value;
                    subs.add(sub);
                }
            }
        }
        
        return subs;
    }
    public static ArrayList<Substitute> unify(Fact f1, Fact f2) {
        
        ArrayList<Substitute> subs = new ArrayList<>();
        if(!f1.predicate.equals(f2.predicate)){
            return subs;
        }
        
        ArrayList<Fact> vars = new ArrayList<>();
        Fact temp = new Fact();
        temp.variables.add(f1.variables.get(0));
        temp.variables.add(f2.variables.get(0));
        vars.add(temp);

        temp = new Fact();
        temp.variables.add(f1.variables.get(1));
        temp.variables.add(f2.variables.get(1));
        vars.add(temp);


        while (!vars.isEmpty()) {
            Fact tempFact = vars.remove(0);
            if (!tempFact.variables.get(0).equals(tempFact.variables.get(1))) {
                if (tempFact.variables.get(0).isVariable) {
                    Substitute s = new Substitute();
                    s.varIdToSubstitute = tempFact.variables.get(0).value;
                    s.valToSubstituteWith = tempFact.variables.get(1).value;
                    subs.add(s);
                } else if (tempFact.variables.get(1).isVariable) {
                    Substitute s = new Substitute();
                    s.varIdToSubstitute = tempFact.variables.get(1).value;
                    s.valToSubstituteWith = tempFact.variables.get(0).value;
                    subs.add(s);
                }
                else{
                    return new ArrayList<Substitute>();
                }
            }
        }
        return subs;
    }

    private static boolean occursCheck(Variable var, Variable term) {
        //determins if term is in var
        return true;
    }
}

//    public SubstitutionString unify(Variable x, Variable y) {
//        return (SubstitutionString) unify(x, y, (SubstitutionString) new LinkedHashMap<>());
//    }
//
//    private Map<Clause, Fact> unify(Variable x, Variable y, Map<Clause, Fact> theta) {
//
//        if (theta == null) {
//            return null;
//        } else if (x.equals(y)) {
//            return theta;
//        } else if (x instanceof Clause) {
//            return unifyClause((Clause) x, y, theta);
//        } else if (y instanceof Clause) {
//            return unifyClause((Clause) y, x, theta);
//        } else if (!x.isUnary() && !y.isUnary()) {
//           // return unify(x.getArgs(), y.getArgs(), unifyOps(x.getOp(), y.getOp(), theta));
//           return theta;
//        } else {
//            return null;
//        }
//    }
//
//    private Map<Clause, Fact> unifyClause(Clause c, Variable v, Map<Clause, Fact> theta) {
//
//        if (!Fact.class.isInstance(v)) {
//            return null;
//        } else if (theta.keySet().contains(c)) {
//            return unify(theta.get(c), v, theta);
//        } else if (theta.keySet().contains(v)) {
//            return unify(c, theta.get(v), theta);
//        } else if (occurCheck(theta, c, v)) {
//            return null;
//        } else {
//            cascadeSubstitution(theta, c, (Fact) v);
//            return theta;
//        }
//    }
//
//    private Map<Clause, Fact> cascadeSubstitution(Map<Clause, Fact> theta, Clause c, Fact f) {
//            
//        return theta;
//    }
//
//    private Map<Clause, Fact> unifyOps(String x, String y, Map<Clause, Fact> theta) {
//
//        if (theta == null) {
//            return null;
//        } else if (x.equals(y)) {
//            return theta;
//        } else {
//            return null;
//        }
//    }
//
//    private boolean occurCheck(Map<Clause, Fact> theta, Clause clause, Variable var) {
//
//        if (clause.equals(var)) {
//            return true;
//        } else if (!theta.containsKey(var)) {
//            if (!var.isVariable) {       //is function
//                //recursivly perform occursCheck on each of functions arguments?
//                if (true) {     //tem
//                    return true;
//                }
//            }
//        } else {
//            return occurCheck(theta, clause, theta.get(var));
//        }
//        return false;
//    }
//}
