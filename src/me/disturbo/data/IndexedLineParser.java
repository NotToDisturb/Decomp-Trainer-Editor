package me.disturbo.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface IndexedLineParser<T> {
    default T parser(File file, LinkedHashMap<String, Integer> indexes, String key){
        LinkedList<String> keys = new LinkedList<>(indexes.keySet());
        int index = indexes.get(key);
        int nextIndex = keys.indexOf(key) + 1 < keys.size() ? indexes.get(keys.get(keys.indexOf(key) + 1)) : -1;

        String raw = "";
        int currentIndex = 0;

        FileReader fr = null; BufferedReader br = null;
        try {
            fr = new FileReader(file); br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null && (currentIndex < nextIndex || nextIndex == -1)) {
                line += System.lineSeparator();
                if(currentIndex >= index) raw += line;
                currentIndex += line.length();

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

        return parseObject(key, raw);
    }

    T parseObject(String key, String raw);
}
