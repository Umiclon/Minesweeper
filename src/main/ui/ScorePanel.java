// Credits: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import model.Board;
import model.ScoreBoard;
import persistence.FileLoader;
import persistence.FileSaver;

import javax.swing.*;
import java.awt.*;

//represents the panel in which the scoreboard is displayed.
@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
    protected static final int LBL_WIDTH = Board.WIDTH;
    protected static final int LBL_HEIGHT = 50;
    private ScoreBoard scoreBoard;
    private JTextArea textArea;
    JScrollPane scrollPane;
    FileSaver fileSaver;
    FileLoader fileLoader;

    // EFFECTS: sets the background colour and draws the scrollPane,
    public ScorePanel(Board b) {
        scoreBoard = new ScoreBoard();
        setBackground(new Color(101, 140, 153));
        init();
    }

    // Getter
    public ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    /*
     * MODIFIES: board
     * EFFECTS: displays the initial scoreBoard
     */
    public void init() {
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, LBL_HEIGHT / 2));
        textArea.append("SCOREBOARD");
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT * 3));
        add(scrollPane);
        loadScores();
        for (String str : scoreBoard.getScoreBoard()) {
            addEntry(str);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Board puzzle and ScoreBoard sb from GAME_FILE, if that file exists;
    // otherwise calls init()
    public void loadScores() {
        try {
            scoreBoard = FileLoader.readScoreBoard("./data/ScoreBoard.json");
        } catch (Exception e) {
            System.out.println("No Saved Game \n");
            init();
        }
    }

    /*
     * EFFECTS: saves Board puzzle and ScoreBoard sb to GAME_FILE
     */
    public void saveScores() {
        try {
            FileSaver.writeScoreBoard(scoreBoard, "./data/ScoreBoard.json");
        } catch (Exception e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS:  displays a new entry on the scrollPane
    public void addEntry(String entry) {
        textArea.append("\n" + entry);
        repaint();
    }
}
