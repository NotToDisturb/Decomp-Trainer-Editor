package types;

import main.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class PartyMember {
    public static final String[] fields = {"iv", "lvl", "species", "heldItem", "moves"};

    public String iv;
    public String level;
    public String species;
    public String heldItem;
    public ArrayList<String> moves;

    public PartyMember(){
        iv = "0";
        level = "0";
        LinkedList<String> keys = new LinkedList<>(MainActivity.species.keySet());
        species = keys.get(0);
        heldItem = MainActivity.items.get(0);
        moves = new ArrayList<>();
    }

    public PartyMember(HashMap<String, String> values){
        iv = values.get("iv");
        level = values.get("lvl");
        species = values.get("species");
        heldItem = values.get("heldItem");
        moves = extractMoves(values.get("moves"));
    }

    public static HashMap<String, String> templateValues(){
        HashMap<String, String> template = new HashMap<>();
        for(String field: fields){
            template.put(field, "");
        }
        return template;
    }

    public static ArrayList<String> extractMoves(String moves){
        if(moves.length() == 0) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(moves.substring(1, moves.length() - 1).split(",")));
    }

    private String buildMoves(){
        String moves = "";
        for (int index = 0; index < this.moves.size(); index++) {
            moves += this.moves.get(index);
            if (index != this.moves.size() - 1) {
                moves += ", ";
            }
        }
        return "{" + moves + "}";
    }

    public boolean hasCustomMoves(){
        boolean hasCustomMoves = false;
        LinkedList<String> keys = new LinkedList<>(MainActivity.moves.keySet());
        for (int index = 0; index < this.moves.size(); index++) {
            if(!moves.get(index).equals(keys.get(0))) hasCustomMoves = true;
        }
        return hasCustomMoves;
    }

    public String buildMemberStruct(boolean partyHasCustomItems, boolean partyHasCustomMoves){
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
