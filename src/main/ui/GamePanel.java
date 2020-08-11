// Credits: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// https://codereview.stackexchange.com/questions/215081/minesweeper-game-in-java-using-swing-gui

package ui;

import model.Board;
import model.*;
import persistence.FileLoader;
import persistence.FileSaver;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Box;

//the panel in which the game is rendered.
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, MouseListener {
    private Board board;
    private JButton[][] buttons;
    private Container grid;
    FileSaver fileSaver;
    FileLoader fileLoader;
    private JPanel optionPanel;
    private JButton reset;
    private JButton save;
    private JButton load;
    private ScorePanel sp;
    private CounterPanel cp;
    public static Icon mineIcon;
    public static Icon flagIcon;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Board b, ScorePanel sp, CounterPanel cp) {
        setPreferredSize(new Dimension(Board.WIDTH + 000, Board.HEIGHT + 100));
        setBackground(Color.GRAY);
        this.board = b;
        this.sp = sp;
        this.cp = cp;
        init();
        drawGrid();

        try {
            Image scaledMine = ImageIO.read(new File("data/mineIcon.jpg"));
            scaledMine = scaledMine.getScaledInstance(Board.WIDTH / board.getBoard().length,
                    Board.HEIGHT / board.getBoard().length, Image.SCALE_SMOOTH);
            mineIcon = new ImageIcon(scaledMine);
            Image scaledFlag = ImageIO.read(new File("data/flagIcon.png"));
            scaledFlag = scaledFlag.getScaledInstance(Board.WIDTH / board.getBoard().length,
                    Board.HEIGHT / board.getBoard().length, Image.SCALE_SMOOTH);
            flagIcon = new ImageIcon(scaledFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        optionPanel = new JPanel();

        reset = new JButton("Reset");
        save = new JButton("Save");
        load = new JButton("Load");

        reset.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 3, ScorePanel.LBL_HEIGHT / 2));
        save.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 3, ScorePanel.LBL_HEIGHT / 2));
        load.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 3, ScorePanel.LBL_HEIGHT / 2));
        reset.setFont(new Font("Arial", Font.PLAIN, Board.WIDTH / board.getBoard().length / 3));
        save.setFont(new Font("Arial", Font.PLAIN, Board.WIDTH / board.getBoard().length / 3));
        load.setFont(new Font("Arial", Font.PLAIN, Board.WIDTH / board.getBoard().length / 3));

        reset.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);

        optionPanel.add(reset);
        optionPanel.add(save);
        optionPanel.add(load);
        add(optionPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS:  draws the boxes onto grid
    private void drawGrid() {
        grid = new Container();
        grid.setLayout(new GridLayout(board.getBoard().length, board.getBoard().length));
        buttons = new JButton[board.getBoard().length][board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);
                buttons[i][j].setPreferredSize(new Dimension(Board.WIDTH / board.getBoard().length,
                        Board.HEIGHT / board.getBoard().length));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN,
                        Board.WIDTH / board.getBoard().length / 2));
                grid.add(buttons[i][j]);
            }
        }
        add(grid);
    }


    // MODIFIES: this
    // EFFECTS: deals with right-click events
    public void mousePressed(MouseEvent e) {
        JButton b = (JButton) e.getSource();

        if (e.getButton() == MouseEvent.BUTTON3) {

            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons.length; y++) {

                    if (null == (b.getIcon()) && e.getSource() == buttons[x][y]) {
                        if (board.getName(x, y).equals("mine")) {
                            flagMine(b, x, y);
                        } else {
                            flagBlock(b, x, y);
                        }
                    } else if (e.getSource() == buttons[x][y]) {
                        if (board.getName(x, y).equals("mine")) {
                            unFlagMine(b, x, y);
                        } else {
                            unFlagBlock(b, x, y);
                        }
                    }
                }
            }
        }
    }

    //EFFECTS: helper for mousePressed
    public void flagMine(JButton b, int x, int y) {
        b.setIcon(flagIcon);
        board.flag(x, y);
        board.setMinesFlagged(board.getMinesFlagged() + 1);
        board.setTotalMines(board.getTotalMines() - 1);
        cp.updateCounters();
    }

    //EFFECTS: helper for mousePressed
    public void flagBlock(JButton b, int x, int y) {
        b.setIcon(flagIcon);
        board.flag(x, y);
        board.setTotalMines(board.getTotalMines() - 1);
        cp.updateCounters();
    }

    //EFFECTS: helper for mousePressed
    public void unFlagMine(JButton b, int x, int y) {
        b.setIcon(null);
        board.flag(x, y);
        board.setMinesFlagged(board.getMinesFlagged() - 1);
        board.setTotalMines(board.getTotalMines() + 1);
        cp.updateCounters();
    }

    //EFFECTS: helper for mousePressed
    public void unFlagBlock(JButton b, int x, int y) {
        b.setIcon(null);
        board.flag(x, y);
        board.setTotalMines(board.getTotalMines() + 1);
        cp.updateCounters();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // MODIFIES: this
    // EFFECTS: deals with actions related to buttons being clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        optionSelected(e);

        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                if (e.getSource() == buttons[x][y]) {
                    if ("mine".equals(board.getName(x, y))) {
                        printBoardSolution();
                        gameOver();
                    } else {
                        if (checkWinCondition()) {
                            winGame();
                        }
                        board.setTotalCovered(board.getTotalCovered() - 1);
                        cp.updateCounters();
                        board.changeState(x, y);
                        buttons[x][y].setText((board.updateBoard(x, y)));
                        buttons[x][y].setEnabled(false);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deals with option buttons being clicked
    public void optionSelected(ActionEvent e) {
        if (reset.equals(e.getSource())) {
            reset();
        } else if (save.equals(e.getSource())) {
            saveGame();
            //ScorePanel.saveScores();
        } else if (load.equals(e.getSource())) {
            reset();
            loadGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the display by covering all boxes
    private void reset() {
        board = new Board(10);
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                buttons[i][j].setText(null);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setIcon(null);

            }
        }
        cp.setBoard(board);
        cp.updateCounters();
    }

    // MODIFIES: this
    // EFFECTS: loads Board puzzle and ScoreBoard sb from GAME_FILE, if that file exists
    // otherwise calls init()
    private void loadGame() {
        try {
            board = FileLoader.readBoard("./data/Board.json");
            sp.loadScores();
            sp.init();
            cp.updateCounters();
            updateButtons();
        } catch (Exception e) {
            System.out.println("No Saved Game \n");
            //init();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the Buttons according to the state of the loaded Board
    public void updateButtons() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                if (1 == board.getState(x, y)) {
                    buttons[x][y].setText(board.updateBoard(x, y));
                    buttons[x][y].setEnabled(false);
                } else if (2 == board.getState(x, y) || 7 == board.getState(x, y)) {
                    buttons[x][y].setIcon(flagIcon);
                } else if (6 == board.getState(x, y)) {
                    buttons[x][y].setIcon(mineIcon);
                }
            }
        }
    }

    /*
     * EFFECTS: saves Board puzzle and ScoreBoard sb to GAME_FILE
     */
    private void saveGame() {
        try {
            sp.saveScores();
            FileSaver.writeBoard(board, "./data/Board.json");
            System.out.println("Game Saved");
        } catch (Exception e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    /*
     * EFFECTS: prints out the solution for the game
     */
    private void printBoardSolution() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                board.gameOver(x, y);
                board.changeState(x, y);
                if ("mine".equals(board.getName(x, y))) {
                    buttons[x][y].setIcon(mineIcon);
                } else {
                    buttons[x][y].setText((board.updateBoard(x, y)));
                    buttons[x][y].setIcon(null);
                    buttons[x][y].setEnabled(false);
                }
            }
        }
    }

    /*
     * EFFECTS: checks whether all mines have been discovered
     */
    public boolean checkWinCondition() {
        if (board.getMinesFlagged() == board.getMines() | board.getTotalCovered() == board.getMines()) {
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
    }

    /*
     * MODIFIES: ScoreBoard
     * EFFECTS: runs the gameOver menu and adds name and score to scoreboard
     */
    public void gameOver() {
        JOptionPane.showMessageDialog(null, "You lost!", "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE);
        double score = 100 - 100 * board.getTotalCovered() / board.getBoard().length / board.getBoard().length;
        sp.addEntry("Player: " + Integer.toString((int) score));
        sp.getScoreBoard().addEntry("Player", Integer.toString((int) score));
    }
}

