package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.LinkedList;

public class AiFlagsParser implements LineParser<LinkedList<String>> {
    @Override
    public boolean parseLine(LinkedList<String> aiFlags, String line) {
        if(line.contains("AI_FLAG_")) aiFlags.add(line.split(" ")[1]);
        return false;
    }
}
