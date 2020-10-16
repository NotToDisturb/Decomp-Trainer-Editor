package me.disturbo.ui.list;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.ui.MainFrame;
import me.disturbo.main.DataManager;

import javax.swing.*;

public class TrainerList extends JList<String> {
    /*
            The TrainerList class is a Swing component that lists all the trainers in the project and loads them upon selection
    */

    public TrainerList(MainFrame frame, String[] trainerNames){
        setPrototypeCellValue(Utils.getLongestString(trainerNames));
        setListData(trainerNames);
        setSelectedIndex(0);
        MainActivity.currentTrainer = getSelectedValue();
        MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));

        addListSelectionListener(event -> {
            if(!event.getValueIsAdjusting() && getSelectedIndex() != -1) {
                frame.saveTrainerData(MainActivity.currentTrainer);
                MainActivity.currentTrainer = getSelectedValue();
                if(!MainActivity.loadedTrainers.containsKey(MainActivity.currentTrainer))
                    MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));
                frame.loadTrainerData(MainActivity.currentTrainer);
                revalidate();
            }
        });
    }
}
