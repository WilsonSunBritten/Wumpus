import java.util.Random;
/*
So im not certain what he wants for this, the project description says

"You should also create a simple reactive explorer that does not use the
reasoning system. Instead, it makes a decision which cell to enter at
random based on whether or not it believes the neighboring cell is safe.
So it selects first from safe neighboring cells, next from unsafe
neighboring cells. Record the same statistics for this reactive explorer"

If he doesn't have access to any sort of kb or state information, how does he
differentiate between safe and unsafe spaces.  He can only determine that all the
spaces around him are safe, or all are unsafe.
 */


public class ReactiveExporer {

    private World world;
    private int arrowCount;
    private int[] previousSpace;

    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte DEATH_BY_WUMPUS = 0b00010000;
    private final byte DEATH_BY_PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;

    public ReactiveExporer(World world) {
        this.world = world;
        this.arrowCount = world.arrowCount;
    }

    public void decideAction(byte percepts) {

        //select safe neighboring cell else select unsafe neighboring cell
        if (((percepts & STENTCH) != 0) && ((percepts & BREEZE) != 0)) {
            //all adjacent spaces are safe
            Random random = new Random();
            switch (random.nextInt(3)) {
                case 1:     //go forward
                    world.action(1);
                    break;
                case 2:     //turn left and go forward
                    world.action(2);
                    world.action(1);
                    break;
                case 3:     //turn right and go forward
                    world.action(3);
                    world.action(1);
                    break;
                case 4:     //turn around and go forward
                    world.action(3);
                    world.action(3);
                    world.action(1);
                    break;
            }
        } else {    //neighboring cells may not be safe
            //return to previous space, maybe?
        }
    }

}
