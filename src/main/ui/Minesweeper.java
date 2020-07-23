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

    public Minesweeper() {
        runMinesweeper();
        input = new Scanner(System.in);
    }

    //EFFECTS: processes input
    private void runMinesweeper() {
        coordinate = Integer.parseInt(input.next());

        displayBoard();
        updateBoard();
        selectBox();
        flagBox();
    }

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

    private void updateBoard() {
        setUp();
        gameOver();
    }

    private void selectBox() {

    }

    private void flagBox() {

    }

    private void setUp() {

    }

    private void gameOver() {

    }
}
