
import java.util.ArrayList;

public class Rule {

    private static final int AND = 1, OR = 2, IMPLIES = 3, IFF = 4;

    private final ArrayList<Quantifier> quantifiers = new ArrayList<>();
    private Fact fact;
    private Rule leftRule, rightRule;
    private boolean justFact = false;
    private int connector;

    public Rule() {

    }

    public Rule(Fact fact) {

        this.fact = fact;
        justFact = true;
    }

    public void printRule() {

        if (justFact) {
            fact.printFact();
            return;
        }
        for (Quantifier quantifier : quantifiers) {
            quantifier.printQuantifier();
        }
        System.out.print(" (");
        leftRule.printRule();
        System.out.print(") ");
        switch (connector) {
            case OR:
                System.out.print("OR");
                break;
            case AND:
                System.out.print("AND");
                break;
            case IFF:
                System.out.print("IFF");
                break;
            case IMPLIES:
                System.out.print("IMPLIES");
        }
        System.out.print(" (");
        rightRule.printRule();
        System.out.println(")");

    }

    class Quantifier {

        private int variableId;
        private boolean isExistential;

        public void printQuantifier() {
            if (isExistential) {
                System.out.print("EXIST(");
            } else {
                System.out.print("FORALL(");
            }
            System.out.print((char) (variableId + 97) + ") ");
        }
    }

}
