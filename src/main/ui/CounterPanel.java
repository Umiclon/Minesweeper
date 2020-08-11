package ui;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class CounterPanel extends JPanel {
    public static final String MINES_LEFT = "Mines: ";
    public static final String BOXES_LEFT = "Boxes: ";
    private JLabel totalMines;
    private JLabel totalCovered;
    private Board board;

    public CounterPanel(Board b) {
        this.board = b;
        setBackground(Color.GRAY);
        drawCounters();
    }

    // MODIFIES: this
    // EFFECTS:  draws the boxes onto grid
    private void drawCounters() {
        setBackground(new Color(138, 154, 184));
        totalMines = new JLabel(MINES_LEFT + board.getTotalMines());
        totalMines.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 6, ScorePanel.LBL_HEIGHT));
        totalMines.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        totalCovered = new JLabel(BOXES_LEFT + board.getTotalCovered());
        totalCovered.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 6, ScorePanel.LBL_HEIGHT));
        totalCovered.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT));
        add(totalMines);
        add(totalCovered);
    }

    // MODIFIES: this
    // EFFECTS:  updates number mines and boxes remaining to be flagged or uncovered
    public void updateCounters() {
        totalMines.setText(MINES_LEFT + board.getTotalMines());
        totalCovered.setText(BOXES_LEFT + board.getTotalCovered());
        repaint();
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
