package src.test.model;

import static org.junit.jupiter.api.Assertions.*;

import src.main.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests for the board
class BoardTest {
    private Board board;

    @BeforeEach
    void runBefore() {
        board = new Board(3);
        board.fillBoard();
    }

    @Test
    void testFillBoard() {
        assertTrue(board.fillBoard());
    }

    @Test
    void testGettersAndSetters() {
        board.setTotalMines(9);
        assertEquals(9, board.getTotalMines());
        board.setTotalCovered(9);
        assertEquals(9, board.getTotalCovered());
        board.setMinesFlagged(9);
        assertEquals(9, board.getMinesFlagged());
        board.setMines(9);
        assertEquals(9, board.getMines());
        assertEquals(board.getBox(1, 1).getName(), board.getName(1, 1));
    }

    @Test
    void testNumberOfSurroundingMines() {
        board.setBox(0, 0, new Mine());
        board.setBox(0, 1, new Mine());
        board.setBox(0, 2, new Mine());
        board.setBox(1, 0, new Mine());
        board.setBox(1, 1, new Block());
        board.setBox(1, 2, new Mine());
        board.setBox(2, 0, new Mine());
        board.setBox(2, 1, new Mine());
        board.setBox(2, 2, new Mine());
        Box[][] a = board.getBoard();
        assertEquals(8, board.numberOfSurroundingMines(1, 1, a));

    }

    @Test
    void testFlag() {
        board.setBox(1, 1, new Block());
        board.flag(1, 1);
        board.changeState(1,1);
        assertTrue(board.isFlagged(1, 1));
        assertEquals(2, board.getState(1, 1));
        board.setBox(1, 1, new Mine());
        board.flag(1, 1);
        board.changeState(1,1);
        assertTrue(board.isFlagged(1, 1));
        assertEquals(7, board.getState(1, 1));
    }

    @Test
    void testGameOver() {
        board.setBox(1, 1, new Block());
        board.gameOver(1, 1);
        assertTrue(board.isGameOver(1, 1));
        assertEquals(1, board.getState(1, 1));
        board.setBox(1, 1, new Mine());
        board.gameOver(1, 1);
        assertTrue(board.isGameOver(1, 1));
        assertEquals(6, board.getState(1, 1));
    }

    @Test
    void testChangeState() {
        board.changeState(1, 1);
        if (board.getName(1, 1).equals("Mine")) {
            assertEquals(6, board.getState(1, 1));
        } else if (board.getName(1, 1).equals("Block")) {
            assertEquals(1, board.getState(1, 1));
        }
    }

}
