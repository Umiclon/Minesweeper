// Credits: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// https://codereview.stackexchange.com/questions/215081/minesweeper-game-in-java-using-swing-gui

package ui;

import model.Board;
import persistence.FileLoader;
import persistence.FileSaver;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

//the panel in which the game is rendered.
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, MouseListener {
    private Board board;
    private int size;
    private JButton[][] buttons;
    private Container grid;
    //private JPanel optionPanel;
    private JButton reset;
    private JButton save;
    private JButton load;
    private ScorePanel sp;
    private CounterPanel cp;
    public static Icon mineIcon;
    public static Icon flagIcon;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Board b, ScorePanel sp, CounterPanel cp, Container grid) {
        setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT));
        setBackground(Color.YELLOW);
        setBorder(BorderFactory.createEmptyBorder(0, 1600, 1600, 1600));
        this.board = b;
        this.size = b.getBoard().length;
        this.sp = sp;
        this.cp = cp;
        this.grid = grid;
        initOptions();
        drawGrid();
        initImageIcons();
    }

    // MODIFIES: this
    // EFFECTS: gets imageIcons from file
    public void initImageIcons() {
        try {
            Image scaledMine = ImageIO.read(new File("data/mineIcon.jpg"));
            scaledMine = scaledMine.getScaledInstance(Board.WIDTH / board.getBoard().length,
                    (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / board.getBoard().length, Image.SCALE_SMOOTH);
            mineIcon = new ImageIcon(scaledMine);
            Image scaledFlag = ImageIO.read(new File("data/flagIcon.png"));
            scaledFlag = scaledFlag.getScaledInstance(Board.WIDTH / board.getBoard().length,
                    (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / board.getBoard().length, Image.SCALE_SMOOTH);
            flagIcon = new ImageIcon(scaledFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this, counterPanel
    // EFFECTS: draws the option buttons onto counterPanel
    private void initOptions() {
        reset = new JButton("Reset");
        save = new JButton("Save");
        load = new JButton("Load");

        reset.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 5, ScorePanel.LBL_HEIGHT));
        save.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 5, ScorePanel.LBL_HEIGHT));
        load.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 5, ScorePanel.LBL_HEIGHT));
        reset.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        save.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        load.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));

        reset.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);

        cp.add(reset);
        cp.add(save);
        cp.add(load);
    }

    // MODIFIES: this
    // EFFECTS:  draws the boxes onto grid
    private void drawGrid() {
        grid.setLayout(new GridLayout(board.getBoard().length, board.getBoard().length));
        grid.setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT - ScorePanel.LBL_HEIGHT));
        buttons = new JButton[board.getBoard().length][board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);
                buttons[i][j].setPreferredSize(new Dimension(Board.WIDTH / board.getBoard().length,
                        (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / board.getBoard().length));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN,
                        Board.WIDTH / board.getBoard().length / 2));
                grid.add(buttons[i][j]);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: deals with mouse events
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

        if (checkWinCondition()) {
            winGame();
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

        if (checkWinCondition()) {
            winGame();

        } else {

            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons.length; y++) {
                    if (e.getSource() == buttons[x][y]) {
                        if ("mine".equals(board.getName(x, y))) {
                            gameOver();
                        } else {
                            board.setTotalCovered(board.getTotalCovered() - 1);
                            cp.updateCounters();
                            board.changeState(x, y);
                            textColour(x, y);
                            buttons[x][y].setText((board.updateBoard(x, y)));
                            //buttons[x][y].setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    // EFFECTS: helper for selecting the colour of a button's text
    public void textColour(int x, int y) {
        if (Integer.parseInt(board.updateBoard(x, y)) == 1) {
            buttons[x][y].setForeground(Color.BLUE);
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 2) {
            buttons[x][y].setForeground(Color.GREEN);
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 3) {
            buttons[x][y].setForeground(Color.RED);
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 4) {
            buttons[x][y].setForeground(new Color(0, 0, 139));
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 5) {
            buttons[x][y].setForeground(new Color(101, 67, 33));
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 6) {
            buttons[x][y].setForeground(Color.CYAN);
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 7) {
            buttons[x][y].setForeground(Color.BLACK);
        } else if (Integer.parseInt(board.updateBoard(x, y)) == 8) {
            buttons[x][y].setForeground(Color.GRAY);
        } else {
            buttons[x][y].setForeground(Color.LIGHT_GRAY);
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
        board = new Board(size);
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
    // EFFECTS: loads Board from Board.json, if that file exists
    // otherwise calls init()
    private void loadGame() {
        try {
            board = FileLoader.readBoard("./data/Board.json");
            size = board.getBoard().length;
            sp.loadScores();
            sp.init();
            cp.setBoard(board);
            cp.updateCounters();
            loadButtons();
        } catch (Exception e) {
            System.out.println("No Saved Game \n");
            //init();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the Buttons according to the state of the loaded Board
    public void loadButtons() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons.length; y++) {
                if (1 == board.getState(x, y)) {
                    textColour(x, y);
                    buttons[x][y].setText(board.updateBoard(x, y));
                    buttons[x][y].setEnabled(true);
                } else if (2 == board.getState(x, y) || 7 == board.getState(x, y)) {
                    buttons[x][y].setIcon(flagIcon);
                } else if (6 == board.getState(x, y)) {
                    buttons[x][y].setIcon(mineIcon);
                }
            }
        }
    }

    /*
     * MODIFIES: file
     * EFFECTS: saves Board to Board.json
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
     * MODIFIES: this
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
                    textColour(x, y);
                    buttons[x][y].setText((board.updateBoard(x, y)));
                    buttons[x][y].setIcon(null);
                    buttons[x][y].setEnabled(true);
                }
            }
        }
    }

    /*
     * EFFECTS: checks whether all mines have been discovered
     */
    public boolean checkWinCondition() {
        return (board.getMinesFlagged() == board.getMines() || board.getTotalCovered() == board.getMines());
    }

    /*
     * MODIFIES: this
     * EFFECTS: runs the winGame menu and adds name and score to scoreboard
     */
    private void winGame() {
        printBoardSolution();
        JOptionPane.showMessageDialog(null, "You won!", "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE);
        sp.addEntry("Player: " + 100);
        sp.getScoreBoard().addEntry("Player", "100");
    }

    /*
     * MODIFIES: this
     * EFFECTS: runs the gameOver menu and adds name and score to scoreboard
     */
    public void gameOver() {
        printBoardSolution();
        JOptionPane.showMessageDialog(null, "You lost!", "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE);
        double boardExposed = (double) board.getMines()
                / (double) (board.getTotalCovered() - board.getMinesFlagged());
        double score = 100 * boardExposed;
        sp.addEntry("Player: " + (int) score);
        sp.getScoreBoard().addEntry("Player", Integer.toString((int) score));
    }
}

