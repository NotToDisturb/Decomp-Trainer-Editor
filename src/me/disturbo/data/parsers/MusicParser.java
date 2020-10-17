package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.LinkedList;

public class MusicParser implements LineParser<LinkedList<String>> {
    @Override
    public boolean parseLine(LinkedList<String> music, String line) {
        if(line.contains("TRAINER_ENCOUNTER_MUSIC_")) music.add(line.split(" ")[1]);
        return false;
    }
}
