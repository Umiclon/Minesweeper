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
public class MinesweeperGUI extends JFrame {

    private Board board;
    private GamePanel gp;
    private CounterPanel cp;
    private ScorePanel sp;

    // EFFECTS: sets up the window in which the Minesweeper game will be played
    public MinesweeperGUI() {
        super("Minesweeper");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new KeyHandler());
        init();
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  initializes the panels
    private void init() {
        board = new Board(10);
        sp = new ScorePanel(board);
        cp = new CounterPanel(board);
        gp = new GamePanel(board, sp, cp);
        add(sp, BorderLayout.NORTH);
        add(gp, BorderLayout.CENTER);
        add(cp, BorderLayout.SOUTH);
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
            //board.keyPressed(e.getKeyCode());
        }
    }
}

