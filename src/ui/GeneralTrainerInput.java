package ui;

import main.MainActivity;
import main.Utils;
import types.Trainer;
import ui.extensions.ComboBoxFiltered;
import ui.extensions.ImagePanel;
import ui.extensions.TextFieldLimiter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class GeneralTrainerInput extends JPanel {
    public JTextField nameInput;
    public ComboBoxFiltered classInput;
    public JCheckBox doubleBattleCheck;
    public LinkedList<ComboBoxFiltered> itemsInput = new LinkedList<>();
    public JRadioButton maleButton;
    public JRadioButton femaleButton;
    public ComboBoxFiltered musicInput;
    public ImagePanel picDisplay;
    public ComboBoxFiltered picInput;

    public GeneralTrainerInput(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(5));
        createGeneralLeftColumn();
        add(Box.createHorizontalStrut(30));
        createGeneralRightColumn();
    }

    public void createGeneralLeftColumn(){
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

    public void createGeneralRightColumn(){
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

    public void createNameInput(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(nameLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        cons.fill = GridBagConstraints.NONE;
        nameInput = new JTextField(11);
        nameInput.setDocument(new TextFieldLimiter(11, -1));
        pane.add(nameInput, cons);
    }

    public void createTrainerClassInput(JPanel pane, GridBagConstraints cons){
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
        classInput = new ComboBoxFiltered(values, "");
        classInput.setPrototypeDisplayValue(Utils.getLongestString(new ArrayList<>(MainActivity.trainerClasses.keySet())));
        pane.add(classInput, cons);
    }

    public void createDoubleBattleInput(JPanel pane, GridBagConstraints cons){
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

    public void createItemsInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel itemsLabel = new JLabel("Items: ");
        itemsLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(itemsLabel, cons);
        for(int index = 0; index < 4; index++){
            cons.gridy++;
            pane.add(Box.createVerticalStrut(5), cons);

            cons.gridy++;
            cons.fill = GridBagConstraints.NONE;
            ComboBoxFiltered itemInput = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0));
            itemInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items));
            pane.add(itemInput, cons);
            itemsInput.add(itemInput);
        }
        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);
    }

    public void createTrainerPicInput(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel picLabel = new JLabel("Trainer pic: ");
        picLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(picLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);

        cons.gridy++;
        picDisplay = new ImagePanel();
        picDisplay.setBackground(Color.WHITE);
        pane.add(picDisplay, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);

        cons.gridy++;
        picInput = new ComboBoxFiltered(MainActivity.picList, "");
        picInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.picList));
        picInput.addActionListener(e -> {
            picDisplay.imagePath = MainActivity.picPaths.get(picInput.getSelectedItem());
            picDisplay.repaint();
        });
        pane.add(picInput, cons);
    }

    public void createGenderInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(genderLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        maleButton = new JRadioButton("Male");
        maleButton.setBackground(Color.WHITE);
        maleButton.addActionListener(event -> {
            femaleButton.setSelected(false);
        });
        pane.add(maleButton, cons);

        cons.gridy++;
        femaleButton = new JRadioButton("Female");
        femaleButton.setBackground(Color.WHITE);
        femaleButton.addActionListener(event -> {
            maleButton.setSelected(false);
        });
        pane.add(femaleButton, cons);
    }

    public void createMusicInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel musicLabel = new JLabel("Encounter music: ");
        musicLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(musicLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        musicInput = new ComboBoxFiltered(MainActivity.music, "");
        musicInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.music));
        pane.add(musicInput, cons);
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
    }

    public void loadTrainerGeneralData(Trainer trainer){
        nameInput.setText(trainer.trainerName);
        classInput.setSelectedItem(trainer.trainerClass);
        doubleBattleCheck.setSelected(trainer.doubleBattle);
        picDisplay.imagePath = MainActivity.picPaths.get(trainer.trainerPic);
        picInput.setSelectedItem(trainer.trainerPic);
        musicInput.setSelectedItem(trainer.music);
        boolean male = trainer.gender.equals("");
        maleButton.setSelected(male);
        femaleButton.setSelected(!male);
        for(int index = 0; index < 4; index++){
            String item = MainActivity.items.get(0);
            if(trainer.items.size() > 0){
                item = trainer.items.get(index);
            }
            itemsInput.get(index).setSelectedItem(item);
        }
    }

    public void saveTrainerGeneralData(Trainer trainer){
        trainer.trainerName = nameInput.getText();
        trainer.trainerClass = classInput.getSelectedItem().toString();
        trainer.doubleBattle = doubleBattleCheck.isSelected();
        trainer.trainerPic = picInput.getSelectedItem().toString();
        trainer.music = musicInput.getSelectedItem().toString();
        String gender = "";
        if(femaleButton.isSelected()) gender += "F_TRAINER_FEMALE";
        trainer.gender = gender;

        ArrayList<String> items = new ArrayList<>();
        boolean areAllItemsNone = true;
        for(ComboBoxFiltered item : itemsInput){
            JTextField field = (JTextField) item.getEditor().getEditorComponent();
            if(!field.getText().equals(MainActivity.items.get(0))) areAllItemsNone = false;
            items.add(field.getText());
        }
        if(areAllItemsNone) items = new ArrayList<>();
        trainer.items = items;
    }
}
