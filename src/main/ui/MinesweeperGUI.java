// NOTE: based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase , and
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete

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
    private ScorePanel sp;

    // EFFECTS: sets up the window in which the Minesweeper game will be played
    public MinesweeperGUI() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        board = new Board(3);
        gp = new GamePanel(board);
        sp = new ScorePanel(board);
        add(gp);
        add(sp, BorderLayout.SOUTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 220 / 4));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(1000,200));
        add(scrollPane, BorderLayout.NORTH);

        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // EFFECTS: reads key input
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //board.keyPressed(e.getKeyCode());
        }
    }

    public static void main(String[] args) {
        new MinesweeperGUI();
    }
}

