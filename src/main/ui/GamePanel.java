// NOTE: based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase , and
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete

package ui;

import model.Board;
import model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

//the panel in which the game is rendered.
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private Board board;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Board b) {
        setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT));
        setBackground(Color.GRAY);
        this.board = b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);

        //NEEDS TO BE CHANGED!!!
        if (false) {
            gameOver(g);
        }
    }

    // MODIFIES: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) {
        drawBoxes(g);
        drawNumber(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws the boxes onto g
    private void drawBoxes(Graphics g) {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                drawBox(g, board.getBox(i, j));
            }
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the Box onto g
    private void drawBox(Graphics g, Box b) {
        Box box = b;
        Color savedCol = g.getColor();
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws the numOfSurroundingMines i onto g
    private void drawNumber(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws game over and replay messages onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        //centreString(GAME_OVER, g, fm, Board.HEIGHT / 2);
        //centreString(REPLAY, g, fm, Board.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Board.WIDTH - width) / 2, y);
    }
}

