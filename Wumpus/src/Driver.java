
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Driver {

    public static void main(String[] args) throws IOException {
//        Driver driver = new Driver();
//        Tester tester = new Tester();
//        tester.testPathFinder();
        //Tester tester = new Tester();
        //tester.testPathFinder();
        //tester.testUnify();
       // tester.testInferenceEngine();
        makeGame();
        World world = new World("PerceptBoard.txt");
        world.startGame("LogicExplorer");
    }

    public static void makeGame() throws IOException {

        boolean newBoard = true;
         boolean newStart = true;
         WumpusGame game;
         if (newBoard) {
             BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
             int[] prob = new int[3];
             System.out.println("Size of board: ");
 
             int size = 5;//Integer.parseInt(dataIn.readLine());
             System.out.print("% chance of generating pit: ");
             prob[0] = 5;//Integer.parseInt(dataIn.readLine());
             System.out.println();
             System.out.print("% chance of generating obstacle: ");
             prob[1] = 5;//Integer.parseInt(dataIn.readLine());
             System.out.println("");
             System.out.print("% chance of generating wumpus: ");
             prob[2] = 5;//Integer.parseInt(dataIn.readLine());
             System.out.println("");
             game = new WumpusGame(size, prob);
         }
         if (newStart) {
             game = new WumpusGame("clean.txt");
         }
         World world = new World("PerceptBoard.txt");
         world.startGame("LogicExplorer");
        //Agent explorer = new ReactiveExplorer(world, world.getLocation(), world.direction,world.getPercepts(), world.arrowCount);
      //  World world = new World("PerceptBoard.txt");
        //world.startGame("LogicExplorer");
      //  world.startGame("ReactiveExplorer");
        //Agent explorer = new LogicExplorer(world);

        //world = new World("PerceptBoard.txt");
       // Agent explorer = new ReactiveExplorer(world, world.getLocation(), world.direction, world.getPercepts(), world.arrowCount);
    }

}
