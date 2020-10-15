package ui.trainer;


import main.MainActivity;

import javax.swing.*;
import java.awt.*;

public class ItemsPanel extends JPanel {
    private ItemPanel[] items = new ItemPanel[MainActivity.ITEMS_MAX];

    public ItemsPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;

        cons.gridy++; add(Box.createVerticalStrut(30), cons);

        JLabel itemsLabel = new JLabel("Items: ");
        itemsLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; add(itemsLabel, cons);
        for(int index = 0; index < MainActivity.ITEMS_MAX; index++){
            ItemPanel itemPanel = new ItemPanel();
            items[index] = itemPanel;
            cons.gridy++; add(itemPanel, cons);

        }
        cons.gridy++; add(Box.createVerticalStrut(10), cons);
    }

    public void setSelectedItem(int index, String item){
        items[index].setSelectedItem(item);
    }

    public ItemPanel[] getSelectedItems(){
        return items;
    }
}
