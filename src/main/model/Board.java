package model;

import sun.applet.Main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

//creates a board and assigns a block or a mine to each box
public class Board {
    Box[][] board;
    private static final int SIZE = 15;
    private static final double DIFFICULTY = 0.25;
    private static final int DIMENSION_X = 500;
    private static final int DIMENSION_Y = 500;

    /*
     * MODIFIES: this
     * EFFECTS:
     */
    public Board() {
        board = new Box[SIZE][SIZE];
    }

    /*
     * MODIFIES: this
     * EFFECTS:
     */
    public void setUp() {
        fillBoard();
    }

    /*
     * MODIFIES: this
     * EFFECTS:
     */
    public boolean fillBoard() {
        for (int i = 0; i < this.board[0].length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                double a = Math.random();
                if (a < DIFFICULTY) {
                    board[i][j] = new Mine();
                } else {
                    board[i][j] = new Block();
                }
            }
        }
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS:
     */
    public void mousePressed() {

    }


}
