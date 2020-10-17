package me.disturbo.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface LineParser<T>{
    default T parse(File file, T output){
        FileReader fr = null; BufferedReader br = null;
        try {
            fr = new FileReader(file); br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(parseLine(output, line)) break;
            }
            fr.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close(); br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }

        }
        return output;
    }

    // If the result of this function is true no more lines will be read
    boolean parseLine(T output, String line);
}
