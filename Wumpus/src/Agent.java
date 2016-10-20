
public abstract class Agent {

    Location curLoc;
    Direction direction;

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        
        private final Direction[] terms = values();
        public Direction left() {
            return terms[(this.ordinal()+1) % terms.length];
        }
        
        public Direction right() {
            return terms[(this.ordinal()-1) % terms.length];
        }
    };

    private void move(int action) {
    }

    public void decideNextAction() {
    }
    
    public void moveDidMove() {
            switch (direction) {
                case NORTH:
                    if (curLoc.y < World.size - 1) {
                        curLoc.y += 1;
                    }
                    break;
                case EAST:
                    if (curLoc.x > 0) {
                        curLoc.x -= 1;
                    }
                    break;
                case SOUTH:
                    if (curLoc.y > 0) {
                        curLoc.y--;
                    }
                    break;
                case WEST:
                    if (curLoc.x < World.size - 1) {
                        curLoc.x++;
                    }
            }
        }

    class Location {

        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void moveDidMove() {
            switch (direction) {
                case NORTH:
                    if (y < World.size - 1) {
                        y += 1;
                    }
                    break;
                case EAST:
                    if (x > 0) {
                        x -= 1;
                    }
                    break;
                case SOUTH:
                    if (y > 0) {
                        y--;
                    }
                    break;
                case WEST:
                    if (x < World.size - 1) {
                        x++;
                    }
            }
        }
    }
}
