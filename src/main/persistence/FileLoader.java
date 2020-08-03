package src.main.persistence;


import com.google.gson.Gson;
import src.main.model.Board;
import src.main.ui.Minesweeper;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {

    public static Board read() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("./data/Board.json"));

            // convert JSON string to Book object
            Board b = gson.fromJson(reader, Board.class);

            // print book
            System.out.println(b);

            // close reader
            reader.close();

            return b;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Board(4);
    }
}
