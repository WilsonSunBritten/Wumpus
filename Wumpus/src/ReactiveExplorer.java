
public class ReactiveExplorer extends Agent {

    private Location prevPos;
    private State curState, prevState;

    public ReactiveExplorer(World world) {
        this.world = world;
        arrowCount = world.arrowCount;
        curLoc = new Location(world.x, world.y);
        prevPos = curLoc;
        percepts = world.getPercepts();
        if (((percepts & STENCH) != STENCH) && ((percepts & BREEZE) != BREEZE)) {
            curState = State.SAFE;
            prevState = State.SAFE;
        }
        run();
    }

    private void run() {

        while (true) {
            decideNextAction();
        }
    }

    private void move(int action) {

        switch (action) {
            case World.GRAB:
                //grab gold, game ends
                world.action(action);
                break;
            case World.MOVE:
                //move forward
                percepts = world.action(action);
                if ((percepts & BUMP) != BUMP) {            //did not bump into anything
                    prevPos = this.curLoc;
                    prevState = curState;
                    updateLocation();
                } else if ((percepts & DEATH_WUMPUS) == DEATH_WUMPUS) {           //killed by a wumpus, therefore use revive potion and take revenge
                    move(World.SHOOT);
                    move(World.MOVE);
                } else if ((percepts & DEATH) == DEATH) {                 //killed by a pit
                    Location temp = curLoc;
                    curLoc = prevPos;
                    prevPos = temp;
                    prevState = State.UNSAFE;
                    curState = State.EXPLORED;
                }
                break;
            case World.TURN_LEFT:
            case World.TURN_RIGHT:
                //turn
                world.action(action);
                prevPos = curLoc;
                if (action == World.SOUTH) {
                    direction = direction.left();                          //turn left
                } else {
                    direction = direction.right();                          //turn right
                }
                break;
            case World.SHOOT:
                //shoot arrow
                world.action(action);
                arrowCount--;
                break;
            case World.QUIT:
                //should never reach here
                System.out.println("Invalid action: " + action);
                break;
            default:
                break;
        }
    }

    public void decideNextAction() {       //select safe neighboring cell else select unsafe neighboring cell

        if (((percepts & STENCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
            switch (random.nextInt(3)) {
                case 1:             //go forward
                    move(World.MOVE);
                    break;
                case 2:             //turn left and go forward
                    move(World.TURN_LEFT);
                    move(World.MOVE);
                    break;
                case 3:             //turn right and go forward
                    move(World.TURN_RIGHT);
                    move(World.MOVE);
                    break;
                default:
                    System.out.println("Should not reach this line.");
            }
        } else {                                    //neighboring cells may not be safe
            //if previous cell was safe, return and pick new direction
            if (prevState == State.SAFE) {          //there might be a situation where the agent move back and forth between 3 safe safe spaces here we might need to account for
                move(World.TURN_LEFT);
                move(World.TURN_LEFT);
                move(World.MOVE);
            } else {                                //pick random move
                switch (random.nextInt(3)) {
                    case 1:                         //go forward
                        move(World.MOVE);
                        break;
                    case 2:                         //turn left and go forward
                        move(World.TURN_LEFT);
                        move(World.MOVE);
                        break;
                    case 3:                         //turn right and go forward
                        move(World.TURN_RIGHT);
                        move(World.MOVE);
                        break;
                    default:
                        System.out.println("Should not reach this line.");
                }
            }
        }
    }
}
