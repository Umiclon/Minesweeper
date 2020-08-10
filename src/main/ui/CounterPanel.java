package ui;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class CounterPanel extends JPanel {
    private static final String MINES_LEFT = "Mines: ";
    private static final String BOXES_LEFT = "Boxes: ";
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
        setBackground(new Color(180, 180, 180));
        totalMines = new JLabel(MINES_LEFT + board.getMines());
        totalMines.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 2, ScorePanel.LBL_HEIGHT));
        totalMines.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT / 2));
        totalCovered = new JLabel(BOXES_LEFT + board.getTotalCovered());
        totalCovered.setPreferredSize(new Dimension(ScorePanel.LBL_WIDTH / 2, ScorePanel.LBL_HEIGHT));
        totalCovered.setFont(new Font("Arial", Font.PLAIN, ScorePanel.LBL_HEIGHT / 2));
        add(totalMines);
        add(Box.createHorizontalStrut(10));
        add(totalCovered);
    }

    // MODIFIES: this
    // EFFECTS:  updates number mines and boxes remaining to be flagged or uncovered
    public void updateCounters() {
        totalMines.setText(MINES_LEFT + board.getMines());
        totalCovered.setText(BOXES_LEFT + board.getTotalCovered());
        repaint();
    }
}
