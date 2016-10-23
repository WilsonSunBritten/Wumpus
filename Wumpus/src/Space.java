
public class Space {

    private boolean hasWumpus = false;
    private boolean hasHole = false;
    private boolean hasObstacle = false;
    private boolean hasGold = false;
    private boolean start;
    private boolean filled = false;

    public Space() {

    }

    public boolean isHasWumpus() {
        return hasWumpus;
    }

    public void toggleWumpus() {
        
        if (hasWumpus == true) {
            hasWumpus = false;
            filled = false;
        } else {
            hasWumpus = true;
            filled = true;
        }
    }

    public boolean isHasHole() {
        return hasHole;
    }

    public void setHasHole(boolean hasHole) {
        this.filled = true;
        this.hasHole = hasHole;
    }

    public boolean isHasObstacle() {
        return hasObstacle;
    }

    public void setHasObstacle(boolean hasObstacle) {
        this.filled = true;
        this.hasObstacle = hasObstacle;
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.filled = true;
        this.hasGold = hasGold;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
