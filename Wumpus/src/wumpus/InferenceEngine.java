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
public class InferenceEngine {
    KnowledgeBase kb;
    
    
    
    public ArrayList<Rule> convertToCNF(Rule rule){
        ArrayList<Rule> cnfRules = new ArrayList<>();
        ArrayList<Rule> toConvertRules = new ArrayList<>();
        toConvertRules.add(rule);
        //Step 1: break iff into two implication cases
        convertToCNFStepOne(toConvertRules);
        
        
        return cnfRules;
    }
    
    public void convertToCNFStepOne(ArrayList<Rule> rules){
        //Convert every iff to two implies statements
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                if(rule.connector == Rule.IFF){ //for each a iff b we change it to ((a=>b)&&(b=>a))
                    Rule newRule1 = new Rule();
                    newRule1.leftRule = rule.leftRule;
                    newRule1.rightRule = rule.rightRule;
                    newRule1.connector = Rule.IMPLIES;
                    Rule newRule2 = new Rule();
                    newRule2.leftRule = rule.rightRule;
                    newRule2.rightRule = rule.leftRule;
                    newRule2.connector = Rule.IMPLIES;
                    rule.leftRule = newRule1;
                    rule.rightRule = newRule2;
                    rule.connector = Rule.AND;
                }
            }
        }
    }
    
    public void convertToCNFStepTwo(ArrayList<Rule> rules){
        //Convert every a=>b to !a V b
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                if(rule.connector == Rule.IMPLIES){
                    rule.leftRule.negated = !rule.leftRule.negated;
                    rule.connector = Rule.OR;
                }
            }
        }
    }
    
    public void convertToCNFStepThree(ArrayList<Rule> rules){
        //Deal with negated quantifiers, ie !(EXISTS x, p) to (Vx, !p) or !(Vx, p) to (Exists x, !p)
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                for(Quantifier quantifier:rule.quantifiers){
                    if(quantifier.not){ //we have a negated quantifier, move inwards
                        quantifier.not = false;
                        quantifier.isExistential = !quantifier.isExistential;
                        rule.negated = !rule.negated;
                    }
                }
                    
            }
        }
    }
    public void convertToCNFStepFour(ArrayList<Rule> rules){
        //Move negation inwards, ie !(A&&B) to (!A V !B) also !(A V B) to (!A && !B)
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){//Maybe we have to order outter terms before inner terms for this to work??
                if(rule.negated){ //We assume only connectors left are OR and AND
                    rule.negated = false;
                    rule.leftRule.negated = !rule.leftRule.negated;
                    rule.rightRule.negated = !rule.rightRule.negated;
                    if(rule.connector == Rule.OR){
                        rule.connector = Rule.AND;
                    }
                    else
                        rule.connector = Rule.OR;
                }
            }
        }
    }
    public void convertToCNFStepFive(ArrayList<Rule> rules){
        //Pair every quantifier to a unique variable
        int uniqueVariableId = 0;//We will increment this for each varId used.
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            
            for(int i = allRules.size()-1; i >= 0; i--){//We want to work with most inner rules prior to the ones containing them
                Rule rule = allRules.get(i);
                for(Quantifier quantifier:rule.quantifiers){
                    ArrayList<Rule> subRules = getAllRules(rule);
                    for(Rule subRule:subRules){
                        if(subRule.justFact){
                            for(Variable var:subRule.fact.variables){
                                if(var.variableId == quantifier.variableId){
                                    var.variableId = uniqueVariableId;
                                }
                            }
                        }
                    }
                    uniqueVariableId++;
                }
            }
        }
    }
    public void convertToCNFStepSix(ArrayList<Rule> rules){
        //Move all quantifiers to 'top' rule
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));//assume allRules.get(0) is topMost rule.
            ArrayList<Quantifier> allQuantifiers = new ArrayList<>();
            for(Rule rule:allRules){
                allQuantifiers.addAll(rule.quantifiers);
                rule.quantifiers = new ArrayList<>();
            }
            allRules.get(0).quantifiers = allQuantifiers;
        }
    }
    public void convertToCNFStepSeven(ArrayList<Rule> rules){
        //Skolemize existentials... this will be a bitch...
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                
            }
        }
    }
    public void convertToCNFStepEight(ArrayList<Rule> rules){
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                
            }
        }
    }
    public void convertToCNFStepNine(ArrayList<Rule> rules){
        while(!rules.isEmpty()){
            ArrayList<Rule> allRules = getAllRules(rules.get(0));
            for(Rule rule:allRules){
                
            }
        }
    }
    
    public ArrayList<Rule> getAllRules(Rule rule){
        ArrayList<Rule> allRules = new ArrayList<>();
        if(rule.leftRule != null){
            allRules.addAll(getAllRules(rule.leftRule));
        }
        if(rule.rightRule != null){
            allRules.addAll(getAllRules(rule.rightRule));
        }
        allRules.add(rule);
        
        return allRules;
    }
}
