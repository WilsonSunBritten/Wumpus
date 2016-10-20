

public class Tester {
    
    public void testUnify(){
        Fact f1 = new Fact();
        f1.isVariable = true;
        Variable v1 = new Variable();
    }

    public void testInferenceEngine() {
        InferenceEngine engine = new InferenceEngine(new KnowledgeBase());

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
