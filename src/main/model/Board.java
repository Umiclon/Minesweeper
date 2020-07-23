package model;

import sun.applet.Main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

//creates a board and assigns random numbers to each box
public class Board {
    Box[][] board;
    private static final int SIZE = 15;
    private static final double DIFFICULTY = 0.25;
    private static final int DIMENSION_X = 500;
    private static final int DIMENSION_Y = 500;

    public Board() {
        board = new Box[SIZE][SIZE];
    }

    public void setUp() {
        fillBoard();
    }

    public boolean fillBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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


    public void mousePressed() {

    }


}
