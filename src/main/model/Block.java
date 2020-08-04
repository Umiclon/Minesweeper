package model;

// represents a block object in a box on the game board
// blocks will indicate the number of mines around it when uncovered
// flagged blocks cannot be uncovered unless un-flagged
public class Block extends Box {
    private static final int COVERED_BLOCK = 0;
    private static final int UNCOVERED_BLOCK = 1;
    private static final int FLAGGED_BLOCK = 2;
    private static final int BLOCK_X = 5;
    private static final int BLOCK_Y = 5;

    /*
     * EFFECTS: creates a block object with int state COVERED_BLOCK, booleans flagged and gameOver false, and string
     * name "block"
     */
    public Block() {
        this.state = COVERED_BLOCK;
        this.flagged = false;
        this.gameOver = false;
        this.name = "block";
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
            this.state = UNCOVERED_BLOCK;
        } else if (this.state == COVERED_BLOCK && !isFlagged()) {
            this.state = UNCOVERED_BLOCK;
        } else if (this.state == COVERED_BLOCK && isFlagged()) {
            this.state = FLAGGED_BLOCK;
        } else if (this.state == FLAGGED_BLOCK && !isFlagged()) {
            this.state = COVERED_BLOCK;
        }
    }

    /*
     * REQUIRES: board = new Box[3][3]
     * EFFECTS: returns the number of mines in a 3x3 area on the board
     */
    public int numberOfSurroundingMines(Box[][] board) {
        int sum = 0;

        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getName().equals("mine")) {
                    sum++;
                }
            }
        }
        return sum;
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
