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
public class Fact {
    int x;
    boolean isXVariable;
    int xVariableIdentifier;
    int y;
    boolean isYVariable;
    int yVariableIdentifier;
    String predicate;
    boolean not;//if true then it's !Predicate(x,y)
}
