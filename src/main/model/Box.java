package model;

//represents a box object on a board
//the box can either be a block or a mine
public class Box {
    protected int state;
    protected boolean flagged;
    protected boolean gameOver;
    protected String name;

    /*
     * EFFECTS: creates a box object with int state 0, booleans flagged and gameOver false, and string name "box"
     */
    public Box() {
        this.state = 0;
        this.flagged = false;
        this.gameOver = false;
        this.name = "box";
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
     * EFFECTS: changes the box state
     */
    public void changeState() {
        if (this.gameOver) {
            this.state = Block.UNCOVERED_BLOCK;
        } else if (this.state == Block.COVERED_BLOCK && isFlagged()) {
            this.state = Block.FLAGGED_BLOCK;
        } else if (this.state == Block.FLAGGED_BLOCK && !isFlagged()) {
            this.state = Block.COVERED_BLOCK;
        } else {
            this.state = Block.UNCOVERED_BLOCK;
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
     * EFFECTS: switches flagged from false to true and vice versa
     */
    public void flag() {
        this.flagged = !flagged;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true and flagged to false
     */
    public void gameOver() {
        this.gameOver = true;
        this.flagged = false;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

}
