package me.disturbo.ui.trainer;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.types.Trainer;
import me.disturbo.ui.party.MovesFilter;
import me.disturbo.ui.extensions.ComboBoxFiltered;
import me.disturbo.ui.extensions.TextFieldLimiter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class TrainerPanel extends JPanel {
    /*
            The TrainerPanel class contains Swing objects that allow for editing the fields of a Trainer object
    */

    private JTextField nameInput;
    private ComboBoxFiltered classInput;
    private JCheckBox doubleBattleCheck;
    private ItemsPanel itemsPanel;
    private GenderPanel genderPanel;
    private ComboBoxFiltered musicInput;
    private TrainerPicPanel picPanel;

    public TrainerPanel(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(5));
        createGeneralLeftColumn();
        add(Box.createHorizontalStrut(30));
        createGeneralRightColumn();
    }

    private final void createGeneralLeftColumn(){
        JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        createNameInput(column, cons);
        createTrainerClassInput(column, cons);
        createDoubleBattleInput(column, cons);
        createItemsInput(column, cons);
        add(column);
    }

    private final void createGeneralRightColumn(){
        JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        createTrainerPicInput(column, cons);
        createGenderInput(column, cons);
        createMusicInput(column, cons);
        add(column);
    }

    private final void createNameInput(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(nameLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        cons.fill = GridBagConstraints.NONE;
        nameInput = new JTextField(MainActivity.NAME_MAX);
        nameInput.setDocument(new TextFieldLimiter(MainActivity.NAME_MAX, -1));
        pane.add(nameInput, cons);
    }

    private final void createTrainerClassInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        cons.fill = GridBagConstraints.BOTH;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel classLabel = new JLabel("Trainer class: ");
        classLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(classLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        LinkedList<String> values = new LinkedList<>(MainActivity.trainerClasses.keySet());
        classInput = new ComboBoxFiltered(values, new MovesFilter());
        classInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.trainerClasses.keySet().toArray(new String[0])));
        pane.add(classInput, cons);
    }

    private final void createDoubleBattleInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JPanel doubleBattlePanel = new JPanel();
        doubleBattlePanel.setBackground(Color.WHITE);
        doubleBattlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        doubleBattleCheck = new JCheckBox();
        doubleBattleCheck.setBackground(Color.WHITE);
        JLabel doubleBattleLabel = new JLabel("Double battle");
        doubleBattleLabel.setHorizontalAlignment(JLabel.LEFT);
        doubleBattlePanel.add(doubleBattleCheck); doubleBattlePanel.add(doubleBattleLabel);
        pane.add(doubleBattlePanel, cons);
    }

    private final void createItemsInput(JPanel pane, GridBagConstraints cons){
        itemsPanel = new ItemsPanel();
        cons.gridy++; pane.add(itemsPanel, cons);
    }

    private final void createTrainerPicInput(JPanel pane, GridBagConstraints cons){
        picPanel = new TrainerPicPanel();
        cons.gridy++; pane.add(picPanel, cons);
    }

    private final void createGenderInput(JPanel pane, GridBagConstraints cons){
        genderPanel = new GenderPanel();
        cons.gridy++; pane.add(genderPanel, cons);
    }

    private final void createMusicInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel musicLabel = new JLabel("Encounter music: ");
        musicLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(musicLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        musicInput = new ComboBoxFiltered(MainActivity.music, new MovesFilter());
        musicInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.music.toArray(new String[0])));
        pane.add(musicInput, cons);
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
    }

    public final void loadTrainerGeneralData(Trainer trainer){
        nameInput.setText(trainer.trainerName);
        classInput.setSelectedItem(trainer.trainerClass);
        doubleBattleCheck.setSelected(trainer.doubleBattle);
        picPanel.setImage(MainActivity.picPaths.get(trainer.trainerPic));
        picPanel.setSelectedPic(trainer.trainerPic);
        musicInput.setSelectedItem(trainer.music);
        genderPanel.doSelection(trainer.gender);
        for(int index = 0; index < MainActivity.ITEMS_MAX; index++){
            String item = trainer.items.size() > 0 ? trainer.items.get(index) : MainActivity.items.get(0);
            itemsPanel.setSelectedItem(index, item);
        }
    }

    public final void saveTrainerGeneralData(Trainer trainer){
        trainer.trainerName = nameInput.getText();
        trainer.trainerClass = classInput.getSelectedItem().toString();
        trainer.doubleBattle = doubleBattleCheck.isSelected();
        trainer.trainerPic = picPanel.getSelectedPic();
        trainer.music = musicInput.getSelectedItem().toString();
        trainer.gender = genderPanel.getGender();

        ArrayList<String> items = new ArrayList<>();
        boolean areAllItemsNone = true;
        for(ItemPanel itemPanel : itemsPanel.getSelectedItems()){
            String item = itemPanel.getSelectedItem();
            if(!item.equals(MainActivity.items.get(0))) areAllItemsNone = false;
            items.add(item);
        }
        trainer.items = areAllItemsNone ? new ArrayList<>() : items;
    }
}
