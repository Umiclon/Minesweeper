package model;

// represents a mine block in a box on the game board
public class Mine extends Box {
    private int state;
    private static final int COVERED_MINE = 0;
    private static final int UNCOVERED_MINE = 6;
    private static final int FLAGGED_MINE = 7;
    private static final int MINE_X = 4;
    private static final int MINE_Y = 4;
    boolean flagged;
    boolean gameOver;
    String name;

    /*
     * EFFECTS: creates a mine object
     */
    public Mine() {
        state = COVERED_MINE;
        flagged = false;
        gameOver = false;
        this.name = "mine";
    }

    public String getName() {
        return this.name;
    }

    public int getState() {
        return this.state;
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes state depending on flagged and gameOver
     */
    public void changeState() {
        if (isGameOver()) {
            this.state = UNCOVERED_MINE;
        } else if (state == COVERED_MINE && !isFlagged()) {
            this.state = UNCOVERED_MINE;
        } else if (state == COVERED_MINE && isFlagged()) {
            state = FLAGGED_MINE;
            //System.out.println("mine has been flagged");
        } else if (state == FLAGGED_MINE && !isFlagged()) {
            this.state = COVERED_MINE;
            //System.out.println("mine has been un-flagged");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: switches flagged from false to true and vice versa
     */
    public void flag() {
        this.flagged = !flagged;
        changeState();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true
     */
    public void gameOver() {
        this.gameOver = true;
        changeState();
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

}
