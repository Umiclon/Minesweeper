package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests for the minesweeper game
class BoardTest {
    private Box[][] board;

    @BeforeEach
    void runBefore(){
        board = new Box[15][15];
    }

    @Test
    void testSetUp(){
        //assertTrue(setUp());
    }

    @Test
    void testFillBoard(){
        //assertTrue(board.fillBoard());
    }

    @Test
    void testMousePressed(){
        //assertTrue(board.mousePressed());
    }

}
