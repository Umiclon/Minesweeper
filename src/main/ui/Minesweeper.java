//NOTE: the methods runMinesweeper and runCommands are based on this repository:
//https://github.students.cs.ubc.ca/CPSC210/TellerApp

package ui;

import model.*;

import java.util.Scanner;

//represents the minesweeper game
public class Minesweeper {
    private Board puzzle;
    private final ScoreBoard sb = new ScoreBoard();
    private final Box block = new Block();
    private Scanner input;
    private boolean play;
    private int size;
    private long start;

    /*
     * EFFECTS: runs the minesweeper game
     */
    public Minesweeper() {
        runMinesweeper();
    }

    /*
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
     * EFFECTS: runs user command
     */
    private void runCommands(String command) {
        if (command.equals("f")) {
            flag();
        } else if (command.equals("s")) {
            select();
        } else {
            System.out.println("\nInvalid Move");
            commandList();
        }
    }


    /*
     * MODIFIES: board
     * EFFECTS: displays the initial board
     */
    private void init() {
        System.out.println("\nEnter board size(>= 3): \n");

        size = input.nextInt();
        puzzle = new Board(size);
        commandList();
        puzzle.totalMines = 0;
        puzzle.totalCovered = 0;
        puzzle.mines = 0;

        puzzle.fillBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("X ");
                puzzle.totalCovered++;
            }
            System.out.println(" ");
        }
        System.out.println("Mines Left: " + puzzle.totalMines);
        System.out.println("Covered Boxes: " + puzzle.totalCovered + "\n");
        start = System.nanoTime();
    }

    /*
     * EFFECTS: displays start message
     */
    private void startMenu() {
        System.out.println("\nWelcome to Minesweeper 2020 \n" + "\nType 'start' to begin \n");
    }


    /*
     * EFFECTS: runs the gameOver menu and resets the game
     */
    private void gameOver() {
        printBoardSolution();
        System.out.println("GAME OVER \n");
        runMinesweeper();
    }

    /*
     * EFFECTS: prints out the solution for the game
     */
    private void printBoardSolution() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzle.board[i][j].gameOver();
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Number of Mines: " + puzzle.totalMines + "\n");
    }

    /*
     * EFFECTS: displays quit game message
     */
    private void quitMenu() {
        System.out.println("\nThank You For Playing \n" + "\nBye!");
    }

    /*
     * MODIFIES: Box
     * EFFECTS: selects a box and uncovers it
     */
    private void select() {
        System.out.println("\nEnter x position: ");
        int x = input.nextInt();

        System.out.println("\nEnter y position: ");
        int y = input.nextInt();
        System.out.println("\nThe box at " + x + ", " + y + " has been uncovered \n ");

        selectBox(x, y);
    }

    /*
     * MODIFIES: Box
     * EFFECTS: selects a box and uncovers it
     */
    private void selectBox(int x, int y) {
        if (puzzle.board[y][x].getName().equals("block") && !puzzle.board[y][x].isFlagged()) {
            for (int i = y - 1; i < y + 2; i++) {
                for (int j = x - 1; j < x + 2; j++) {
                    if (i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1
                            && puzzle.board[i][j].getName().equals("block")
                            && !puzzle.board[i][j].isFlagged() && puzzle.board[i][j].getState() == 0) {
                        puzzle.totalCovered--;
                        puzzle.board[i][j].changeState();
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
     * MODIFIES: Box
     * EFFECTS: flags a box as a potential mine
     */
    private void flag() {
        System.out.println("\nEnter x position: ");
        int x = input.nextInt();

        System.out.println("\nEnter y position: ");
        int y = input.nextInt();

        flagBox(x, y);

        System.out.println("\nThe box at " + x + ", " + y + " has been flagged");
        if (!checkWinCondition()) {
            update();
        }
    }

    /*
     * MODIFIES: Box
     * EFFECTS: flags a box as a potential mine
     */
    private void flagBox(int x, int y) {
        if (!puzzle.board[y][x].isFlagged()) {
            if (puzzle.board[y][x].getName().equals("mine")) {
                puzzle.minesFlagged++;
                puzzle.totalMines--;
            }

        } else {
            if (puzzle.board[y][x].getName().equals("mine")) {
                puzzle.minesFlagged--;
                puzzle.totalMines++;
            }

        }
        puzzle.board[y][x].flag();
    }

    /*
     * EFFECTS: displays the list of available commands
     */
    public void commandList() {
        System.out.println("\nType 'r' to restart");
        System.out.println("Type 'q' to quit");
        System.out.println("Type 'f' to flag a box");
        System.out.println("Type 's' to select a box \n");
    }

    /*
     * MODIFIES: Board
     * EFFECTS: updates the game board
     */
    private void update() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                updateBoard(i, j);
            }
            System.out.println(" ");
        }
        System.out.println("Mines Left: " + puzzle.totalMines);
        System.out.println("Covered Boxes: " + puzzle.totalCovered + "\n");
        commandList();
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateBoard(int i, int j) {
        //board[i][j].changeState();
        if (puzzle.board[i][j].getState() == 1 && !puzzle.board[i][j].isFlagged()) {
            if (i >= 1 && i < size - 1 && j >= 1 && j < size - 1) {
                updateBox(i, j);
            } else if (j >= 1 && j < size - 1 && i == 0) {
                updateTop(i, j);
            } else if (j >= 1 && j < size - 1) {
                updateBot(i, j);
            } else if (i >= 1 && i < size - 1 && j == 0) {
                updateLeft(i, j);
            } else if (i >= 1 && i < size - 1) {
                updateRight(i, j);
            } else {
                updateCorner(i, j);
            }
        } else if (puzzle.board[i][j].isFlagged()) {
            System.out.print("F ");
        } else {
            System.out.print("X ");
//            totalCovered++;
//            if (board[i][j].getName().equals("mine")) {
//                totalMines++;
//            }
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateBox(int i, int j) {
        Box[][] a = new Box[][]{{puzzle.board[i - 1][j - 1], puzzle.board[i - 1][j], puzzle.board[i - 1][j + 1]},
                {puzzle.board[i][j - 1], puzzle.board[i][j], puzzle.board[i][j + 1]},
                {puzzle.board[i + 1][j - 1], puzzle.board[i + 1][j], puzzle.board[i + 1][j + 1]}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateTop(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {puzzle.board[i][j - 1], puzzle.board[i][j], puzzle.board[i][j + 1]},
                {puzzle.board[i + 1][j - 1], puzzle.board[i + 1][j], puzzle.board[i + 1][j + 1]}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateBot(int i, int j) {
        Box[][] a = new Box[][]{{puzzle.board[i - 1][j - 1], puzzle.board[i - 1][j], puzzle.board[i - 1][j + 1]},
                {puzzle.board[i][j - 1], puzzle.board[i][j], puzzle.board[i][j + 1]},
                {block, block, block}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateLeft(int i, int j) {
        Box[][] a = new Box[][]{{block, puzzle.board[i - 1][j], puzzle.board[i - 1][j + 1]},
                {block, puzzle.board[i][j], puzzle.board[i][j + 1]},
                {block, puzzle.board[i + 1][j], puzzle.board[i + 1][j + 1]}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateRight(int i, int j) {
        Box[][] a = new Box[][]{{puzzle.board[i - 1][j - 1], puzzle.board[i - 1][j], block},
                {puzzle.board[i][j - 1], puzzle.board[i][j], block},
                {puzzle.board[i + 1][j - 1], puzzle.board[i + 1][j], block}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void updateCorner(int i, int j) {
        if (i == 0 && j == 0) {
            topLeftCorner(i, j);
        } else if (i == 0 && j == size - 1) {
            topRightCorner(i, j);
        } else if (i == size - 1 && j == 0) {
            botLeftCorner(i, j);
        } else {
            botRightCorner(i, j);
        }
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void topLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {block, puzzle.board[i][j], puzzle.board[i][j + 1]},
                {block, puzzle.board[i + 1][j], puzzle.board[i + 1][j + 1]}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void topRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, block, block},
                {puzzle.board[i][j - 1], puzzle.board[i][j], block},
                {puzzle.board[i + 1][j - 1], puzzle.board[i + 1][j], block}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void botLeftCorner(int i, int j) {
        Box[][] a = new Box[][]{{block, puzzle.board[i - 1][j], puzzle.board[i - 1][j + 1]},
                {block, puzzle.board[i][j], puzzle.board[i][j + 1]},
                {block, block, block}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: helper for the update method
     */
    private void botRightCorner(int i, int j) {
        Box[][] a = new Box[][]{{puzzle.board[i - 1][j - 1], puzzle.board[i - 1][j], block},
                {puzzle.board[i][j - 1], puzzle.board[i][j], block},
                {block, block, block}};
        System.out.print(puzzle.board[i][j].numberOfSurroundingMines(a) + " ");
    }

    /*
     * EFFECTS: checks whether all mines have been discovered
     */
    private boolean checkWinCondition() {
        if (puzzle.minesFlagged == puzzle.mines | puzzle.totalCovered == puzzle.mines) {
            winGame();
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: ScoreBoard
     * EFFECTS: runs the winGame menu and adds name and score to scoreboard
     */
    private void winGame() {
        printBoardSolution();
        System.out.println("CONGRATULATIONS YOU WON! \n");
        System.out.println("\nEnter your name: \n");
        input = new Scanner(System.in);
        String playerName = input.nextLine();
        double score = 100 * puzzle.totalMines / size / size;
        sb.addEntry(playerName, Integer.toString((int) score));
        printScoreBoard();
        runMinesweeper();
    }

    /*
     * EFFECTS: prints out the scoreBoard
     */
    private void printScoreBoard() {
        System.out.println();
        for (int i = 0; i < sb.scoreBoard.size(); i++) {
            System.out.println(sb.scoreBoard.get(i));
        }
        long end = System.nanoTime();
        long time = (end - start) / 1000000000;
        //System.out.println(time);
    }
}
