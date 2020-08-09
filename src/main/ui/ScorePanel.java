// NOTE: based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase , and
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete

package ui;

import model.Board;
import model.ScoreBoard;

import javax.swing.*;
import java.awt.*;

//represents the panel in which the scoreboard is displayed.
@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
    private static final String MINES_LEFT = "Mines: ";
    private static final String BOXES_LEFT = "Boxes: ";
    private static final int LBL_WIDTH = 750;
    private static final int LBL_HEIGHT = 100;
    private ScoreBoard scoreboard;
    private JLabel totalMines;
    private JLabel totalCovered;
    private Board board;

    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Board b) {
        scoreboard = new ScoreBoard();
        board = b;
        setBackground(new Color(180, 180, 180));
        totalMines = new JLabel(MINES_LEFT + board.getMines());
        totalMines.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        totalMines.setFont(new Font("Arial", Font.PLAIN, LBL_HEIGHT / 2));
        totalCovered = new JLabel(BOXES_LEFT + board.getTotalCovered());
        totalCovered.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        totalCovered.setFont(new Font("Arial", Font.PLAIN, LBL_HEIGHT / 2));
        add(totalMines);
        add(Box.createHorizontalStrut(10));
        add(totalCovered);
    }

    // MODIFIES: this
    // EFFECTS:  updates number mines and boxes remaining to be flagged or uncovered
    public void update() {
        totalMines.setText(MINES_LEFT + board.getMines());
        totalCovered.setText(BOXES_LEFT + board.getTotalCovered());
        repaint();
    }
}
