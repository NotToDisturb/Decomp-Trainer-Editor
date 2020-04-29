package ui;

import main.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class SpeciesRenderer extends JLabel implements ListCellRenderer<String>{

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        setOpaque(true);
        String species = MainActivity.species.get(value);
        String text = species == null ? value : " " +  species;
        setText(text);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
