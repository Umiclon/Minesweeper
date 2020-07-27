package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

//represents a box object on a board
//the box can either be a block or a mine
public class Box {
    protected int state;
    protected boolean flagged;
    protected boolean gameOver;
    protected String name;

    /*
     * EFFECTS: creates a box object with int state 0, booleans flagged and gameOver false, and string name "box"
     */
    public Box() {
        this.state = 0;
        this.flagged = false;
        this.gameOver = false;
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
     * EFFECTS: changes the box state depending on flagged and gameOver
     */
    public void changeState() {
        this.state = 1;
    }

    /*
     * REQUIRES: board = new Box[3][3]
     * EFFECTS: returns the number of mines in a 3x3 area on the board
     */
    public int numberOfSurroundingMines(Box[][] board) {
        return 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: switches flagged from false to true and vice versa and updates the state
     */
    public void flag() {
        this.flagged = !flagged;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets gameOver to true and updates the state
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
