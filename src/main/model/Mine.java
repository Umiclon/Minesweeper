package model;

// represents a mine block in a box on the game board
// mines will explode and end the game when uncovered
// flagged mines cannot be uncovered unless un-flagged
public class Mine extends Box {
    private static final int COVERED_MINE = 0;
    private static final int UNCOVERED_MINE = 6;
    private static final int FLAGGED_MINE = 7;

    /*
     * EFFECTS: creates a mine object with int state COVERED_MINE, booleans flagged and gameOver false, and string name
     * "mine"
     */
    public Mine() {
        super();
        this.state = COVERED_MINE;
        this.name = "mine";
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes state depending on flagged and gameOver
     */
    @Override
    public void changeState() {
        if (super.isGameOver()) {
            this.state = UNCOVERED_MINE;
        } else if (this.state == COVERED_MINE && isFlagged()) {
            this.state = FLAGGED_MINE;
        } else if (this.state == FLAGGED_MINE && !isFlagged()) {
            this.state = COVERED_MINE;
        } else {
            this.state = UNCOVERED_MINE;
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
