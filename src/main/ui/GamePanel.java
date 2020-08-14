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
        initImageIcons();
        drawGrid();
    }

    // MODIFIES: this
    // EFFECTS: gets imageIcons from file
    public void initImageIcons() {
        try {
            Image scaledMine = ImageIO.read(new File("data/mineIcon.jpg"));
            scaledMine = scaledMine.getScaledInstance(Board.WIDTH / size,
                    (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / size, Image.SCALE_SMOOTH);
            mineIcon = new ImageIcon(scaledMine);

            Image scaledFlag = ImageIO.read(new File("data/flagIcon.png"));
            scaledFlag = scaledFlag.getScaledInstance(Board.WIDTH / size,
                    (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / size, Image.SCALE_SMOOTH);
            flagIcon = new ImageIcon(scaledFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this, counterPanel
    // EFFECTS: draws the option buttons onto counterPanel
    public void initOptions() {
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
    public void drawGrid() {
        grid.setLayout(new GridLayout(size, size));
        grid.setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT - ScorePanel.LBL_HEIGHT));
        buttons = new JButton[size][size];

        drawButtons();
    }

    // MODIFIES: this
    // EFFECTS:  helper for drawGrid
    public void drawButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);
                buttons[i][j].setPreferredSize(new Dimension(Board.WIDTH / size,
                        (Board.HEIGHT - ScorePanel.LBL_HEIGHT * 2) / size));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN,
                        Board.WIDTH / size / 2));
                grid.add(buttons[i][j]);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deals with mouse events
    public void mousePressed(MouseEvent e) {
        JButton b = (JButton) e.getSource();

        if (e.getButton() == MouseEvent.BUTTON3) {
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {

                    if (null == (b.getIcon()) && e.getSource() == buttons[x][y]) {
                        flag(b, x, y);
                    } else if (e.getSource() == buttons[x][y]) {
                        unFlag(b, x, y);
                    }
                }
            }
            if (checkWinCondition()) {
                winGame();
            }
        }
    }

    //MODIFIES: this, Board
    //EFFECTS: flags a box on the board, displays flag on button
    public void flag(JButton b, int x, int y) {
        if (board.getName(x, y).equals("mine")) {
            board.setMinesFlagged(board.getMinesFlagged() + 1);
        }
        b.setIcon(flagIcon);
        b.setText(null);
        board.flag(x, y);
        board.changeState(x,y);
        board.setTotalMines(board.getTotalMines() - 1);
        cp.updateCounters();
    }

    //MODIFIES: this, Board
    //EFFECTS: un-flags a box on the board, removes flag on button
    public void unFlag(JButton b, int x, int y) {
        if (board.getName(x, y).equals("mine")) {
            board.setMinesFlagged(board.getMinesFlagged() - 1);
        }
        b.setIcon(null);
        board.flag(x, y);
        board.changeState(x,y);
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
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (e.getSource() == buttons[x][y]) {
                        if ("mine".equals(board.getName(x, y))) {
                            gameOver();
                        } else {
                            buttons[x][y].setIcon(null);
                            board.setTotalCovered(board.getTotalCovered() - 1);
                            cp.updateCounters();
                            board.changeState(x, y);
                            textColour(x, y);
                            buttons[x][y].setText((board.updateBoard(x, y)));
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
    public void reset() {
        board = new Board(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                updateButton(i, j);
            }
        }
        cp.setBoard(board);
        cp.updateCounters();
    }

    // MODIFIES: this
    // EFFECTS: loads Board from Board.json, if that file exists
    // otherwise calls init()
    public void loadGame() {
        try {
            board = FileLoader.readBoard("./data/Board.json");
            size = board.getBoard().length;
            sp.loadScores();
            sp.init();
            cp.setBoard(board);
            cp.updateCounters();
            updateButtons();
        } catch (Exception e) {
            System.out.println("No Saved Game \n");
            //init();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the Buttons according to the state of the Boxes on Board
    public void updateButtons() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                updateButton(x, y);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates a Button according to the state of the Box on Board
    public void updateButton(int x, int y) {
        if (1 == board.getState(x, y)) {
            buttons[x][y].setIcon(null);
            textColour(x, y);
            buttons[x][y].setText(board.updateBoard(x, y));
        } else if (2 == board.getState(x, y) || 7 == board.getState(x, y)) {
            buttons[x][y].setIcon(flagIcon);
        } else if (6 == board.getState(x, y)) {
            buttons[x][y].setIcon(mineIcon);
        } else {
            buttons[x][y].setText(null);
            buttons[x][y].setIcon(null);
        }
    }

    /*
     * MODIFIES: file
     * EFFECTS: saves Board to Board.json
     */
    public void saveGame() {
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
    public void printBoardSolution() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board.gameOver(x, y);
                board.changeState(x, y);
                updateButton(x, y);
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
     * MODIFIES: this, sp
     * EFFECTS: runs the winGame menu and adds name and score to scoreboard
     */
    public void winGame() {
        int score = 100;
        endGame("You won!", score);
    }

    /*
     * MODIFIES: this, sp
     * EFFECTS: runs the gameOver menu and adds name and score to scoreboard
     */
    public void gameOver() {
        double boardUncovered = (double) board.getMines() / (double) board.getTotalCovered();
        double boardFlagged = (double) board.getMinesFlagged() / (double) board.getMines();
        double score = 100 * Math.max(boardUncovered, boardFlagged);
        endGame("You lost!", (int) score);
    }

    /*
     * MODIFIES: this, sp
     * EFFECTS: runs the end menu and adds name and score to scoreboard
     */
    public void endGame(String endMsg, int score) {
        printBoardSolution();
        JOptionPane.showMessageDialog(null, endMsg, "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE);
        sp.addEntry("Player: " + score);
        sp.getScoreBoard().addEntry("Player", Integer.toString(score));
    }

}

