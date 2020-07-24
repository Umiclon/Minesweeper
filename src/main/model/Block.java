package model;

// represents a block object in a box on the game board
// changes state from covered to uncovered or flagged
// blocks can indicate the number of mines around it
// flagged blocks cannot be uncovered unless un-flagged
public class Block extends Box {
    private int state;
    private static final int COVERED_BLOCK = 0;
    private static final int UNCOVERED_BLOCK = 1;
    private static final int FLAGGED_BLOCK = 2;
    private static final int BLOCK_X = 5;
    private static final int BLOCK_Y = 5;
    boolean flagged;
    boolean gameOver;
    String name;

    public Block() {
        state = COVERED_BLOCK;
        flagged = false;
        gameOver = false;
        this.name = "block";
    }

    public String getName() {
        return this.name;
    }

    public int getState() {
        return this.state;
    }

    public void changeState() {
        if (isGameOver()) {
            this.state = UNCOVERED_BLOCK;
        } else if (state == COVERED_BLOCK && !isFlagged()) {
            this.state = UNCOVERED_BLOCK;
        } else if (state == COVERED_BLOCK && isFlagged()) {
            state = FLAGGED_BLOCK;
        } else if (state == FLAGGED_BLOCK && !isFlagged()) {
            this.state = COVERED_BLOCK;
        }
    }


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

    public void flag() {
        this.flagged = !flagged;
        changeState();
    }

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
