package src.main.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.main.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSaver {
    private Writer writer;

    // EFFECTS: constructs a writer that will write data to file
    public FileSaver() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("./data/Board.json"));
    }

    public static void write(Board b, ScoreBoard sb) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer1 = Files.newBufferedWriter(Paths.get("./data/Board.json"));
            Writer writer2 = Files.newBufferedWriter(Paths.get("./data/ScoreBoard.json"));

            gson.toJson(b, writer1);
            System.out.println(b);

            gson.toJson(sb, writer2);

            writer1.close();
            writer2.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

