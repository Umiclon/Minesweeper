package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Box {
    int state;
    boolean flagged;

    public Box() {
        state = -1;
        flagged = false;
    }

    public int getState() {
        return this.state;
    }

    public void changeState() {

    }

    public int numberOfSurroundingMines() {
        return 0;
    }


    public boolean isFlagged() {
        return flagged;
    }

    public boolean isGameOver() {
        return false;
    }
}
