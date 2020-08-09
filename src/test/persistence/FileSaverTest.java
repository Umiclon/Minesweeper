package persistence;

import model.Board;
import model.ScoreBoard;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//test for the FileSaver class
public class FileSaverTest {

    @Test
    void testSaveFile1() {
        try {
            Board a1 = new Board(3);
            a1.fillBoard();

            FileSaver.writeBoard(a1, "./data/Board.json");

            Board b = FileLoader.readBoard("./data/Board.json");
            for (int i = 0; i < b.getBoard().length; i++) {
                for (int j = 0; j < b.getBoard().length; j++) {
                    assertEquals(a1.getBoard()[i][j].getName(), b.getBoard()[i][j].getName());
                }
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testLoadFile2() {
        try {
            ScoreBoard a2 = new ScoreBoard();
            a2.addEntry("a", "22");

            FileSaver.writeScoreBoard(a2,"./data/ScoreBoard.json" );

            ScoreBoard sb = FileLoader.readScoreBoard("./data/ScoreBoard.json");
            for (int i = 0; i < sb.getScoreBoard().size(); i++) {
                assertEquals(a2.getScoreBoard().get(i), sb.getScoreBoard().get(i));
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Board b = new Board(3);
            FileSaver.writeBoard(b,"./path/does/not/exist/testAccount.txt");
        } catch (IOException e) {
            // expected
        }

        try {
            ScoreBoard sb = new ScoreBoard();
            FileSaver.writeScoreBoard(sb,"./path/does/not/exist/testAccount.txt");
        } catch (IOException e) {
            // expected
        }
    }
}