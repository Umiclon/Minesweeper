package ui;

import model.*;

import java.util.Scanner;

//runs the minesweeper game
public class Minesweeper {
    private static final int SIZE = 4;
    private static final double DIFFICULTY = 0.25;
    private Box[][] board;
    private final Box block = new Block();
    private Scanner input;
    int totalMines;
    int totalCovered;
    int minesFlagged;
    int mines;
    boolean play;

    /*
     * EFFECTS: runs the minesweeper game
     */
    public Minesweeper() {
        runMinesweeper();
    }

    /*
     * MODIFIES: this
     * EFFECTS: reads and processes user input
     */
    private void runMinesweeper() {
        String command;
        play = true;
        input = new Scanner(System.in);

        startMenu();

        while (play) {
            command = input.next();
            command = command.toLowerCase();

            switch (command) {
                case "start":
                    init();
                    break;
                case "r":
                    runMinesweeper();
                    break;
                case "q":
                    play = false;
                    quitMenu();
                    break;
                default:
                    runCommands(command);
                    break;
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:runs user command
     */
    private void runCommands(String command) {
        if (command.equals("f")) {
            flag();
        } else if (command.equals("s")) {
            select();
        } else {
            System.out.println();
            System.out.println("Invalid Move");
            rules();
        }
    }


    /*
     * EFFECTS: displays the initial board
     */
    private void init() {
        board = new Box[SIZE][SIZE];
        rules();
        totalMines = 0;
        totalCovered = 0;
        mines = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                double a = Math.random();
                if (a < DIFFICULTY) {
                    board[i][j] = new Mine();
                    totalMines++;
                    mines++;
                } else {
                    board[i][j] = new Block();
                }
                System.out.print("X ");
                totalCovered++;
            }
            System.out.println(" ");
        }
        System.out.println("Mines Left: " + totalMines);
        System.out.println("Covered Boxes: " + (totalCovered));
        System.out.println();
    }

    /*
     * EFFECTS: displays start message
     */
    private void startMenu() {
        System.out.println();
        System.out.println("Welcome to Minesweeper 2020");
        System.out.println();
        System.out.println("Type 'start' to begin");
        System.out.println();
    }


    /*
     * EFFECTS: runs the gameOver menu
     */
    private void gameOver() {
        printBoardSolution();
        System.out.println("GAME OVER");
        System.out.println();
        runMinesweeper();
    }

    /*
     * EFFECTS: prints out the solution for the game
     */
    private void printBoardSolution() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].gameOver();
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Number of Mines: " + totalMines);
        System.out.println();
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
     * MODIFIES: Box
     * EFFECTS: selects a box and uncovers it
     */
    private void select() {
        System.out.println();
        System.out.println("Enter x position: ");
        int x = input.nextInt();

        System.out.println();
        System.out.println("Enter y position: ");
        int y = input.nextInt();
        System.out.println();
        System.out.println("The box at " + x + ", " + y + " has been uncovered");
        System.out.println();

        selectBox(x, y);
    }

    /*
     * MODIFIES: Box
     * EFFECTS: selects a box and uncovers it
     */
    private void selectBox(int x, int y) {
        if (board[y][x].getName().equals("block") && !board[y][x].isFlagged()) {
            for (int i = y - 1; i < y + 2; i++) {
                for (int j = x - 1; j < x + 2; j++) {
                    if (i >= 0 && i <= SIZE - 1 && j >= 0 && j <= SIZE - 1 && board[i][j].getName().equals("block")
                            && !board[i][j].isFlagged() && board[i][j].getState() == 0) {
                        totalCovered--;
                        board[i][j].changeState();
                        updateBoard(i, j);
                    }
                }
                System.out.println(" ");
            }
            System.out.println();
            if (!checkWinCondition()) {
                update();
            }
        } else {
            play = false;
            gameOver();
        }
    }

    /*
     * MODIFIES: box
     * EFFECTS: flags a box as a potential mine
     */
    private void flag() {
        System.out.println();
        System.out.println("Enter x position: ");
        int x = input.nextInt();

        System.out.println();
        System.out.println("Enter y position: ");
        int y = input.nextInt();
        System.out.println();

        flagBox(x, y);

        System.out.println("The box at " + x + ", " + y + " has been flagged");
        if (!checkWinCondition()) {
            update();
        }
    }

    private void flagBox(int x, int y) {
        if (!board[y][x].isFlagged()) {
            if (board[y][x].getName().equals("mine")) {
                minesFlagged--;
            }

            board[y][x].flag();
            totalMines--;
        } else {
            if (board[y][x].getName().equals("mine")) {
                minesFlagged++;
            }

            board[y][x].flag();
            totalMines++;
        }
    }

    //EFFECTS: list of helpful rules
    public void rules() {
        System.out.println();
        System.out.println("Type 'r' to restart");
        System.out.println("Type 'q' to quit");
        System.out.println("Type 'f' to flag a box");
        System.out.println("Type 's' to select a box");
        System.out.println();
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
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Mines Left: " + totalMines);
        System.out.println("Covered Boxes: " + totalCovered);
        System.out.println();
        rules();
    }

    private void updateBoard(int i, int j) {
        //board[i][j].changeState();
        if (board[i][j].getState() == 1 && !board[i][j].isFlagged()) {
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
        } else if (board[i][j].isFlagged()) {
            System.out.print("F ");
        } else {
            System.out.print("X ");
//            totalCovered++;
//            if (board[i][j].getName().equals("mine")) {
//                totalMines++;
//            }
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

    /*
     * EFFECTS: checks whether all mines have been discovered
     */
    private boolean checkWinCondition() {
        if (minesFlagged == mines | totalCovered == totalMines) {
            winGame();
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: runs the winGame menu
     */
    private void winGame() {
        printBoardSolution();
        System.out.println("CONGRATULATIONS YOU WON!");
        System.out.println();
        runMinesweeper();
    }
}
