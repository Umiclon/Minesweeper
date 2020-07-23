package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

// represents a block object in a box on the game board
// changes state from covered to uncovered or flagged
// uncovered blocks will show a number
// flagged blocks cannot be uncovered unless un-flagged
public class Block extends Box {
    private int state;
    private static final int BLOCK_X = 5;
    private static final int BLOCK_Y = 5;
    boolean flagged = false;

    public Block() {
        state = 0;
    }

    public int getState() {
        return this.state;
    }

    public void changeState() {
        state = 1;
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
