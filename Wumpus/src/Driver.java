
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Driver {

    public static void main(String[] args) throws IOException {
//        Driver driver = new Driver();
//        Tester tester = new Tester();
//        tester.testInferenceEngine();
        Driver.makeGame();
    }

    public static void makeGame() throws IOException {

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

}
