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
    int totalCovered;
    boolean play;

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
        play = true;
        input = new Scanner(System.in);

        startMenu();

        while (play) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("start")) {
                init();
            } else if (command.equals("r")) {
                runMinesweeper();
            } else if (command.equals("q")) {
                play = false;
                quitMenu();
            } else {
                runCommands(command);
                update();
            }
        }
    }

    /*
     * MODIFIES:
     * EFFECTS:runs user command
     */
    private void runCommands(String command) {
        if (command.equals("f")) {
            flagBox();
        } else if (command.equals("s")) {
            selectBox();
        } else {
            System.out.println();
            System.out.println("Invalid Move");
            System.out.println();
        }

        System.out.println();
        System.out.println("Type 'r' to restart");
        System.out.println("Type 'q' to quit");
        System.out.println("Type 'f' to flag a box");
        System.out.println("Type 's' to select a box");
        System.out.println();
    }


    /*
     * EFFECTS: displays the initial board
     */
    private void init() {
        board = new Box[SIZE][SIZE];

        System.out.println();
        System.out.println("Type 'r' to restart");
        System.out.println("Type 'q' to quit");
        System.out.println("Type 'f' to flag a box");
        System.out.println("Type 's' to select a box");
        System.out.println();

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
     * EFFECTS: displays gameOver message
     */
    private void startMenu() {
        System.out.println();
        System.out.println("Welcome to Minesweeper 2020");
        System.out.println();
        System.out.println("Type Start to Begin");
        System.out.println();
    }


    /*
     * MODIFIES: game
     * EFFECTS: runs the gameOver menu
     */
    private void gameOver() {
        System.out.println();
        totalMines = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].gameOver();
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Number of Mines: " + totalMines);
        System.out.println();
        System.out.println("GAME OVER");
        System.out.println();
        runMinesweeper();
    }

    /*
     * EFFECTS: displays gameOver message
     */
    private void quitMenu() {
        System.out.println();
        System.out.println("Thank You For Playing");
        System.out.println();
        System.out.println("Bye!");
    }

    /*
     * MODIFIES: this
     * EFFECTS: selects a box and shows actions that can be done on the box
     */
    private void selectBox() {
        System.out.println();
        System.out.println("Enter x position: ");
        System.out.println();
        int x = input.nextInt();

        System.out.println();
        System.out.println("Enter y position: ");
        System.out.println();
        int y = input.nextInt();
        System.out.println();
        System.out.println("The box at " + x + ", " + y + " has been uncovered");

        if (board[y][x].getName().equals("block")) {
            for (int i = y - 1; i < y + 2; i++) {
                for (int j = x - 1; j < x + 2; j++) {
                    board[i][j].changeState();
                    updateBoard(i, j);
                }
                System.out.println(" ");
            }
        } else {
            play = false;
            gameOver();
        }
    }

    /*
     * MODIFIES: box
     * EFFECTS: allows a box to be flagged
     */
    private void flagBox() {
        System.out.println();
        System.out.println("Enter x position: ");
        System.out.println();
        int x = input.nextInt();

        System.out.println();
        System.out.println("Enter y position: ");
        System.out.println();
        int y = input.nextInt();
        System.out.println();

        board[y][x].flag();
        System.out.println("The box at " + x + ", " + y + " has been flagged");
    }

    /*
     * EFFECTS: displays the boxes on the board
     */
    private void displayBoard() {

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
        System.out.println("Mines Left: " + totalMines);
        System.out.println();
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
            totalCovered++;
            if (board[i][j].getName().equals("mine")) {
                totalMines++;
            }
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
