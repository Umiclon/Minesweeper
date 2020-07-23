package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

// represents a mine block in a box on the game board
public class Mine extends Box {
    private int state;
    private static final int MINE_X = 4;
    private static final int MINE_Y = 4;
    boolean flagged;

    public Mine() {
        state = 5;
        flagged = false;
    }

    public int getState() {
        return this.state;
    }

    public void changeState() {
        state = 6;

    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isGameOver() {
        return false;
    }

}
