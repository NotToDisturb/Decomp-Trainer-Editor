package me.disturbo.ui.trainer;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.ui.extensions.AlphanumericUnderscoreFilter;
import me.disturbo.ui.party.MovesFilter;
import me.disturbo.ui.extensions.ComboBoxFiltered;
import me.disturbo.ui.extensions.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class TrainerPicPanel extends JPanel {
    /*
            The TrainerPicPanel contains a picture display panel and a picture input field
            - The picBox updates the picPanel every time a new picture is selected
    */

    private ImagePanel picPanel;
    private ComboBoxFiltered picBox;
    
    public TrainerPicPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        
        add(Box.createVerticalStrut(30), cons);
        
        JLabel picLabel = new JLabel("Trainer pic: ");
        picLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++;add(picLabel, cons);
        
        cons.gridy++; add(Box.createVerticalStrut(10), cons);
        
        picPanel = new ImagePanel();
        picPanel.setBackground(Color.WHITE);
        cons.gridy++; add(picPanel, cons);
        
        cons.gridy++; add(Box.createVerticalStrut(10), cons);

        
        picBox = new ComboBoxFiltered(MainActivity.picList, new AlphanumericUnderscoreFilter());
        picBox.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.picList.toArray(new String[0])));
        cons.gridy++; add(picBox, cons);

        addFieldListener();
    }
    
    private final void addFieldListener(){
        picBox.addActionListener(e -> {
            picPanel.setImage(MainActivity.picPaths.get(picBox.getSelectedItem()));
            picPanel.repaint();
        });
    }
    
    public final void setImage(String path){
        picPanel.setImage(path);
    }
    
    public final void setSelectedPic(String pic){
        picBox.setSelectedItem(pic);
    }

    public final String getSelectedPic(){
        return picBox.getSelectedItem().toString();
    }
}
