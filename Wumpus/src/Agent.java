
public abstract class Agent {

    Position curPos;

    private void move(int action) {
    }

    public void decideNextAction() {
    }

    class Position {

        int x;
        int y;
        int direction;

        public Position(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public void moveDidMove() {
            switch (direction) {
                case World.NORTH:
                    if (y < World.size - 1) {
                        y += 1;
                    }
                    break;
                case World.EAST:
                    if (x > 0) {
                        x -= 1;
                    }
                    break;
                case World.SOUTH:
                    if (y > 0) {
                        y--;
                    }
                    break;
                case World.WEST:
                    if (x < World.size - 1) {
                        x++;
                    }
            }
        }
    }
}
