package ui;

import main.MainActivity;
import main.Utils;
import ui.extensions.SimplifiedDocumentFilter;
import utils.DataManager;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class TrainerListInput extends JPanel {
    public TrainerListInput(){
        setLayout(new BorderLayout());
        JPanel search = new JPanel();
        search.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weighty = 0.1;
        cons.fill = GridBagConstraints.BOTH;
        JList trainers = new JList();
        createTrainerList(trainers);
        JScrollPane trainersScrollable = new JScrollPane(trainers);
        search.add(trainersScrollable, cons);
        createTrainerSearchBar(search, trainers, cons);
        add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        add(search, BorderLayout.CENTER);
    }

    public void createTrainerList(JList list){
        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        list.setPrototypeCellValue(Utils.getLongestString(keys));
        list.setListData(keys.toArray());
        list.setSelectedIndex(0);
        MainActivity.currentTrainer = list.getSelectedValue().toString();
        MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));
        list.addListSelectionListener(event -> {
            if(!event.getValueIsAdjusting() && list.getSelectedIndex() != -1) {
                MainActivity.screen.saveTrainerData(MainActivity.currentTrainer);
                MainActivity.currentTrainer = list.getSelectedValue().toString();
                if(!MainActivity.loadedTrainers.containsKey(MainActivity.currentTrainer))
                    MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));
                MainActivity.screen.loadTrainerData(MainActivity.currentTrainer);
                revalidate();
            }
        });
    }

    private void createTrainerSearchBar(JPanel pane, JList list, GridBagConstraints cons){
        cons.gridy++;
        cons.weighty = 0;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JTextField field = new JTextField();
        ((PlainDocument) field.getDocument()).setDocumentFilter(new SimplifiedDocumentFilter(){
            @Override
            protected boolean test(String text) {
                if(text.matches("[a-zA-Z_]*")){
                    list.setModel(new DefaultComboBoxModel(getFilteredTrainers(text)));
                    return true;
                }
                return false;
            }
        });
        pane.add(field, cons);
    }

    public Object[] getFilteredTrainers(String text){
        LinkedList<String> elements = new LinkedList<>();
        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        text = text.toLowerCase();
        for(String element : keys){
            if(element.toLowerCase().contains(text)){
                elements.add(element);
            }
        }
        return elements.toArray();
    }
}
