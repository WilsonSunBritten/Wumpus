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
public class Quantifier {
    int variableId;
    boolean isExistential; //if false, assumed universal...
    boolean not;
    
    public void printQuantifier(){
        if(isExistential)
                System.out.print("EXIST(");
            else
                System.out.print("FORALL(");
            System.out.print((char)(variableId+97) + ") ");
    }
}
