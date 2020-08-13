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

    // EFFECTS: creates the panel with a gray background
    public CounterPanel(Board b) {
        this.board = b;
        setBackground(Color.GRAY);
        drawCounters();
    }

    // Setter
    public void setBoard(Board board) {
        this.board = board;
    }

    // MODIFIES: this
    // EFFECTS:  draws the the counterPanel
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
        totalMines.setText(MINES_LEFT + (Math.max(board.getTotalMines(), 0)));
        totalCovered.setText(BOXES_LEFT + (Math.max(board.getTotalCovered(), 0)));
        repaint();
    }
}
