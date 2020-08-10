////Credits: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//
//package ui;
//
//import model.Block;
//import model.Board;
//import model.Box;
//import model.ScoreBoard;
//import persistence.FileLoader;
//import persistence.FileSaver;
//
//import java.util.Date;
//import java.util.Scanner;
//
////represents the minesweeper game
//public class MinesweeperConsole {
//    private Board board;
//    private ScoreBoard sb;
//    private final Box block = new Block();
//    private Scanner input;
//    private boolean play;
//    private int size;
////    private long start;
//
//    /*
//     * EFFECTS: runs the minesweeper game
//     */
//    public MinesweeperConsole() {
//        runMinesweeper();
//    }
//
//    /*
//     * EFFECTS: reads and processes user input
//     */
//    private void runMinesweeper() {
//        String command;
//        play = true;
//        input = new Scanner(System.in);
//
//        startMenu();
//
//        while (play) {
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("start")) {
//                if (board == null) {
//                    start();
//                } else {
//                    init();
//                }
//            } else if (command.equals("r")) {
//                runMinesweeper();
//            } else if (command.equals("q")) {
//                play = false;
//                quitMenu();
//            } else {
//                runCommands(command);
//            }
//        }
//    }
//
//    /*
//     * EFFECTS: runs user command
//     */
//    private void runCommands(String command) {
//        if (command.equals("f")) {
//            flag();
//        } else if (command.equals("s")) {
//            select();
//        } else {
//            System.out.println("\nInvalid Move");
//            commandList();
//        }
//    }
//
//
//    /*
//     * MODIFIES: board
//     * EFFECTS: displays the initial board
//     */
//    private void init() {
//        System.out.println("\nEnter board size(>= 3): \n");
//
//        size = input.nextInt();
//        board = new Board(size);
//        commandList();
//
//        board.fillBoard();
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.print("X ");
//            }
//            System.out.println(" ");
//        }
//        System.out.println("Mines Left: " + board.getTotalMines());
//        System.out.println("Covered Boxes: " + board.getTotalCovered() + "\n");
//        //start = System.nanoTime();
//    }
//
//    /*
//     * EFFECTS: displays start message
//     */
//    private void startMenu() {
//        System.out.println("\nWelcome to Minesweeper 2020 \n" + "\nType 'start' to begin \n");
//    }
//
//    private void start() {
//        System.out.println("\nDo you want to load a previous game(y/n)? ");
//        String ans = input.next();
//        if (ans.equals("y")) {
//            loadGame();
//        } else {
//            init();
//        }
//        try {
//            sb = FileLoader.readScoreBoard("./data/ScoreBoard.json");
//        } catch (Exception e) {
//            System.out.println("ScoreBoard Loading Error \n");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads Board puzzle and ScoreBoard sb from GAME_FILE, if that file exists;
//    // otherwise calls init()
//    private void loadGame() {
//        try {
//            board = FileLoader.readBoard("./data/Board.json");
//            size = board.getBoard().length;
//            sb = FileLoader.readScoreBoard("./data/ScoreBoard.json");
//            update();
//        } catch (Exception e) {
//            System.out.println("No Saved Game \n");
//            init();
//        }
//    }
//
//    /*
//     * EFFECTS: runs the gameOver menu and resets the game
//     */
//    private void gameOver() {
//        printBoardSolution();
//        System.out.println("GAME OVER \n");
//        runMinesweeper();
//    }
//
//    /*
//     * EFFECTS: prints out the solution for the game
//     */
//    private void printBoardSolution() {
//        System.out.println();
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (board.getName(i, j).equals("block")) {
//                    board.changeState(i, j);
//                }
//                board.gameOver(i, j);
//                board.updateBoard(i, j);
//            }
//            System.out.println(" ");
//        }
//        System.out.println("Number of Mines: " + board.getMines() + "\n");
//    }
//
//    /*
//     * EFFECTS: displays quit game message
//     */
//    private void quitMenu() {
//        saveGame();
//        System.out.println("\nThank You For Playing \n" + "\nBye!");
//    }
//
//    /*
//     * EFFECTS: saves Board puzzle and ScoreBoard sb to GAME_FILE
//     */
//    private void saveGame() {
//        try {
//            FileSaver.writeBoard(board, "./data/Board.json");
//            FileSaver.writeScoreBoard(sb, "./data/ScoreBoard.json");
//            System.out.println("Game Saved");
//            Date d = new Date();
//            System.out.print(d.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            // this is due to a programming error
//        }
//    }
//
//    /*
//     * MODIFIES: Box
//     * EFFECTS: selects a box and uncovers it
//     */
//    private void select() {
//        System.out.println("\nEnter x position: ");
//        int x = input.nextInt();
//
//        System.out.println("\nEnter y position: ");
//        int y = input.nextInt();
//        System.out.println("\nThe box at " + x + ", " + y + " has been uncovered");
//
//        selectBox(x, y);
//    }
//
//    /*
//     * MODIFIES: Box
//     * EFFECTS: selects a box and uncovers it
//     */
//    private void selectBox(int x, int y) {
//        if (board.getName(y, x).equals("block") && !board.isFlagged(y, x)) {
//            for (int i = y - 1; i < y + 2; i++) {
//                for (int j = x - 1; j < x + 2; j++) {
//                    if (i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1
//                            && board.getName(i, j).equals("block")
//                            && !board.isFlagged(i, j) && board.getState(i, j) == 0) {
//                        board.setTotalCovered(board.getTotalCovered() - 1);
//                        board.changeState(i, j);
//                    }
//                }
//            }
//            if (!checkWinCondition()) {
//                update();
//            }
//        } else {
//            play = false;
//            gameOver();
//        }
//    }
//
//    /*
//     * MODIFIES: Box
//     * EFFECTS: flags a box as a potential mine
//     */
//    private void flag() {
//        System.out.println("\nEnter x position: ");
//        int x = input.nextInt();
//
//        System.out.println("\nEnter y position: ");
//        int y = input.nextInt();
//
//        flagBox(x, y);
//
//        System.out.println("\nThe box at " + x + ", " + y + " has been flagged");
//        if (!checkWinCondition()) {
//            update();
//        }
//    }
//
//    /*
//     * MODIFIES: Box
//     * EFFECTS: flags a box as a potential mine
//     */
//    private void flagBox(int x, int y) {
//        if (!board.isFlagged(y, x)) {
//            if (board.getName(y, x).equals("mine")) {
//                board.setMinesFlagged(board.getMinesFlagged() + 1);
//            }
//            board.setTotalMines(board.getTotalMines() - 1);
//        } else {
//            if (board.getName(y, x).equals("mine")) {
//                board.setMinesFlagged(board.getMinesFlagged() - 1);
//            }
//            board.setTotalMines(board.getTotalMines() + 1);
//        }
//        board.flag(y, x);
//        board.changeState(y, x);
//    }
//
//    /*
//     * EFFECTS: displays the list of available commands
//     */
//    public void commandList() {
//        System.out.println("\nType 'r' to restart");
//        System.out.println("Type 'q' to quit");
//        System.out.println("Type 'f' to flag a box");
//        System.out.println("Type 's' to select a box \n");
//    }
//
//    /*
//     * MODIFIES: Board
//     * EFFECTS: updates the game board
//     */
//    private void update() {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                board.updateBoard(i, j);
//            }
//            System.out.println();
//        }
//        System.out.println("Mines Left: " + board.getTotalMines());
//        System.out.println("Covered Boxes: " + board.getTotalCovered() + "\n");
//        commandList();
//    }
//
//    /*
//     * EFFECTS: checks whether all mines have been discovered
//     */
//    private boolean checkWinCondition() {
//        if (board.getMinesFlagged() == board.getMines() | board.getTotalCovered() == board.getMines()) {
//            winGame();
//            return true;
//        }
//        return false;
//    }
//
//    /*
//     * MODIFIES: ScoreBoard
//     * EFFECTS: runs the winGame menu and adds name and score to scoreboard
//     */
//    private void winGame() {
//        printBoardSolution();
//        System.out.println("CONGRATULATIONS YOU WON! \n");
//        System.out.println("\nEnter your name: \n");
//        input = new Scanner(System.in);
//        String playerName = input.nextLine();
//        double score = 100 * board.getMines() / size / size;
//        if (sb == null) {
//            sb = new ScoreBoard();
//        }
//        sb.addEntry(playerName, Integer.toString((int) score));
//        printScoreBoard();
//        runMinesweeper();
//    }
//
//    /*
//     * EFFECTS: prints out the scoreBoard
//     */
//    private void printScoreBoard() {
//        System.out.println();
//        System.out.println("SCOREBOARD");
//        for (int i = 0; i < sb.getScoreBoard().size(); i++) {
//            System.out.println(sb.getScoreBoard().get(i));
//        }
////        long end = System.nanoTime();
////        long time = (end - start) / 1000000000;
////        System.out.println(time);
//    }
//
//    /*
//     * EFFECTS: displays an uncovered Box
//     */
//    public static void printBox(int mines) {
//        System.out.print(mines + " ");
//    }
//
//    /*
//     * EFFECTS: displays a flagged box
//     */
//    public static void printFlag() {
//        System.out.print("F ");
//    }
//
//    /*
//     * EFFECTS: displays a covered box
//     */
//    public static void printCoveredBox() {
//        System.out.print("X ");
//    }
//}

//---------------------------------BOARD METHODS FOR CONSOLE-----------------------------------------------------------

///*
// * EFFECTS: helper for the update method in Minesweeper ui
// */
//public void updateBoard(int i,int j){
//        if(this.getState(i,j)==1){
//        if(i>=1&&i< this.board.length-1&&j>=1&&j< this.board.length-1){
//        this.updateBox(i,j);
//        }else if(j>=1&&j< this.board.length-1&&i==0){
//        this.updateTop(i,j);
//        }else if(j>=1&&j< this.board.length-1){
//        this.updateBot(i,j);
//        }else if(i>=1&&i< this.board.length-1&&j==0){
//        this.updateLeft(i,j);
//        }else if(i>=1&&i< this.board.length-1){
//        this.updateRight(i,j);
//        }else{
//        this.updateCorner(i,j);
//        }
//        }else if(this.isFlagged(i,j)){
//        MinesweeperConsole.printFlag();
//        }else{
//        MinesweeperConsole.printCoveredBox();
//        }
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateBox(int i,int j){
//        Box[][]a=new Box[][]{{this.getBox(i-1,j-1),this.getBox(i-1,j),this.getBox(i-1,j+1)},
//        {this.getBox(i,j-1),this.getBox(i,j),this.getBox(i,j+1)},
//        {this.getBox(i+1,j-1),this.getBox(i+1,j),this.getBox(i+1,j+1)}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateTop(int i,int j){
//        Box[][]a=new Box[][]{{block,block,block},
//        {this.getBox(i,j-1),this.getBox(i,j),this.getBox(i,j+1)},
//        {this.getBox(i+1,j-1),this.getBox(i+1,j),this.getBox(i+1,j+1)}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateBot(int i,int j){
//        Box[][]a=new Box[][]{{this.getBox(i-1,j-1),this.getBox(i-1,j),this.getBox(i-1,j+1)},
//        {this.getBox(i,j-1),this.getBox(i,j),this.getBox(i,j+1)},
//        {block,block,block}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateLeft(int i,int j){
//        Box[][]a=new Box[][]{{block,this.getBox(i-1,j),this.getBox(i-1,j+1)},
//        {block,this.getBox(i,j),this.getBox(i,j+1)},
//        {block,this.getBox(i+1,j),this.getBox(i+1,j+1)}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateRight(int i,int j){
//        Box[][]a=new Box[][]{{this.getBox(i-1,j-1),this.getBox(i-1,j),block},
//        {this.getBox(i,j-1),this.getBox(i,j),block},
//        {this.getBox(i+1,j-1),this.getBox(i+1,j),block}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void updateCorner(int i,int j){
//        if(i==0&&j==0){
//        this.topLeftCorner(i,j);
//        }else if(i==0&&j==this.board.length-1){
//        this.topRightCorner(i,j);
//        }else if(i==this.board.length-1&&j==0){
//        this.botLeftCorner(i,j);
//        }else{
//        this.botRightCorner(i,j);
//        }
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void topLeftCorner(int i,int j){
//        Box[][]a=new Box[][]{{block,block,block},
//        {block,this.getBox(i,j),this.getBox(i,j+1)},
//        {block,this.getBox(i+1,j),this.getBox(i+1,j+1)}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void topRightCorner(int i,int j){
//        Box[][]a=new Box[][]{{block,block,block},
//        {this.getBox(i,j-1),this.getBox(i,j),block},
//        {this.getBox(i+1,j-1),this.getBox(i+1,j),block}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void botLeftCorner(int i,int j){
//        Box[][]a=new Box[][]{{block,this.getBox(i-1,j),this.getBox(i-1,j+1)},
//        {block,this.getBox(i,j),this.getBox(i,j+1)},
//        {block,block,block}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
//
///*
// * EFFECTS: helper for the update method
// */
//public void botRightCorner(int i,int j){
//        Box[][]a=new Box[][]{{this.getBox(i-1,j-1),this.getBox(i-1,j),block},
//        {this.getBox(i,j-1),this.getBox(i,j),block},
//        {block,block,block}};
//        MinesweeperConsole.printBox(this.numberOfSurroundingMines(i,j,a));
//        }
