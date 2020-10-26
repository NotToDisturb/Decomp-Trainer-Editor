package me.disturbo.types;

import me.disturbo.main.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class PartyMember {
    /*
            The Trainer class manages the information of each pokemon
             - <fields> serves as a collection of pokemon attributes, each one having a corresponding field
    */

    public static final String[] fields = {"iv", "lvl", "species", "heldItem", "moves"};

    public String iv;
    public String level;
    public String species;
    public String heldItem;
    public ArrayList<String> moves;

    public PartyMember(String species){
        iv = "0";
        level = "0";
        LinkedList<String> keys = new LinkedList<>(MainActivity.species.keySet());
        this.species = species;
        heldItem = MainActivity.items.get(0);
        moves = new ArrayList<>();
    }

    public PartyMember(){
        this((new LinkedList<>(MainActivity.species.keySet())).get(0));
    }


    public PartyMember(HashMap<String, String> values){
        iv = values.get("iv");
        level = values.get("lvl");
        species = values.get("species");
        heldItem = values.get("heldItem");
        moves = extractMoves(values.get("moves"));
    }

    public static final HashMap<String, String> templateValues(){
        HashMap<String, String> template = new HashMap<>();
        for(String field: fields){
            template.put(field, "");
        }
        return template;
    }

    public static final ArrayList<String> extractMoves(String moves){
        if(moves.length() == 0) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(moves.substring(1, moves.length() - 1).split(",")));
    }

    private final String buildMoves(){
        String moves = "";
        for (int index = 0; index < this.moves.size(); index++) {
            moves += this.moves.get(index);
            if (index != this.moves.size() - 1) moves += ", ";
        }
        return "{" + moves + "}";
    }

    public final boolean hasCustomMoves(){
        boolean hasCustomMoves = false;
        for (int index = 0; index < this.moves.size(); index++) {
            if(!moves.get(index).equals(MainActivity.moves.keySet().toArray(new String[0])[0])) hasCustomMoves = true;
        }
        return hasCustomMoves;
    }

    public final String buildMemberStruct(boolean partyHasCustomItems, boolean partyHasCustomMoves){
        String struct = "    {" + System.lineSeparator();
        struct += "    .iv = " + iv + "," + System.lineSeparator();
        struct += "    .lvl = " + level + "," + System.lineSeparator();
        String speciesBuild = "    .species = " + this.species;
        String heldItemBuild = "    .heldItem = " + this.heldItem;
        String movesBuild = "    .moves = " + buildMoves();
        struct += speciesBuild + "," + System.lineSeparator();
        if(partyHasCustomItems) {
            if (!partyHasCustomMoves) struct += heldItemBuild + System.lineSeparator();
            else{
                struct += heldItemBuild + "," + System.lineSeparator();
                struct += movesBuild + System.lineSeparator();
            }
        }
        else if (partyHasCustomMoves) struct += movesBuild + System.lineSeparator();
        struct += "    }";
        return struct;
    }
}
