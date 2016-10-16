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
public class Rule {
    public static final int AND = 1;
    public static final int OR = 2;
    public static final int IMPLIES = 3;
    public static final int IFF = 4;
    
    Fact fact1;
    Fact fact2;
    Rule rule1;
    Rule rule2;
    int connector;
}
