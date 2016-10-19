
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public class WumpusGame {

    private int boardSize;
    private int wumpus;
    private Space[][] board;
    private Random random = new Random();
    private HashMap probabilityGeneration;
    private int[] prob;
    private byte[][] perceptBoard;
    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;
    private PrintWriter out = new PrintWriter(new File("PerceptBoard.txt"));

    public WumpusGame(int boardSize, int[] prob) throws FileNotFoundException {
        this.boardSize = boardSize;
        this.prob = prob;
        perceptBoard = new byte[boardSize][boardSize];
        board = new Space[boardSize][boardSize];
        setBoard();
        initializeBoard();
        PrintStream out = new PrintStream(new FileOutputStream("world.txt"));
        System.setOut(out);
        printBoards();
    }

    public void setBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Space();
            }
        }
    }

    public void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 0; j--) {
                chooseState(i, j);
            }
        }
        int x = random.nextInt(boardSize);
        int y = random.nextInt(boardSize);
        while (board[x][y].isFilled()) {
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
        }
        placeGold(x, y);

        while (board[x][y].isFilled()) {
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
        }
        placeStart(x, y);
    }

    public void chooseState(int x, int y) {

        if ((random.nextInt(100) + 1) <= prob[0]) {
            placePit(x, y);
        } else if ((random.nextInt(100) + 1) <= prob[1]) {
            placeObstacle(x, y);
        } else if ((random.nextInt(100) + 1) <= prob[2]) {
            placeWumpus(x, y);
        }
    }

    public void placePercept(int x, int y, byte percept) {
        perceptBoard[x][y] |= percept;
    }

    public void placeAdjacentPercept(int x, int y, byte percept) {
        if (x > 0) {
            placePercept(x - 1, y, percept);
        }
        if (x < boardSize - 1) {
            placePercept(x + 1, y, percept);
        }
        if (y > 0) {
            placePercept(x, y - 1, percept);
        }
        if (y < boardSize - 1) {
            placePercept(x, y + 1, percept);
        }
    }

    public void placeObstacle(int x, int y) {
        board[x][y].setHasObstacle(true);
        placePercept(x, y, BUMP);
    }

    public void placePit(int x, int y) {
        board[x][y].setHasHole(true);
        placeAdjacentPercept(x, y, BREEZE);
        placePercept(x, y, DEATH_BY_PIT);
    }

    public void placeGold(int x, int y) {
        board[x][y].setHasGold(true);
        placePercept(x, y, GLITTER);
    }

    public void placeWumpus(int x, int y) {
        board[x][y].toggleWumpus();
        placeAdjacentPercept(x, y, STENTCH);
        placePercept(x, y, DEATH_BY_WUMPUS);
        wumpus++;
    }

    public void placeStart(int x, int y) {
        board[x][y].setStart(true);
    }

    public void checkBlockedStart() {

    }

    public void printBoards() {
        printBoard();
        printPerceptBoard();
    }

    public void printPerceptBoard() {
        out.println(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                out.print(perceptBoard[i][j] + " ");
            }
            out.println();
        }
        out.close();
    }

    public void printBoard() {
        System.out.println(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isHasWumpus()) {
                    System.out.print("W ");
                } else if (board[i][j].isHasHole()) {
                    System.out.print("H ");
                } else if (board[i][j].isHasObstacle()) {
                    System.out.print("I ");
                } else if (board[i][j].isHasGold()) {
                    System.out.print("G ");
                } else if (board[i][j].isStart()) {
                    System.out.print("S ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }
}
