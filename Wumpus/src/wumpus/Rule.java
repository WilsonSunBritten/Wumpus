/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wumpus;

import java.util.ArrayList;

/**
 *
 * @author Wilson
 */
public class Rule {
    public static final int AND = 1;
    public static final int OR = 2;
    public static final int IMPLIES = 3;
    public static final int IFF = 4;
    
    ArrayList<Quantifier> quantifiers = new ArrayList<>();
    boolean overAllNot;
    boolean leftRuleNot;
    boolean rightRuleNot;
    Fact fact;
    Rule leftRule;
    Rule rightRule;
    boolean justFact = false;
    int connector;
    public Rule(){}
    public Rule(Fact fact){
        this.fact = fact;
        justFact = true;
    }
}
