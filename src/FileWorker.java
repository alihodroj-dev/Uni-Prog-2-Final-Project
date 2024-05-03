import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileWorker {
    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                output.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return output;
    }

    public void writeFile(String data, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(data);
            fileWriter.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
