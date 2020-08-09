package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//test for the FileLoader class
public class FileLoaderTest {
    @Test
    void testLoadFile1() {
        try {
            Box block = new Block();
            Box mine = new Mine();
            Box[][] a = new Box[][]
                    {{block, mine},
                            {block, block}};
            Board b = FileLoader.readBoard("./data/TestBoardFile.json");
            for (int i = 0; i < b.getBoard().length; i++) {
                for (int j = 0; j < b.getBoard().length;j++) {
                    assertEquals(a[i][j].getName(),b.getBoard()[i][j].getName());
                }
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testLoadFile2() {
        try {
            ScoreBoard a = new ScoreBoard();
            a.addEntry("a", "22");
            ScoreBoard sb = FileLoader.readScoreBoard("./data/TestScoreBoardFile.json");
            assertEquals(a.getScoreBoard().get(0), sb.getScoreBoard().get(0));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Board b = FileLoader.readBoard("./path/does/not/exist/testAccount.txt");
        } catch (IOException e) {
            // expected
        }
    }
}