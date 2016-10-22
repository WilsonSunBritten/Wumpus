
import java.util.ArrayList;

public class Rule {

    public static final int AND = 1;
    public static final int OR = 2;
    public static final int IMPLIES = 3;
    public static final int IFF = 4;

    ArrayList<Quantifier> quantifiers = new ArrayList<>();
    boolean negated;
    //boolean leftRuleNot;
    //boolean rightRuleNot;
    Fact fact;
    Rule leftRule;
    Rule rightRule;
    boolean justFact = false;
    int connector;

    public Rule() {
        
    }

    public Rule(Fact fact) {
        this.fact = fact;
        justFact = true;
    }

    public void printRule() {
//        if (justFact) {
//            fact.printFact();
//            return;
//        }
//        for (Quantifier quantifier : quantifiers) {
//            quantifier.printQuantifier();
//        }
//        System.out.print(" (");
//        leftRule.printRule();
//        System.out.print(") ");
//        switch (connector) {
//            case OR:
//                System.out.print("OR");
//                break;
//            case AND:
//                System.out.print("AND");
//                break;
//            case IFF:
//                System.out.print("IFF");
//                break;
//            case IMPLIES:
//                System.out.print("IMPLIES");
//        }
//        System.out.print(" (");
//        rightRule.printRule();
//        System.out.println(")");

    }
}
