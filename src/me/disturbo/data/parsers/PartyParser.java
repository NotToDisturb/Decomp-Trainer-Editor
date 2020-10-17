package me.disturbo.data.parsers;

import me.disturbo.data.IndexedLineParser;
import me.disturbo.types.Party;
import me.disturbo.types.PartyMember;

import java.util.HashMap;

public class PartyParser implements IndexedLineParser<Party> {
    private final String flags, size;

    public PartyParser(String flags, String size){
        this.flags = flags;
        this.size = size;
    }

    @Override
    public Party parseObject(String name, String rawParty) {
        String partyType = rawParty.substring(rawParty.indexOf("TrainerMon"), rawParty.indexOf("Moves") + "Moves".length());
        String partyDeclaration = "staticconststruct" + partyType + name + "[]={{";
        rawParty = rawParty.replaceAll(System.lineSeparator(), "").replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
        rawParty = rawParty.substring(partyDeclaration.length(), rawParty.length() - 3);

        String[] partyMembers = rawParty.split("},\\{");
        Party party = new Party(flags, size, name);
        for(String member : partyMembers){
            HashMap<String, String> values = PartyMember.templateValues();
            int commaIndex = member.endsWith(",") ? 1 : 0;
            member = member.substring(1, member.length() - commaIndex); //Remove first "." and last ","
            String[] memberData = member.split(",\\.");
            for(String field : memberData){
                String key = field.substring(0, field.indexOf("="));
                String value = field.substring(field.indexOf("=") + 1);
                values.put(key, value);
            }
            party.add(new PartyMember(values));
        }
        return party;
    }
}
