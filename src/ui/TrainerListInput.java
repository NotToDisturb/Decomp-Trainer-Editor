package ui;

import main.MainActivity;
import main.Utils;
import utils.DataManager;

import javax.swing.*;
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
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
                String previousText, text;
                previousText = text = field.getText();
                if(String.valueOf(event.getKeyChar()).matches("[a-zA-Z]") || String.valueOf(event.getKeyChar()).equals("_")) text += event.getKeyChar();
                Object[] filtered = getFilteredTrainers(text);
                if(filtered.length == 0) SwingUtilities.invokeLater(() -> field.setText(previousText));
                else{
                    list.setModel(new DefaultComboBoxModel(filtered));
                    field.setText(previousText);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
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
