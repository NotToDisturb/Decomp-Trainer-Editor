package me.disturbo.ui.trainer;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.ui.extensions.AlphanumericUnderscoreFilter;
import me.disturbo.ui.extensions.ComboBoxFiltered;

import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    /*
            The MovesPanel class contains spacing and the combo box required for inputting trainer items
    */

    private final ComboBoxFiltered itemBox;

    public ItemPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;

        cons.gridy++; add(Box.createVerticalStrut(5), cons);

        cons.fill = GridBagConstraints.NONE;
        itemBox = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0), new AlphanumericUnderscoreFilter());
        itemBox.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items.toArray(new String[0])));
        cons.gridy++; add(itemBox, cons);
    }

    public final void setSelectedItem(String item){
        itemBox.setSelectedItem(item);
    }

    public final String getSelectedItem(){
        return itemBox.getSelectedItem().toString();
    }
}
