package ui;

import model.*;

import java.util.Scanner;

//runs the minesweeper game
public class Minesweeper {
    private static final int SIZE = 15;
    private static final double DIFFICULTY = 0.25;
    private Box[][] board;
    private Box block = new Block();
    private Scanner input;
    int totalMines;

    /*
     * MODIFIES: this
     * EFFECTS: constructor
     */
    public Minesweeper() {
        runMinesweeper();
    }

    /*
     * MODIFIES: this
     * EFFECTS: reads user input
     */
    private void runMinesweeper() {
        int coordinate = 0;
        String command = "";
        boolean play = true;
        input = new Scanner(System.in);

        init();

        while (play) {
            displayBoard();
            command = input.next();

            if (command.equals("r")) {
                runMinesweeper();
            } else if (command.equals("q")) {
                play = false;
                gameOver();
            } else {
                runCommands(command);
                update();
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:runs user command
     */
    private void runCommands(String command) {
        if (command.equals("f")) {
            flagBox();
        } else if (command.equals("s")) {
            selectBox();
        } else {
            System.out.println("Invalid Move");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: selects a box and shows actions that can be done on the box
     */
    private void selectBox() {
        System.out.println("Enter x position: ");
        int x = input.nextInt();

        System.out.println("Enter y position: ");
        int y = input.nextInt();

        for (int i = y - 1; i < y + 2; i++) {
            for (int j = x - 1; j < x + 2; j++) {
                board[i][j].changeState();
                updateBoard(i, j);
            }
            System.out.println(" ");
        }


        System.out.println("The box at " + x + ", " + y + " has been uncovered");
    }

    /*
     * MODIFIES: box
     * EFFECTS: allows a box to be flagged
     */
    private void flagBox() {
        System.out.println("Enter x position: ");
        int x = input.nextInt();

        System.out.println("Enter y position: ");
        int y = input.nextInt();

        board[y][x].flag();
        System.out.println("The box at " + x + ", " + y + " has been flagged");
    }

    /*
     * EFFECTS: displays the initial board
     */
    private void init() {
        board = new Box[SIZE][SIZE];

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


        System.out.println();
    }

    /*
     * MODIFIES: game
     * EFFECTS: runs the gameOver menu
     */
    private void gameOver() {
        totalMines = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Number of Mines: " + totalMines);
    }

    /*
     * EFFECTS: displays the boxes on the board
     */
    private void displayBoard() {
        //update();
        //gameOver();
    }

    /*
     * MODIFIES: game board
     * EFFECTS: updates the board display
     */
    private void update() {
        totalMines = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Number of Mines: " + totalMines);
    }

    private void updateBoard(int i, int j) {
        //board[i][j].changeState();
        if (board[i][j].getState() == 1) {
            if (i >= 1 && i < SIZE - 1 && j >= 1 && j < SIZE - 1) {
                updateBox(i, j);
            } else if (j >= 1 && j < SIZE - 1 && i == 0) {
                updateTop(i, j);
            } else if (j >= 1 && j < SIZE - 1) {
                updateBot(i, j);
            } else if (i >= 1 && i < SIZE - 1 && j == 0) {
                updateLeft(i, j);
            } else if (i >= 1 && i < SIZE - 1) {
                updateRight(i, j);
            } else {
                updateCorner(i, j);
            }
        } else {
            System.out.print("0 ");
            totalMines++;
        }
    }

    private void updateBox(int i, int j) {
        Box[][] a = new Box[][]{{board[i - 1][j - 1], board[i - 1][j], board[i - 1][j + 1]},
                {board[i][j - 1], board[i][j], board[i][j + 1]},
                {board[i + 1][j - 1], board[i + 1][j], board[i + 1][j + 1]}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void updateTop(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {board[i][j - 1], board[i][j], board[i][j + 1]},
                {board[i + 1][j - 1], board[i + 1][j], board[i + 1][j + 1]}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void updateBot(int i, int j) {
        Box[][] a = new Box[][]{{board[i - 1][j - 1], board[i - 1][j], board[i - 1][j + 1]},
                {board[i][j - 1], board[i][j], board[i][j + 1]},
                {block, block, block}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void updateLeft(int i, int j) {
        Box[][] a = new Box[][]{{block, board[i - 1][j], board[i - 1][j + 1]},
                {block, board[i][j], board[i][j + 1]},
                {block, board[i + 1][j], board[i + 1][j + 1]}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void updateRight(int i, int j) {
        Box[][] a = new Box[][]{{board[i - 1][j - 1], board[i - 1][j], block},
                {board[i][j - 1], board[i][j], block},
                {board[i + 1][j - 1], board[i + 1][j], block}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void updateCorner(int i, int j) {
        if (i == 0 && j == 0) {
            topLeftCorner(i, j);
        } else if (i == 0 && j == SIZE - 1) {
            topRightCorner(i, j);
        } else if (i == SIZE - 1 && j == 0) {
            botLeftCorner(i, j);
        } else {
            botRightCorner(i, j);
        }
    }

    private void topLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {block, board[i][j], board[i][j + 1]},
                {block, board[i + 1][j], board[i + 1][j + 1]}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void topRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {board[i][j - 1], board[i][j], block},
                {board[i + 1][j - 1], board[i + 1][j], block}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void botLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, board[i - 1][j], board[i - 1][j + 1]},
                {block, board[i][j], board[i][j + 1]},
                {block, block, block}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

    private void botRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{board[i - 1][j - 1], board[i - 1][j], block},
                {board[i][j - 1], board[i][j], block},
                {block, block, block}};
        System.out.print(board[i][j].numberOfSurroundingMines(a) + " ");
    }

}
