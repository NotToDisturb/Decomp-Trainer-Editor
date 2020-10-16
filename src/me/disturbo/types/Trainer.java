package me.disturbo.types;

import me.disturbo.main.MainActivity;
import me.disturbo.main.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Trainer {
    /*
            The Trainer class manages the information of a trainer
             - <fields> serves as a collection of trainer attributes, each one having a corresponding field

            The most notable methods of this class are:
             - extractGender: the gender is stored alongside the music track, so it is derived from the music
             - buildMusicGender: as with the previous method, music and gender and built together
             - buildTrainerItems: creates a string with all the items while accounting for the possibility of all of them being ITEM_NONE
    */


    public static final String[] fields = {"partyFlags", "trainerClass", "encounterMusic_gender", "trainerPic", "trainerName",
                                           "items", "doubleBattle", "aiFlags", "partySize", "party"};


    public String key, partyFlags, trainerClass, music, gender, trainerPic, trainerName, partySize, partyName;
    public ArrayList<String> items;
    public boolean doubleBattle;
    public ArrayList<String> aiFlags;
    public LinkedList<PartyMember> party;

    public Trainer(String key, HashMap<String, String> values){
        this.key = key;
        partyFlags = values.get("partyFlags");
        trainerClass = values.get("trainerClass");
        music = extractMusic(values.get("encounterMusic_gender"));
        gender = extractGender(values.get("encounterMusic_gender"));
        trainerPic = values.get("trainerPic");
        trainerName = extractTrainerName(values.get("trainerName"));
        items = extractItems(values.get("items"));
        doubleBattle = Boolean.parseBoolean(values.get("doubleBattle"));
        aiFlags = extractAiFlags(values.get("aiFlags"));
        partySize = values.get("partySize");
        partyName = extractPartyName(values.get("party"));
        party = DataManager.loadParty(partyName);
    }

    public static final HashMap<String, String> templateValues(){
        HashMap<String, String> template = new HashMap<>();
        for(String field: fields){
            template.put(field, "");
        }
        return template;
    }

    private final String extractTrainerName(String name){
        return name.replace("\"", "").replace("_(", "").replace(")", "");
    }

    private final String buildTrainerName(){
        return "_(\"" + trainerName + "\")";
    }

    private final String extractMusic(String music){
        if(music.contains("|")) return music.split("\\|")[1];
        else return music;
    }

    private final String extractGender(String music){
        if(music.contains("|")) return music.split("\\|")[0];
        else return "";
    }

    private final String buildMusicGender(){
        String musicGender = "";
        if(!gender.equals("")) musicGender += gender + " | ";
        return musicGender + music;
    }

    private final ArrayList<String> extractItems(String items){
        // If no items
        if(items.equals("{}")) return new ArrayList<>();
        items = items.replace("{", "").replace("}", "").replaceAll(" ", "");
        return new ArrayList<>(Arrays.asList(items.split(",")));
    }

    private final String buildTrainerItems(){
        String items = "";
        boolean areAllItemsNone = true;
        for (int index = 0; index < this.items.size(); index++) {
            String item = this.items.get(index);
            if(!item.equals(MainActivity.items.get(0))) areAllItemsNone = false;
            items += item;
            if (index != this.items.size() - 1) {
                items += ", ";
            }
        }
        if(areAllItemsNone) items = "";
        return "{" + items + "}";
    }

    private final String buildDoubleBattle(){
        if(doubleBattle) return "TRUE";
        else return "FALSE";
    }

    private final ArrayList<String> extractAiFlags(String flags){
        return new ArrayList<>(Arrays.asList(flags.split("\\|")));
    }
    private final String buildAiFlags(){
        String flags = "";
        for(int index = 0; index < aiFlags.size(); index++){
            flags += aiFlags.get(index);
            if(index != aiFlags.size() - 1){
                flags += " | ";
            }
        }
        if(flags.equals("")) flags = "0";
        return flags;
    }

    private final String extractPartyName(String name){
        return name.split("=")[1].replace("}", "");
    }

    private final String buildPartyName(){
        return "{." + getPartyType().substring("TrainerMon".length()) + " = " + partyName + "}";
    }

    public final String getPartyType(){
        String partyType = "TrainerMon";
        if(!partyHasCustomItems()) partyType += "No";
        partyType += "Item";
        if(!partyHasCustomMoves()) partyType += "Default";
        else partyType += "Custom";
        partyType += "Moves";
        return partyType;
    }

    public final String getPartyFlags(){
        String partyType = "0";
        if(partyHasCustomItems()) partyType = "F_TRAINER_PARTY_HELD_ITEM";
        if(partyHasCustomMoves()){
            if(partyType.equals("0")) partyType = "F_TRAINER_PARTY_CUSTOM_MOVESET";
            else partyType += " | F_TRAINER_PARTY_CUSTOM_MOVESET";
        }
        return partyType;
    }

    public final boolean partyHasCustomMoves(){
        boolean hasCustomMoves = false;
        for(PartyMember member : party){
            if(member.hasCustomMoves()){
                hasCustomMoves = true;
                break;
            }
        }
        return hasCustomMoves;
    }

    public final boolean partyHasCustomItems(){
        boolean hasCustomItems = false;
        for(PartyMember member : party){
            if(member.heldItem != MainActivity.items.get(0)){
                hasCustomItems = true;
                break;
            }
        }
        return hasCustomItems;
    }

    public final String buildTrainerStruct(){
        String struct = "    [" + key + "] =" + System.lineSeparator();
        struct += "    {" + System.lineSeparator();
        struct += "        .partyFlags = " + getPartyFlags() + "," + System.lineSeparator();
        struct += "        .trainerClass = " + trainerClass + "," + System.lineSeparator();
        struct += "        .encounterMusic_gender = " + buildMusicGender() + "," + System.lineSeparator();
        struct += "        .trainerPic = " + trainerPic + "," + System.lineSeparator();
        struct += "        .trainerName = " + buildTrainerName() + "," + System.lineSeparator();
        struct += "        .items = " + buildTrainerItems() + "," + System.lineSeparator();
        struct += "        .doubleBattle = " + buildDoubleBattle() + "," + System.lineSeparator();
        struct += "        .aiFlags = " + buildAiFlags() + "," + System.lineSeparator();
        struct += "        .partySize = " + partySize + "," + System.lineSeparator();
        struct += "        .party = " + buildPartyName() + "," + System.lineSeparator();
        struct += "    }," + System.lineSeparator() + System.lineSeparator();
        return struct;
    }

    public final String buildPartyStruct(){
        String struct = "static const struct " + getPartyType() + " " + partyName + "[] = {" + System.lineSeparator();
        for(int index = 0; index < party.size(); index++){
            struct += party.get(index).buildMemberStruct(partyHasCustomItems(), partyHasCustomMoves());
            if(index < party.size() - 1) struct += ",";
            struct += System.lineSeparator();
        }
        struct += "};" + System.lineSeparator();
        return struct;
    }
}
