package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//tests for the scoreboard
public class ScoreBoardTest {

    private ScoreBoard sb;

    @BeforeEach
    void runBefore() {
        sb = new ScoreBoard();
    }

    @Test
    void addEntryTest() {
        sb.addEntry("Bob", "5:00");
        assertEquals("Bob: 5:00", sb.getScoreBoard().get(0));
    }
}
