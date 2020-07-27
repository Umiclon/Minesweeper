package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests for the minesweeper game
class BoardTest {
    private Board board;

    @BeforeEach
    void runBefore(){
        board = new Board(15);
    }

//    @Test
//    void testSetUp(){
//        assertTrue(board.setUp());
//    }

    @Test
    void testFillBoard(){
        assertTrue(board.fillBoard());
    }

//    @Test
//    void testMousePressed(){
//        assertTrue(board.mousePressed());
//    }

}
