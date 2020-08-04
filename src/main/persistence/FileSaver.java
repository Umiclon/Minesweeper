//NOTE: code from this website:
//https://attacomsian.com/blog/gson-read-write-json

package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

//Saves the object onto the json file
public class FileSaver {

    // EFFECTS: writes the objects to the corresponding json file
    public static void write(Board b, ScoreBoard sb) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer1 = Files.newBufferedWriter(Paths.get("./data/Board.json"));
            Writer writer2 = Files.newBufferedWriter(Paths.get("./data/ScoreBoard.json"));

            gson.toJson(b, writer1);
            gson.toJson(sb, writer2);

            writer1.close();
            writer2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

