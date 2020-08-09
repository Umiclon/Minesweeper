//NOTE: code from this website:
//https://attacomsian.com/blog/gson-read-write-json

package persistence;

import com.google.gson.Gson;
import model.Board;
import model.ScoreBoard;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

//loads the object from the json file
public class FileLoader {

    // EFFECTS: reads the Board object from the corresponding json file
    public static Board readBoard(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));
        Board b = gson.fromJson(reader, Board.class);
        reader.close();
        return b;
    }

    // EFFECTS: reads the ScoreBoard object from the corresponding json file
    public static ScoreBoard readScoreBoard(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));
        ScoreBoard sb = gson.fromJson(reader, ScoreBoard.class);
        reader.close();
        return sb;
    }
}
