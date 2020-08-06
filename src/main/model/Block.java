package model;

// represents a block object in a box on the game board
// blocks will indicate the number of mines around it when uncovered
// flagged blocks cannot be uncovered unless un-flagged
public class Block extends Box {
    private static final int COVERED_BLOCK = 0;
    private static final int UNCOVERED_BLOCK = 1;
    private static final int FLAGGED_BLOCK = 2;

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
        if (super.isGameOver()) {
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
     * REQUIRES: board = new Box[3][3]
     * EFFECTS: returns the number of mines in a 3x3 area on the board
     */
    @Override
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
     * EFFECTS: sets gameOver to true, flagged to false, and updates the state
     */
    @Override
    public void gameOver() {
        super.gameOver();
        this.changeState();
    }

}
