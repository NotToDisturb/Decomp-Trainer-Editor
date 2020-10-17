package me.disturbo.data;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface IndexedOrderedComposer<T> {
    default void compose(File file, LinkedList<T> ordered, LinkedHashMap<String, Integer> indexes){
        LinkedList<String> keys = new LinkedList<>(indexes.keySet());
        int orderedIndex = 0;
        String name = getName(ordered.get(orderedIndex));
        int index = indexes.get(name),
            nextIndex = keys.indexOf(name) + 1 < keys.size() ? indexes.get(keys.get(keys.indexOf(name) + 1)) : -1,
            currentIndex = 0;

        String composed = "";

        FileReader fr = null; BufferedReader br = null;
        try {
            fr = new FileReader(file); br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                line += System.lineSeparator();
                boolean appended = false;
                while(!appended){
                    // If the current target has been passed and there is a another one
                    if(currentIndex >= nextIndex && nextIndex > -1 && index != - 1){
                        orderedIndex++;
                        if(orderedIndex < ordered.size()) {
                            name = getName(ordered.get(orderedIndex));
                            index = indexes.get(name);
                            nextIndex = keys.indexOf(name) + 1 < keys.size() ? indexes.get(keys.get(keys.indexOf(name) + 1)) : -1;
                        }
                        // No more left to save
                        else index = -1;
                    }
                    else{
                        if(currentIndex < index || index == - 1) composed += line;
                        // If the target starts here
                        else if(currentIndex == index) composed += buildStruct(ordered.get(orderedIndex), nextIndex);
                        appended = true;
                    }
                }
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

        FileWriter wr = null;
        try {
            wr = new FileWriter(file);
            wr.write(composed);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                wr.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        finalize(ordered);
    }

    String getName(T type);

    String buildStruct(T type, int nextIndex);

    void finalize(LinkedList<T> ordered);
}
