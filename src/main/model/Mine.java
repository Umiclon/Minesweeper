package src.main.model;

// represents a mine block in a box on the game board
// mines will explode and end the game when uncovered
// flagged mines cannot be uncovered unless un-flagged
public class Mine extends Box {
    private static final int COVERED_MINE = 0;
    private static final int UNCOVERED_MINE = 6;
    private static final int FLAGGED_MINE = 7;
    private static final int MINE_X = 4;
    private static final int MINE_Y = 4;

    /*
     * EFFECTS: creates a mine object with int state COVERED_MINE, booleans flagged and gameOver false, and string name
     * "mine"
     */
    public Mine() {
        this.state = COVERED_MINE;
        this.flagged = false;
        this.gameOver = false;
        this.name = "mine";
    }

    public String getName() {
        return this.name;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes state depending on flagged and gameOver
     */
    public void changeState() {
        if (isGameOver()) {
            this.state = UNCOVERED_MINE;
        } else if (this.state == COVERED_MINE && !isFlagged()) {
            this.state = UNCOVERED_MINE;
        } else if (this.state == COVERED_MINE && isFlagged()) {
            this.state = FLAGGED_MINE;
            //System.out.println("mine has been flagged");
        } else if (this.state == FLAGGED_MINE && !isFlagged()) {
            this.state = COVERED_MINE;
            //System.out.println("mine has been un-flagged");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: switches flagged from false to true and vice versa and updates the state
     */
    public void flag() {
        this.flagged = !flagged;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true and updates the state
     */
    public void gameOver() {
        this.gameOver = true;
        this.flagged = false;
        this.changeState();
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

}
