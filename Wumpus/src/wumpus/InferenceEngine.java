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
}
