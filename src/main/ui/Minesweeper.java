package ui;

import model.*;
import java.util.Scanner;

//runs the minesweeper game
public class Minesweeper {
    private static final int SIZE = 15;
    private static final double DIFFICULTY = 0.25;
    private Box [][] board;
    private Scanner input;
    int coordinate;
    String command;

    /*
     * MODIFIES: this
     * EFFECTS: reads user input
     */
    public Minesweeper() {
        runMinesweeper();
        input = new Scanner(System.in);
    }

    /*
     * MODIFIES: this
     * EFFECTS: reads user input
     */
    private void runMinesweeper() {
        //command = input.next();
        //coordinate = Integer.parseInt(command);

        displayBoard();
        updateBoard();
        selectBox();
        flagBox();
    }

    /*
     * EFFECTS: displays the boxes on the board
     */
    private void displayBoard() {
        board = new Box [SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                double a = Math.random();
                if (a < DIFFICULTY) {
                    board[i][j] = new Mine();
                } else {
                    board[i][j] = new Block();
                }
                System.out.print(board[i][j].getState() + " ");
            }
            System.out.println(" ");
        }
    }

    /*
     * MODIFIES: game board
     * EFFECTS: updates the board display
     */
    private void updateBoard() {
        setUp();
        gameOver();
    }

    /*
     * MODIFIES: this
     * EFFECTS: selects a box and shows actions that can be done on the box
     */
    private void selectBox() {

    }

    /*
     * MODIFIES: box
     * EFFECTS: allows a box to be flagged
     */
    private void flagBox() {

    }

    /*
     * MODIFIES: game
     * EFFECTS: initializes the game
     */
    private void setUp() {

    }

    /*
     * MODIFIES: game
     * EFFECTS: runs the gameOver menu
     */
    private void gameOver() {

    }
}
