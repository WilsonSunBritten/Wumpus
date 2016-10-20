
public abstract class Agent {

    Position curPos;

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        
        void next() {
            
        }
    };

    private void move(int action) {
    }

    public void decideNextAction() {
    }

    class Position {

        int x;
        int y;
        Direction direction;

        public Position(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

//        public int[] getLeft() {
//            switch (direction) {
//                
//            }
//            
//        }
//
//        public int[] getRight() {
//
//        }
//
//        public int[] getForward() {
//
//        }
//
//        public int[] getBehind() {
//
//        }

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
