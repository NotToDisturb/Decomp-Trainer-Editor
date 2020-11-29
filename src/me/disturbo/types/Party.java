package me.disturbo.types;

import me.disturbo.main.MainActivity;

import java.util.LinkedList;

public class Party extends LinkedList<PartyMember> {
    public String flags, size, name;

    public Party(String flags, String size, String name){
        this.flags = flags;
        this.size = size;
        this.name = name;
    }

    public static final String extractPartyName(String name){
        return name.split("=")[1].replace("}", "");
    }

    final String buildPartyName(){
        return "{." + getPartyType().substring("TrainerMon".length()) + " = " + name + "}";
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
        for(PartyMember member : this){
            if(member.hasCustomMoves()) return true;
        }
        return false;
    }

    public final boolean partyHasCustomItems(){
        String noItem = MainActivity.items.get(0);
        for(PartyMember member : this){
            if(!member.heldItem.equals(noItem)) return true;
        }
        return false;
    }

    public final String buildPartyStruct(){
        String struct = "static const struct " + getPartyType() + " " + name + "[] = {" + System.lineSeparator();
        for(int index = 0; index < size(); index++){
            struct += get(index).buildMemberStruct(partyHasCustomItems(), partyHasCustomMoves());
            if(index < size() - 1) struct += ",";
            struct += System.lineSeparator();
        }
        struct += "};" + System.lineSeparator() + System.lineSeparator();
        return struct;
    }
}
