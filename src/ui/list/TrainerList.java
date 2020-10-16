package ui.list;

import main.MainActivity;
import main.Utils;
import ui.MainFrame;
import utils.DataManager;

import javax.swing.*;

public class TrainerList extends JList<String> {
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
