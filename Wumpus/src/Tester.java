
import java.util.ArrayList;

public class Tester {

    public int[] initialParse(String ruleString) {
        int[] initials = new int[3];
        if (ruleString.startsWith("V") || ruleString.startsWith("E")) {
            //Has quantifiers
            initials[0] = 1;
        } else {
            initials[0] = 0;
        }

        return initials;
    }

    public Rule addRule(String ruleString) {
        ruleString = "Vx,y Commutative(x,y) IFF Commutative(y,x)  ";
        Rule rule = new Rule();
        ArrayList<Quantifier> quants = new ArrayList();
        ArrayList<Fact> facts = new ArrayList();
        ArrayList<Rule> rules = new ArrayList();
        //Gets quantifier for main rule
        if (ruleString.contains("V")) {
            String quant = ruleString.substring(ruleString.indexOf("V") + 1, ruleString.indexOf(" "));
            int i = 0;
            for (char c : quant.toCharArray()) {
                if (c > 64) {
                    Quantifier q = new Quantifier();
                    q.variableRep = c;
                    q.variableId = i;
                    quants.add(q);
                    rule.quantifiers.add(q);
                    i++;
                }
            }
            ruleString = ruleString.substring(ruleString.indexOf(" ") + 1);
        }

        int numPredicates = 0;
        String tempRule = ruleString;
        while (!tempRule.equals("")) {
            if (checkConnector(tempRule.substring(0, tempRule.indexOf(" "))) != 0) {
                numPredicates++;
            }
            tempRule = tempRule.substring(tempRule.indexOf(" ") + 1);
        }
        if (numPredicates != 0) {
            numPredicates++;
        }

        if (numPredicates == 2) {
            int connector = 0;
            for (int j = 0; j < numPredicates; j++) {
                Rule sideRule = new Rule();
                Fact sideFact = new Fact();
                sideFact.predicate = ruleString.substring(0, ruleString.indexOf("("));
                ruleString = ruleString.substring(ruleString.indexOf("("));
                String vars = ruleString.substring(1, ruleString.indexOf(")"));
                int i = 0;
                for (char c : vars.toCharArray()) {
                    if (c > 64) {
                        Variable v = new Variable();
                        if (i + 1 < vars.length() && vars.toCharArray()[i + 1] == 44) {
                            v.isVariable = true;
                            v.variableId = checkQuantifiers(quants, c).variableId;
                            sideFact.variables.add(v);
                        } else if (i + 1 == vars.length() && i - 1 > -1 && vars.toCharArray()[i - 1] == 44) {
                            v.isVariable = true;
                            v.variableId = checkQuantifiers(quants, c).variableId;
                            sideFact.variables.add(v);
                        }
                    }
                    i++;
                }
                ruleString = ruleString.substring(ruleString.indexOf(" ") + 1);
                if(checkConnector(ruleString.substring(0, ruleString.indexOf(" "))) != 0){
                    connector = rule.connector = checkConnector(ruleString.substring(0, ruleString.indexOf(" ")));
                    ruleString = ruleString.substring(ruleString.indexOf(" ") + 1);
                }
                sideRule.justFact = true;
                sideRule.fact = sideFact;
                rules.add(sideRule);
            }
            rule.connector = connector;
            rule.leftRule = rules.get(0);
            rule.rightRule = rules.get(1);
        }

        return rule;
    }

    public Quantifier checkQuantifiers(ArrayList<Quantifier> quant, char c) {
        for (Quantifier quan : quant) {
            if (quan.variableRep == c) {
                return quan;
            }
        }
        return null;
    }

    public int checkConnector(String connector) {
        switch (connector) {
            case "AND":
                return 1;
            case "OR":
                return 2;
            case "IMPLIES":
                return 3;
            case "IFF":
                return 4;
        }
        return 0;
    }

    public void testInferenceEngine() {
        InferenceEngine engine = new InferenceEngine();

        //FORALL x, y, Commutative(x,y) IFF Commutative(y,x)
        Rule rule = new Rule();
        Quantifier x = new Quantifier();
        x.variableId = 0;
        Quantifier y = new Quantifier();
        y.variableId = 1;
        rule.quantifiers.add(x);
        rule.quantifiers.add(y);
        Rule leftRule = new Rule();
        Fact leftFact = new Fact();
        leftFact.predicate = "Commutative";
        Variable varX = new Variable();
        varX.isVariable = true;
        varX.variableId = x.variableId;
        Variable varY = new Variable();
        varY.isVariable = true;
        varY.variableId = y.variableId;
        leftFact.variables.add(varX);
        leftFact.variables.add(varY);
        leftRule.justFact = true;
        leftRule.fact = leftFact;
        Fact rightFact = new Fact();
        Rule rightRule = new Rule();
        rightFact.predicate = "Commutative";
        rightFact.variables.add(varY);
        rightFact.variables.add(varX);
        rightRule.justFact = true;
        rightRule.fact = rightFact;
        rule.leftRule = leftRule;
        rule.rightRule = rightRule;
        rule.connector = Rule.IFF;

        rule.printRule();
        Rule newRule = new Rule();
        newRule = addRule("");
        newRule.printRule();

//        //Vd,t Facing(d,t) => Facing(d,t+1) OR Action(Turnleft,t) OR Action(TurnRight,t)
//        Rule rule2 = new Rule();
//        x.variableId = 0;
//        y.variableId = 1;
//        rule2.quantifiers.add(x);
//        rule2.quantifiers.add(y);
//        leftFact.predicate = "Facing";
        //engine.convertToCNF(null);
    }
}
