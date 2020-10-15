package ui.trainer;

import main.MainActivity;
import main.Utils;
import ui.extensions.ComboBoxFiltered;

import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    private final ComboBoxFiltered itemInput;

    public ItemPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;

        cons.gridy++; add(Box.createVerticalStrut(5), cons);

        cons.fill = GridBagConstraints.NONE;
        itemInput = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0));
        itemInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items.toArray(new String[0])));
        cons.gridy++; add(itemInput, cons);
    }

    public void setSelectedItem(String item){
        itemInput.setSelectedItem(item);
    }

    public String getSelectedItem(){
        return itemInput.getSelectedItem().toString();
    }
}
