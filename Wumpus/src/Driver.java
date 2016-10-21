
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
        //makeGame();
        World world = new World("PerceptBoard.txt");
        world.startGame("LogicExplorer");
    }

    public static void makeGame() throws IOException {

        BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
        int[] prob = new int[3];
        System.out.println("Size of board: ");
        
        int size = Integer.parseInt(dataIn.readLine());
        System.out.print("% chance of generating pit: ");
        prob[0] = Integer.parseInt(dataIn.readLine());
        System.out.println();
        System.out.print("% chance of generating obstacle: ");
        prob[1] = Integer.parseInt(dataIn.readLine());
        System.out.println("");
        System.out.print("% chance of generating wumpus: ");
        prob[2] = Integer.parseInt(dataIn.readLine());
        System.out.println("");

        WumpusGame game = new WumpusGame(size, prob);

        //Agent explorer = new ReactiveExplorer(world, world.getLocation(), world.direction,world.getPercepts(), world.arrowCount);
      //  World world = new World("PerceptBoard.txt");
        //world.startGame("LogicExplorer");
      //  world.startGame("ReactiveExplorer");
        //Agent explorer = new LogicExplorer(world);

        //world = new World("PerceptBoard.txt");
       // Agent explorer = new ReactiveExplorer(world, world.getLocation(), world.direction, world.getPercepts(), world.arrowCount);
    }

}
