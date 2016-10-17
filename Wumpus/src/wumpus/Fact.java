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
    Variable xVariable;
    IFunction xFunction;
    IFunction yFunction;
    int y;
    boolean isYVariable;
    Variable yVariable;
    String predicate;
    boolean not;//if true then it's !Predicate(x,y)
}
