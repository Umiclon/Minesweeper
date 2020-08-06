package model;

import ui.Minesweeper;

//represents a game board with boxes that are either blocks or mines
public class Board {
    private Box[][] board;
    private static final double DIFFICULTY = 0.25;
    private static final int DIMENSION_X = 500;
    private static final int DIMENSION_Y = 500;
    private static final int BOX_X = 5;
    private static final int BOX_Y = 5;
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
     * EFFECTS: helper for the update method in Minesweeper ui
     */
    public void updateBoard(int i, int j) {
        if (this.getState(i, j) == 1) {
            if (i >= 1 && i < this.board.length - 1 && j >= 1 && j < this.board.length - 1) {
                this.updateBox(i, j);
            } else if (j >= 1 && j < this.board.length - 1 && i == 0) {
                this.updateTop(i, j);
            } else if (j >= 1 && j < this.board.length - 1) {
                this.updateBot(i, j);
            } else if (i >= 1 && i < this.board.length - 1 && j == 0) {
                this.updateLeft(i, j);
            } else if (i >= 1 && i < this.board.length - 1) {
                this.updateRight(i, j);
            } else {
                this.updateCorner(i, j);
            }
        } else if (this.isFlagged(i, j)) {
            Minesweeper.printFlag();
        } else {
            Minesweeper.printCoveredBox();
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateBox(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateTop(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateBot(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {this.getBox(i, j - 1), this.getBox(i, j), this.getBox(i, j + 1)},
                {block, block, block}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateLeft(int i, int j) {
        Box[][] a = new Box[][]{{block, this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateRight(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), block}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void updateCorner(int i, int j) {
        if (i == 0 && j == 0) {
            this.topLeftCorner(i, j);
        } else if (i == 0 && j == this.board.length - 1) {
            this.topRightCorner(i, j);
        } else if (i == this.board.length - 1 && j == 0) {
            this.botLeftCorner(i, j);
        } else {
            this.botRightCorner(i, j);
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void topLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, this.getBox(i + 1, j), this.getBox(i + 1, j + 1)}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void topRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {this.getBox(i + 1, j - 1), this.getBox(i + 1, j), block}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void botLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, this.getBox(i - 1, j), this.getBox(i - 1, j + 1)},
                {block, this.getBox(i, j), this.getBox(i, j + 1)},
                {block, block, block}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

    /*
     * EFFECTS: helper for the update method
     */
    public void botRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{this.getBox(i - 1, j - 1), this.getBox(i - 1, j), block},
                {this.getBox(i, j - 1), this.getBox(i, j), block},
                {block, block, block}};
        Minesweeper.printBox(this.numberOfSurroundingMines(i, j, a));
    }

}
