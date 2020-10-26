package me.disturbo.data.composers;

import me.disturbo.data.DataManager;
import me.disturbo.data.IndexedOrderedComposer;
import me.disturbo.main.MainActivity;
import me.disturbo.types.Party;
import me.disturbo.types.Trainer;

import java.util.LinkedList;

public class PartiesComposer implements IndexedOrderedComposer<Party> {
    @Override
    public String getName(Party party) {
        return party.name;
    }

    @Override
    public String buildStruct(Party party, int nextTrainerIndex) {
        return party.buildPartyStruct();
    }

    @Override
    public void finalize(LinkedList<Party> trainers) {
        MainActivity.partyIndexes = DataManager.indexParties();
    }
}
