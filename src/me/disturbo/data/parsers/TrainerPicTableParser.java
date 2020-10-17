package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TrainerPicTableParser implements LineParser<LinkedHashMap<String, String>> {
    @Override
    public boolean parseLine(LinkedHashMap<String, String> picAssociations, String line) {
        if(line.contains("TRAINER_SPRITE")){
            line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
            String[] association = line.substring("TRAINER_SPRITE(".length(), line.length() - 2).split(",");
            picAssociations.put(association[1], "TRAINER_PIC_" + association[0]);
        }
        return false;
    }
}
