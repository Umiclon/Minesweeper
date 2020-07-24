package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

//represents a box object on a board
//the box can either be a block or a mine
public class Box {
    int state;
    boolean flagged;
    boolean gameOver;
    String name;

    /*
     * MODIFIES: this
     * EFFECTS: creates a box object
     */
    public Box() {
        state = 0;
        flagged = false;
        gameOver = false;
        this.name = "box";
    }

    public String getName() {
        return this.name;
    }

    public int getState() {
        return this.state;
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes state depending on the value of flagged and gameOver
     */
    public void changeState() {
        this.state = 1;
    }

    /*
     * REQUIRES: board = new Box[3][3]
     * EFFECTS: return number of mines in a 3x3 area on the board
     */
    public int numberOfSurroundingMines(Box[][] board) {
        return 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: switches flagged from false to true and vice versa
     */
    public void flag() {
        this.flagged = !flagged;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true
     */
    public void gameOver() {
        this.gameOver = true;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

}
