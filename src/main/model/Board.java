package model;

import ui.*;

//represents a game board with boxes that are either blocks or mines
public class Board {
    private Box[][] board;
    public static final double DIFFICULTY = 0.35;
    public static final int WIDTH = 1750;
    public static final int HEIGHT = 1750;
    private int totalMines;
    private int totalCovered;
    private int minesFlagged;
    private int mines;
    private final Box block = new Block();

    /*
     * EFFECTS: creates a box array of size SIZE, sets counters to 0
     */
    public Board(int size) {
        this.board = new Box[size][size];
        this.totalCovered = 0;
        this.totalMines = 0;
        this.mines = 0;
        this.minesFlagged = 0;
        fillBoard();
    }

    /*
     * MODIFIES: this
     * EFFECTS: fills the board with blocks and mines
     */
    public boolean fillBoard() {
        for (int i = 0; i < this.board[0].length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                double a = Math.random();
                if (a < DIFFICULTY) {
                    board[i][j] = new Mine();
                    totalMines++;
                    mines++;
                } else {
                    board[i][j] = new Block();
                }
                totalCovered++;
            }
        }
        return true;
    }

    public Box[][] getBoard() {
        return this.board;
    }

    public int getTotalMines() {
        return this.totalMines;
    }

    public int getTotalCovered() {
        return this.totalCovered;
    }

    public int getMinesFlagged() {
        return this.minesFlagged;
    }

    public int getMines() {
        return this.mines;
    }

    public Box getBox(int i, int j) {
        return board[i][j];
    }

    public void setTotalMines(int i) {
        this.totalMines = i;
    }

    public void setTotalCovered(int i) {
        this.totalCovered = i;
    }

    public void setMinesFlagged(int i) {
        this.minesFlagged = i;
    }

    public void setMines(int i) {
        this.mines = i;
    }

    public void setBox(int i, int j, Box box) {
        board[i][j] = box;
    }

    public String getName(int i, int j) {
        return board[i][j].getName();
    }

    public int getState(int i, int j) {
        return board[i][j].getState();
    }

    /*
     * MODIFIES: this, Box
     * EFFECTS: changes state depending on flagged and gameOver
     */
    public void changeState(int i, int j) {
        board[i][j].changeState();
    }

    /*
     * REQUIRES: board = new Box[3][3]
     * EFFECTS: returns the number of mines in a 3x3 area on the board
     */
    public int numberOfSurroundingMines(int i, int j, Box[][] board) {
        return this.board[i][j].numberOfSurroundingMines(board);
    }

    /*
     * MODIFIES: this, Box
     * EFFECTS: switches flagged from false to true and vice versa and updates the state
     */
    public void flag(int i, int j) {
        board[i][j].flag();
    }

    /*
     * MODIFIES: this, Box
     * EFFECTS: sets gameOver to true and updates the state
     */
    public void gameOver(int i, int j) {
        board[i][j].gameOver();
    }

    public boolean isFlagged(int i, int j) {
        return board[i][j].isFlagged();
    }

    public boolean isGameOver(int i, int j) {
        return board[i][j].isGameOver();
    }

    /*
     * EFFECTS: returns number that should be shown on the uncovered box as a string
     */
    public String updateBoard(int i, int j) {
        if (this.getState(i, j) == 1) {
            if (i >= 1 && i < this.board.length - 1 && j >= 1 && j < this.board.length - 1) {
                return this.updateBox(i, j);
            } else if (j >= 1 && j < this.board.length - 1 && i == 0) {
                return this.updateTop(i, j);
            } else if (j >= 1 && j < this.board.length - 1) {
                return this.updateBot(i, j);
            } else if (i >= 1 && i < this.board.length - 1 && j == 0) {
                return this.updateLeft(i, j);
            } else if (i >= 1 && i < this.board.length - 1) {
                return this.updateRight(i, j);
            } else {
                return this.updateCorner(i, j);
            }
        } else if (this.isFlagged(i, j)) {
            return "F";
        } else {
            return "X";
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateBox(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateTop(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateBot(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {block, block, block}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateLeft(int i, int j) {
        Box[][] a = new Box[][]{{block, this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateRight(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), block}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String updateCorner(int i, int j) {
        if (i == 0 && j == 0) {
            return this.topLeftCorner(i, j);
        } else if (i == 0 && j == this.board.length - 1) {
            return this.topRightCorner(i, j);
        } else if (i == this.board.length - 1 && j == 0) {
            return this.botLeftCorner(i, j);
        } else {
            return this.botRightCorner(i, j);
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String topLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String topRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), block}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String botLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, block, block}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public String botRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {block, block, block}};
        return Integer.toString(this.numberOfSurroundingMines(i, j, a));
    }

}
