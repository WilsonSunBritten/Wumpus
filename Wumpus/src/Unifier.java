
import java.util.ArrayList;

public class Unifier {

    public static ArrayList<Substitute> unify(Fact f1, Fact f2) {

        ArrayList<Substitute> subs = new ArrayList<>();
        if (!f1.predicate.equals(f2.predicate)) {
            return subs;
        }

        Fact fact1 = new Fact(f1);
        Fact fact2 = new Fact(f2);
        //check if fact1 has a variable
        for (int i = 0; i < fact1.variables.size(); i++) {
            Variable tempVar = fact1.variables.get(i);
            if (tempVar.isVariable) {
                if (!fact2.variables.get(i).isVariable) {
                    Substitute sub = new Substitute();
                    sub.varIdToSubstitute = tempVar.variableId;
                    sub.valToSubstituteWith = fact2.variables.get(i).value;
                    if (tempVar.function != null) {
                        int val = tempVar.function.process(0);
                        sub.valToSubstituteWith += -1 * val;
                    }
                    subs.add(sub);
                }
            }
        }
        for (int i = 0; i < fact2.variables.size(); i++) {
            Variable tempVar = fact2.variables.get(i);
            if (tempVar.isVariable) {
                if (!fact1.variables.get(i).isVariable) {
                    Substitute sub = new Substitute();
                    sub.varIdToSubstitute = tempVar.variableId;
                    sub.valToSubstituteWith = fact1.variables.get(i).value;
                    if (tempVar.function != null) {
                        int val = tempVar.function.process(0);
                        sub.valToSubstituteWith += -1 * val;
                    }
                    subs.add(sub);
                }
            }
        }

        if (equalWithSubs(fact1, fact2, subs)) {
            return subs;
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean equalWithSubs(Fact fact1, Fact fact2, ArrayList<Substitute> subs) {

        substitute(subs, fact1);
        substitute(subs, fact2);
        for (int i = 0; i < fact1.variables.size(); i++) {
            Variable var1 = fact1.variables.get(i);
            Variable var2 = fact2.variables.get(i);
            if (var1.value != var2.value) {
                return false;
            }
        }
        return true;
    }

    public static void substitute(ArrayList<Substitute> subs, Fact fact) {

        for (Substitute sub : subs) {
            for (Variable var : fact.variables) {
                if (var.isVariable && var.variableId == sub.varIdToSubstitute) {
                    var.isVariable = false;
                    var.value = sub.valToSubstituteWith;
                }
            }
        }
    }
}
