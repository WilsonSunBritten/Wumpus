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
        
    }
    
    public void convertToCNFStepThree(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepFour(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepFive(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepSix(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepSeven(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepEight(ArrayList<Rule> rules){
        
    }
    public void convertToCNFStepNine(ArrayList<Rule> rules){
        
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
