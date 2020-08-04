package persistence;

import org.junit.jupiter.api.Test;
import model.*;
import persistence.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FileSaverTest {

    @Test
    void testSaveFile1() {
        try {
            Board a1 = new Board(3);
            a1.fillBoard();

            ScoreBoard a2 = new ScoreBoard();
            a2.addEntry("a", "22");

            FileSaver.write(a1, a2);

            Board b = FileLoader.readBoard("./data/Board.json");
            for (int i = 0; i < b.getBoard().length; i++) {
                for (int j = 0; j < b.getBoard().length; j++) {
                    assertEquals(a1.getBoard()[i][j].getName(), b.getBoard()[i][j].getName());
                }
            }

            ScoreBoard sb = FileLoader.readScoreBoard("./data/ScoreBoard.json");
            assertEquals(a2.getScoreBoard().get(0), sb.getScoreBoard().get(0));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}