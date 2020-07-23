package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests for the minesweeper game
class BoxTest {
    private static final int COVERED_BLOCK = 0;
    private static final int UNCOVERED_BLOCK = 1;
    private static final int FLAGGED_BLOCK = 2;
    private static final int COVERED_MINE = 0;
    private static final int UNCOVERED_MINE = 6;
    private static final int FLAGGED_MINE = 7;
    private boolean flagged;
    private boolean gameOver;
    private Box block;
    private Box mine;
    private Box box;
    private Box[][] board;

    @BeforeEach
    void runBefore() {
        block = new Block();
        mine = new Mine();
        box = new Box();
        flagged = false;
        gameOver = false;
        board = new Box[][]{{block, block, block}, {block, block, block}, {block, block, block}};
    }

    @Test
    void testChangeState() {
        assertEquals(0, block.getState());
        block.flag();
        assertEquals(2, block.getState());
        block.flag();
        assertEquals(0, block.getState());
        block.changeState();
        assertEquals(1,block.getState());
        block.gameOver();
        assertEquals(1, block.getState());

        assertEquals(0, mine.getState());
        mine.flag();
        assertEquals(7, mine.getState());
        mine.flag();
        assertEquals(0, mine.getState());
        mine.changeState();
        assertEquals(6,mine.getState());
        mine.gameOver();
        assertEquals(6, mine.getState());

        box.changeState();
        assertEquals(1,box.getState());
    }

    @Test
    void testNumberOfSurroundingMines() {
        board = new Box[][]{{block, block, block}, {block, block, block}, {block, block, block}};
        assertEquals(0, board[1][1].numberOfSurroundingMines(board));
        board = new Box[][]{{mine, block, mine}, {block, block, mine}, {block, mine, mine}};
        assertEquals(5, board[1][1].numberOfSurroundingMines(board));
        board = new Box[][]{{mine, mine, mine}, {mine, block, mine}, {mine, mine, mine}};
        assertEquals(8, board[1][1].numberOfSurroundingMines(board));

        assertEquals(0, box.numberOfSurroundingMines(board));
    }

    @Test
    void testIsFlagged() {
        assertFalse(block.isFlagged());
        block.flag();
        assertTrue(block.isFlagged());

        assertFalse(mine.isFlagged());
        mine.flag();
        assertTrue(mine.isFlagged());

        assertFalse(box.isFlagged());
        box.flag();
        assertTrue(box.isFlagged());
    }

    @Test
    void testIsGameOver() {
        assertFalse(block.isGameOver());
        block.gameOver();
        assertTrue(block.isGameOver());

        assertFalse(mine.isGameOver());
        mine.gameOver();
        assertTrue(mine.isGameOver());

        assertFalse(box.isGameOver());
        box.gameOver();
        assertTrue(box.isGameOver());
    }


}