
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public class WumpusGame {

    private int boardSize, wumpus, startX, startY;
    private Space[][] board;
    private Random random = new Random();
    private HashMap probabilityGeneration;
    private int[] prob;
    private byte[][] perceptBoard;
    protected final byte BREEZE = 0b00000001, STENCH = 0b0000010, BUMP = 0b00000100, GLITTER = 0b00001000, DEATH = 0b00010000, DEATH_WUMPUS = 0b00100000, SCREAM = 0b01000000;
    private PrintWriter out = new PrintWriter(new File("PerceptBoard.txt"));

    public WumpusGame(String fileName) throws FileNotFoundException {
        newStart(fileName);
        System.setOut(new PrintStream(new FileOutputStream("world.txt")));
        printBoards();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    public WumpusGame(int boardSize, int[] prob, boolean newOrNo) throws FileNotFoundException {
        this.boardSize = boardSize;
        this.prob = prob;
        perceptBoard = new byte[boardSize][boardSize];
        board = new Space[boardSize][boardSize];
        setBoard();
        if (!newOrNo) {
            initializeBoard();
            PrintStream out = new PrintStream(new FileOutputStream("world.txt"));
            System.setOut(out);
            printBoards();
            System.setOut(new PrintStream(new FileOutputStream("clean.txt")));
            printBoards();
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        } else {
            newStart("clean.txt");
            System.setOut(new PrintStream(new FileOutputStream("world.txt")));
            printBoards();
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        }

    }

    public void setBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Space();
            }
        }
    }

    public void newStart(String fileName) {
        try {
            FileReader in = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(in);
            String next = reader.readLine();
            int size = Integer.parseInt(next);
            this.boardSize = size;
            board = new Space[boardSize][boardSize];
            perceptBoard = new byte[boardSize][boardSize];
            setBoard();
            int i = 0;
            while ((next = reader.readLine()) != null) {//((Integer) reader.read()).toString()).equals("-1")) {
                int j = 0;
                while (next.contains(" ") && !next.equals(" ")) {
                    String temp = next.substring(0, next.indexOf(" "));
                    if (temp.equals("W")) {
                        placeWumpus(i, j);
                    } else if (temp.equals("I")) {
                        placeObstacle(i, j);
                    } else if (temp.equals("H")) {
                        placePit(i, j);
                    } else {

                    }

                    next = next.substring(next.indexOf(" ") + 1, next.length());
                    j++;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        startX = x;
        startY = y;
        placeStart(x, y);

    }

    public void initializeBoard() {
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                chooseState(i, j);
            }
        }
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
        placePercept(x, y, DEATH);
    }

    public void placeGold(int x, int y) {
        board[x][y].setHasGold(true);
        placePercept(x, y, GLITTER);
    }

    public void placeWumpus(int x, int y) {
        board[x][y].toggleWumpus();
        placeAdjacentPercept(x, y, STENCH);
        placePercept(x, y, DEATH_WUMPUS);
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
        out.print(boardSize + " ");
        out.print(startX + " ");
        out.print(startY + " ");
        out.println();
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
