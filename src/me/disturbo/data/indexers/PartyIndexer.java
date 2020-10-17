package me.disturbo.data.indexers;

import me.disturbo.data.LineParser;

import java.util.LinkedHashMap;

public class PartyIndexer implements LineParser<LinkedHashMap<String, Integer>> {
    private int currentIndex = 0;

    @Override
    public boolean parseLine(LinkedHashMap<String, Integer> partyIndexes, String line) {
        if(line.contains("static")){
            String partyName = line.substring(line.indexOf("sParty"), line.indexOf("[")).replace(" ", "");
            partyIndexes.put(partyName, currentIndex);
        }
        currentIndex += (line + System.lineSeparator()).length();
        return false;
    }
}
