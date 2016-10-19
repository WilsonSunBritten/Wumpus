
public class Tester {

    public void addRule(String ruleString) {
        ruleString = "Vd,t Facing(d,t) => Facing(d,t+1) OR Action(Turnleft,t) OR Action(TurnRight,t)";
        Rule rule = new Rule();
        if(ruleString.contains("V")){
            String quant = ruleString.substring(ruleString.indexOf("V"), ruleString.indexOf(" "));
            System.out.println(quant);
        }
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
