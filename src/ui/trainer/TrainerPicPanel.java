package ui.trainer;

import main.MainActivity;
import main.Utils;
import ui.extensions.ComboBoxFiltered;
import ui.extensions.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class TrainerPicPanel extends JPanel {
    private ImagePanel picPanel;
    private ComboBoxFiltered picCombo;
    
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

        
        picCombo = new ComboBoxFiltered(MainActivity.picList);
        picCombo.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.picList.toArray(new String[0])));
        cons.gridy++; add(picCombo, cons);

        addFieldListener();
    }
    
    private final void addFieldListener(){
        picCombo.addActionListener(e -> {
            picPanel.setImage(MainActivity.picPaths.get(picCombo.getSelectedItem()));
            picPanel.repaint();
        });
    }
    
    public void setImage(String path){
        picPanel.setImage(path);
    }
    
    public void setSelectedPic(String pic){
        picCombo.setSelectedItem(pic);
    }

    public String getSelectedPic(){
        return picCombo.getSelectedItem().toString();
    }
}
