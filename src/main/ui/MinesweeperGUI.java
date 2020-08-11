// Credits:  https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
// https://codereview.stackexchange.com/questions/215081/minesweeper-game-in-java-using-swing-gui

package ui;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// represents the main window of the Minesweeper game
@SuppressWarnings("serial")
public class MinesweeperGUI extends JFrame implements ActionListener {

    private Board board;
    private GamePanel gp;
    private CounterPanel cp;
    private ScorePanel sp;
    private int size = 10;
    private JPanel startScreen;
    private JButton start;
    private JTextField input;
    private JLabel startMsg;
    private Container grid;
    private JPanel optionPanel;

    // EFFECTS: sets up the window in which the Minesweeper game will be played
    public MinesweeperGUI() {
        super("Minesweeper");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new KeyHandler());
        start();
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  initializes start screen
    private void start() {
        startScreen = new JPanel();
        startScreen.setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT + ScorePanel.LBL_HEIGHT));
        startMsg = new JLabel("Enter board size: ", SwingConstants.CENTER);
        startMsg.setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT / 2 - 200));
        startMsg.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        startMsg.setVerticalAlignment(JLabel.BOTTOM);

        input = new JTextField("", 20);
        input.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 8, ScorePanel.LBL_HEIGHT * 2));
        input.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        input.setAlignmentX(Component.CENTER_ALIGNMENT);

        start = new JButton("Start");
        start.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 8, ScorePanel.LBL_HEIGHT));
        start.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        start.addActionListener(this);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        startScreen.add(startMsg, BorderLayout.NORTH);
        startScreen.add(input, BorderLayout.CENTER);
        startScreen.add(Box.createVerticalStrut(500));
        startScreen.add(start, BorderLayout.SOUTH);
        add(startScreen);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            size = Integer.parseInt(input.getText());
            startScreen.setEnabled(false);
            startScreen.setVisible(false);
            remove(startScreen);
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS:  initializes the panels
    private void init() {
        board = new Board(size);
        sp = new ScorePanel(board);
        cp = new CounterPanel(board);
        grid = new Container();
        gp = new GamePanel(board, sp, cp, grid);

        add(sp, BorderLayout.NORTH);
        add(gp, BorderLayout.CENTER);
        add(grid, BorderLayout.CENTER);
        add(cp, BorderLayout.SOUTH);
    }


    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2 - 50);
    }

    // EFFECTS: reads key input
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        }
    }

    /*
     * MODIFIES: ScoreBoard
     * EFFECTS: runs the gameOver menu and adds name and score to scoreboard
     */
    public void gameOver() {
//        while (true) {
//            if (board.isGameOver(0,0)) {
//                gameOver();
//                break;
//            }
//        }
        gp.setEnabled(false);
        JPanel textBox = new JPanel();
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(Board.WIDTH, 100));
        textField.setFont(new Font("Arial", Font.PLAIN, 220 / 8));
        textField.setEnabled(true);
        textBox.add(textField);
        add(textBox, BorderLayout.NORTH);
        //textField.setLocation(Board.WIDTH / 2, Board.HEIGHT / 2);
        sp.addEntry(textField.getText());
    }
}

