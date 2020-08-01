package src.main.model;

import sun.applet.Main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

//represents a game board with boxes that are either blocks or mines
public class Board {
    private Box[][] board;
    private static final double DIFFICULTY = 0.25;
    private static final int DIMENSION_X = 500;
    private static final int DIMENSION_Y = 500;
    private int totalMines;
    private int totalCovered;
    private int minesFlagged;
    private int mines;

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
     * EFFECTS: sets up the board
     */
//    public void setUp() {
//        fillBoard();
//    }

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

    /*
     * MODIFIES: Box
     * EFFECTS: changes the state of the box that was pressed on
     */
//    public void mousePressed() {
//
//    }

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

    public Box getBox(int i, int j) {
        return board[i][j];
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
        return board[i][j].numberOfSurroundingMines(board);
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
}
