package src.main.persistence;

import com.google.gson.Gson;
import src.main.model.*;

import java.io.*;

public class Writer {
    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

//    "./data/myFile.txt"
//
//    rather than an absolute path of the form:
//
//            "/Users/Sammy/Documents/CPSC210/PersonalProject/MyApp/data/myFile.txt"

    public static void write() {
        try {

            Gson gson = new Gson();

            ScoreBoard sc = new ScoreBoard();

            Board b =  new Board(4);
            b.fillBoard();


            // convert book object to JSON
            String json = new Gson().toJson(b);

            // print JSON string
            System.out.println(json);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

