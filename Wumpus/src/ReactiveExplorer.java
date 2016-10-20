
public class ReactiveExplorer extends Agent {

    private Location prevPos;
    private State curState, prevState;

    public ReactiveExplorer(World world) {
        super(world);
        prevPos = location;
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
            case GRAB:
                world.action(GRAB);
                break;
            case MOVE:
                percepts = world.action(MOVE);
                if ((percepts & BUMP) != BUMP) {                            //did not bump into anything
                    prevPos = location;
                    prevState = curState;
                    updateLocation();
                } else if ((percepts & DEATH_WUMPUS) == DEATH_WUMPUS) {     //killed by a wumpus, therefore use revive potion and take revenge
                    move(SHOOT);
                    move(MOVE);
                } else if ((percepts & DEATH) == DEATH) {                   //killed by a pit
                    Location temp = location;
                    location = prevPos;
                    prevPos = temp;
                    prevState = State.UNSAFE;
                    curState = State.EXPLORED;
                }
                break;
            case TURN_LEFT:
                world.action(TURN_LEFT);
                System.out.println("direction: " + direction);
                turnLeft();
                break;
            case TURN_RIGHT:
                world.action(TURN_RIGHT);
                turnRight();
                break;
            case SHOOT:
                world.action(SHOOT);
                arrowCount--;
                break;
            case QUIT:
                world.action(QUIT);
                break;
        }
    }

    public void decideNextAction() {    //select safe neighboring cell else select unsafe neighboring cell

        if (((percepts & STENCH) != 0) && ((percepts & BREEZE) != 0)) {        //all adjacent spaces are safe
            int rand = random.nextInt(3);
            switch (rand) {
                case 1:                 //go forward
                    move(MOVE);
                    break;
                case 2:                 //turn left and go forward
                    move(TURN_LEFT);
                    move(MOVE);
                    break;
                case 3:                 //turn right and go forward
                    move(TURN_RIGHT);
                    move(MOVE);
                    break;
                default:
                    System.out.println("Invalid case: random action, reactive explorer (safe) rand = " + rand);
            }
        } else {                                    //neighboring cells may not be safe

            if (prevState == State.SAFE) {          //there might be a situation where the agent move back and forth between 3 safe safe spaces here we might need to account for
                move(TURN_LEFT);
                move(TURN_LEFT);
                move(MOVE);
            } else {                                //pick random move
                int rand = random.nextInt(3);
                switch (random.nextInt(3)) {
                    case 1:                         //go forward
                        move(MOVE);
                        break;
                    case 2:                         //turn left and go forward
                        move(TURN_LEFT);
                        move(MOVE);
                        break;
                    case 3:                         //turn right and go forward
                        move(TURN_RIGHT);
                        move(MOVE);
                        break;
                    default:
                    System.out.println("Invalid case: random action, reactive explorer (unsafe) rand = " + rand);
                }
            }
        }
    }
}
