package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MovesParser implements LineParser<LinkedHashMap<String, String>> {

    @Override
    public boolean parseLine(LinkedHashMap<String, String> moves, String line) {
        if(line.contains("[MOVE_") && !line.contains("[MOVE_NAME_LENGTH")) {
            line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
            String declaration = line.substring(1, line.indexOf("]"));
            String name = line.substring(line.indexOf("_(\"") + 3, line.indexOf("\")"));
            moves.put(declaration, name);
        }
        return false;
    }
}
