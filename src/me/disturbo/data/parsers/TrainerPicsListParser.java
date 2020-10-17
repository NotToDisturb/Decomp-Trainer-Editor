package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.LinkedList;

public class TrainerPicsListParser implements LineParser<LinkedList<String>> {
    @Override
    public boolean parseLine(LinkedList<String> picList, String line) {
        if(line.contains("TRAINER_PIC_")) picList.add(line.split(" ")[1]);
        return false;
    }
}
