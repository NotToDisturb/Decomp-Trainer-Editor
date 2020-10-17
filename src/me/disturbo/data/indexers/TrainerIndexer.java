package me.disturbo.data.indexers;

import me.disturbo.data.LineParser;

import java.util.LinkedHashMap;

public class TrainerIndexer implements LineParser<LinkedHashMap<String, Integer>> {
    private int currentIndex = 0;

    @Override
    public boolean parseLine(LinkedHashMap<String, Integer> trainerIndexes, String line) {
        if(line.contains("[TRAINER_") && !line.contains("[TRAINER_NONE]")){
            String trainerName = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            trainerIndexes.put(trainerName, currentIndex);
        }
        currentIndex += (line + System.lineSeparator()).length();
        return false;
    }
}
