package model;

import java.util.ArrayList;

//represents a scoreboard that shows name and time spent
public class ScoreBoard {
    private ArrayList<String> scoreBoard;

    /*
     * EFFECTS: creates a scoreboard with an arrayList and a string title "SCOREBOARD"
     */
    public ScoreBoard() {
        scoreBoard = new ArrayList<String>();
    }

    public ArrayList<String> getScoreBoard() {
        return this.scoreBoard;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a new entry with name and time to the scoreboard
     */
    public void addEntry(String name, String time) {
        scoreBoard.add(name + ": " + time);
    }

}
