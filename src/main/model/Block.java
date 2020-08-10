package model;

// represents a block object in a box on the game board
// blocks will indicate the number of mines around it when uncovered
// flagged blocks cannot be uncovered unless un-flagged
public class Block extends Box {
    public static final int COVERED_BLOCK = 0;
    public static final int UNCOVERED_BLOCK = 1;
    public static final int FLAGGED_BLOCK = 2;

    /*
     * EFFECTS: creates a block object with int state COVERED_BLOCK, booleans flagged and gameOver false, and string
     * name "block"
     */
    public Block() {
        super();
        this.state = COVERED_BLOCK;
        this.name = "block";
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes state depending on flagged and gameOver
     */
    @Override
    public void changeState() {
        if (this.gameOver) {
            this.state = UNCOVERED_BLOCK;
        } else if (this.state == COVERED_BLOCK && isFlagged()) {
            this.state = FLAGGED_BLOCK;
        } else if (this.state == FLAGGED_BLOCK && !isFlagged()) {
            this.state = COVERED_BLOCK;
        } else {
            this.state = UNCOVERED_BLOCK;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true, flagged to false, and updates the state
     */
    @Override
    public void gameOver() {
        super.gameOver();
        this.changeState();
    }

}
