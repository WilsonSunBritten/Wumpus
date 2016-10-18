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
public class Fact {
    ArrayList<Variable> variables = new ArrayList<>();
    String predicate;
    boolean isEvaluation;
    int operator;
    
    public void printFact(){
        System.out.print(predicate+"(");
        for (Variable var:variables) {
            var.printVariable();
            System.out.print(", ");
        }
        System.out.print(") ");
    }
}
