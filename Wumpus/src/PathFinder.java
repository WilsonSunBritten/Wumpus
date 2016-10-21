
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {

    //Blocked cells are just null Cell values in grid
    Cell[][] map;
    PriorityQueue<Cell> open;
    ArrayList<Cell> path = new ArrayList<>();

    boolean closed[][];
    int startX, startY, endX, endY;

    public PathFinder(int endX, int endY, int startX, int startY, boolean[][] blocked) {
        map = new Cell[blocked.length][blocked.length];
        getPath(endX, endY, startX, startY, blocked);
    }

    public void getPath(int endX, int endY, int startX, int startY, boolean[][] blocked) {

        for (int i = 0; i < blocked.length - 1; i++) {
            for (int j = 0; j < blocked.length - 1; j++) {
                if (blocked[i][j] != true) {
                    map[i][j] = null;
                }
            }
        }
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        AStar();
        while (open.peek() != null) {
            path.add(open.poll());
        }
        printPath();
    }

    private void printPath() {

        for (Cell c : path) {
            System.out.println(c.x + ", " + c.y);
        }
    }

    void checkAndUpdateCost(Cell current, Cell cell, int cost) {
        if (cell == null || closed[cell.x][cell.y]) {
            return;
        }
        int t_final_cost = cost;

        boolean inOpen = open.contains(cell);
        if (!inOpen || t_final_cost < cell.finalCost) {
            cell.finalCost = t_final_cost;
            cell.parent = current;
            if (!inOpen) {
                open.add(cell);
            }
        }
    }

    public void AStar() {

        //add the start location to open list.
        open.add(map[startX][startY]);
        Cell current;

        while (true) {
            current = open.poll();
            if (current == null) {
                break;
            }
            closed[current.x][current.y] = true;

            if (current.equals(map[endX][endY])) {
                return;
            }

            Cell cell;
            if (current.x - 1 >= 0) {
                cell = map[current.x - 1][current.y];
                checkAndUpdateCost(current, cell, current.finalCost++);

                if (current.y - 1 >= 0) {
                    cell = map[current.x - 1][current.y - 1];
                    checkAndUpdateCost(current, cell, current.finalCost++);
                }

                if (current.y + 1 < map[0].length) {
                    cell = map[current.x - 1][current.y + 1];
                    checkAndUpdateCost(current, cell, current.finalCost++);
                }
            }

            if (current.y - 1 >= 0) {
                cell = map[current.x][current.y - 1];
                checkAndUpdateCost(current, cell, current.finalCost++);
            }

            if (current.y + 1 < map[0].length) {
                cell = map[current.x][current.y + 1];
                checkAndUpdateCost(current, cell, current.finalCost++);
            }

            if (current.x + 1 < map.length) {
                cell = map[current.x + 1][current.y];
                checkAndUpdateCost(current, cell, current.finalCost++);

                if (current.y - 1 >= 0) {
                    cell = map[current.x + 1][current.y - 1];
                    checkAndUpdateCost(current, cell, current.finalCost++);
                }

                if (current.y + 1 < map[0].length) {
                    cell = map[current.x + 1][current.y + 1];
                    checkAndUpdateCost(current, cell, current.finalCost++);
                }
            }
        }
    }

    class Cell {

        int finalCost = 0;
        int x, y;
        Cell parent;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
