package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.LinkedList;

public class ItemsParser implements LineParser<LinkedList<String>> {
    @Override
    public boolean parseLine(LinkedList<String> items, String line) {
        if(line.contains("ITEMS_COUNT")) return true;
        else if(line.contains("ITEM_")) items.add(line.split(" ")[1]);
        return false;
    }
}
