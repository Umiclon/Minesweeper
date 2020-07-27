package model;

import sun.applet.Main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

//represents a game board with boxes that are either blocks or mines
public class Board {
    public Box[][] board;
    private static final double DIFFICULTY = 0.25;
    private static final int DIMENSION_X = 500;
    private static final int DIMENSION_Y = 500;
    public int totalMines;
    public int totalCovered;
    public int minesFlagged;
    public int mines;

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


}
