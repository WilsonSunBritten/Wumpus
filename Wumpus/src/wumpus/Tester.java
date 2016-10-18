/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wumpus;

/**
 *
 * @author Wilson
 */
public class Tester {
     public void testInferenceEngine(){
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
        //engine.convertToCNF(null);
        
    }
}
