
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import wumpus.InferenceEngine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author j48k851
 */
public class Driver {
    public static void main(String[] args) throws IOException{
        testInferenceEngine();
        //makeGame();
    }
    
    public static void makeGame() throws IOException{
        
        BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
        int[] prob = new int[3];
        System.out.print("% chance of generating pit: ");
        prob[0] = Integer.parseInt(dataIn.readLine());
        System.out.println();
        System.out.print("% chance of generating obstacle: ");
        prob[1] = Integer.parseInt(dataIn.readLine());
        System.out.println("");
        System.out.print("% chance of generating wumpus: ");
        prob[2] = Integer.parseInt(dataIn.readLine());
        System.out.println("");
        
        WumpusGame game = new WumpusGame(5, prob);
    }
    
    public static void testInferenceEngine(){
        InferenceEngine engine = new InferenceEngine();
        
        
        
        engine.convertToCNF(null);
    }
}
