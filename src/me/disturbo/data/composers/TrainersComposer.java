package me.disturbo.data.composers;

import me.disturbo.data.DataManager;
import me.disturbo.data.IndexedOrderedComposer;
import me.disturbo.main.MainActivity;
import me.disturbo.types.Party;
import me.disturbo.types.Trainer;

import java.util.LinkedList;

public class TrainersComposer implements IndexedOrderedComposer<Trainer> {
    @Override
    public String getName(Trainer trainer) {
        return trainer.name;
    }

    @Override
    public String buildStruct(Trainer trainer, int nextTrainerIndex) {
        String trainerStruct = trainer.buildTrainerStruct();
        if(nextTrainerIndex == -1) trainerStruct += "};";
        return trainerStruct;
    }

    @Override
    public void finalize(LinkedList<Trainer> trainers) {
        DataManager.indexTrainers();
        LinkedList<Party> saveQueue = new LinkedList<>();
        for(Trainer trainer : trainers){
            if(saveQueue.isEmpty()) saveQueue.add(trainer.party);
            else {
                boolean inserted = false;
                for(int insertionIndex = 0; insertionIndex < saveQueue.size(); insertionIndex++){

                    int savingIndex = MainActivity.partyIndexes.get(trainer.party.name);
                    int savedIndex = MainActivity.partyIndexes.get(saveQueue.get(insertionIndex).name);
                    if(savingIndex < savedIndex){
                        saveQueue.add(insertionIndex, trainer.party);
                        inserted = true;
                        break;
                    }
                }
                if(!inserted) saveQueue.add(trainer.party);
            }
        }
        DataManager.saveParties(saveQueue);
    }
}
