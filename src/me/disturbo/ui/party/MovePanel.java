package me.disturbo.ui.party;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.ui.extensions.ComboBoxFiltered;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MovePanel extends JPanel {
    /*
            The MovesPanel class contains spacing and the combo box required for inputting moves
    */

    private final ComboBoxFiltered moveBox;

    public MovePanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;

        cons.gridy++; add(Box.createVerticalStrut(10), cons);

        cons.fill = GridBagConstraints.NONE;
        LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
        moveBox = new ComboBoxFiltered(values, values.get(0), new MovesFilter());
        moveBox.setPrototypeDisplayValue(Utils.getLongestString(values.toArray(new String[0])));
    }

    public final void setSelectedItem(String item){
        moveBox.setSelectedItem(item);
    }

    public final String getSelectedItem(){
        return moveBox.getSelectedItem().toString();
    }
}
