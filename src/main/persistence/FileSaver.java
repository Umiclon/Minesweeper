//NOTE: code from this website:
//https://attacomsian.com/blog/gson-read-write-json

package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Board;
import model.ScoreBoard;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

//Saves the object onto the json file
public class FileSaver {

    // EFFECTS: writes the objects to the corresponding json file
    public static void writeBoard(Board b, String path) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Writer writer1 = Files.newBufferedWriter(Paths.get(path));

        gson.toJson(b, writer1);

        writer1.close();
    }

    // EFFECTS: writes the objects to the corresponding json file
    public static void writeScoreBoard(ScoreBoard sb, String path) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Writer writer2 = Files.newBufferedWriter(Paths.get("./data/ScoreBoard.json"));

        gson.toJson(sb, writer2);

        writer2.close();
    }
}

