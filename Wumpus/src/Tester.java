
import java.util.ArrayList;

public class Tester {
    
    public int[] initialParse(String ruleString){
        int[] initials = new int[3];
        if(ruleString.startsWith("V") || ruleString.startsWith("E")){
            //Has quantifiers
        }
        return initials;
    }

    public Rule addRule(String ruleString) {
        ruleString = "Vx,y Commutative(x,y) IFF Commutative(y,x)";
        Rule rule = new Rule();
        ArrayList<Quantifier> quants = new ArrayList();
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
            System.out.println(ruleString);
        }
        
        Rule leftRule = new Rule();
        Fact leftFact = new Fact();
        leftFact.predicate = ruleString.substring(0, ruleString.indexOf("("));
        ruleString = ruleString.substring(ruleString.indexOf("("));
        System.out.println(ruleString);
        String vars = ruleString.substring(1, ruleString.indexOf(")"));
        System.out.println(vars);
        int i = 0;
        for (char c : vars.toCharArray()) {
            if (c > 64) {
                Variable v = new Variable();
                if (i + 1 < vars.length() && vars.toCharArray()[i + 1] == 44) {
                    v.isVariable = true;
                    v.variableId = checkQuantifiers(quants, c).variableId;
                    leftFact.variables.add(v);
                    System.out.println(v.variableId);
                } else if (i + 1 == vars.length() && i - 1 > -1 && vars.toCharArray()[i - 1] == 44) {
                    v.isVariable = true;
                    v.variableId = checkQuantifiers(quants, c).variableId;
                    leftFact.variables.add(v);
                    System.out.println(v.variableId);
                }
            }
            i++;
        }
        leftRule.justFact = true;
        leftRule.fact = leftFact;
        ruleString = ruleString.substring(ruleString.indexOf(" ") + 1);
        System.out.println(ruleString);
        
        rule.connector = checkConnector(ruleString.substring(0, ruleString.indexOf(" ")));
        System.out.println(rule.connector);
        
        ruleString = ruleString.substring(ruleString.indexOf(" ") + 1);
        System.out.println(ruleString);
        
        Rule rightRule = new Rule();
        Fact rightFact = new Fact();
        leftFact.predicate = ruleString.substring(0, ruleString.indexOf("("));
        ruleString = ruleString.substring(ruleString.indexOf("("));
        System.out.println(ruleString);
        vars = ruleString.substring(1, ruleString.indexOf(")"));
        System.out.println(vars);
        i = 0;
        for (char c : vars.toCharArray()) {
            if (c > 64) {
                Variable v = new Variable();
                if (i + 1 < vars.length() && vars.toCharArray()[i + 1] == 44) {
                    v.isVariable = true;
                    v.variableId = checkQuantifiers(quants, c).variableId;
                    rightFact.variables.add(v);
                    System.out.println(v.variableId);
                } else if (i + 1 == vars.length() && i - 1 > -1 && vars.toCharArray()[i - 1] == 44) {
                    v.isVariable = true;
                    v.variableId = checkQuantifiers(quants, c).variableId;
                    rightFact.variables.add(v);
                    System.out.println(v.variableId);
                }
            }
            i++;
        }
        rightRule.justFact = true;
        rightRule.fact = leftFact;
        System.out.println(ruleString);
        
        rule.leftRule = leftRule;
        rule.rightRule = rightRule;
        
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
    
    public int checkConnector(String connector){
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
