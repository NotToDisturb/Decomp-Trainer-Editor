package ui.extensions;

import main.MainActivity;
import types.PartyMember;

import javax.swing.*;
import java.awt.*;

public class SpeciesRenderer extends JLabel implements ListCellRenderer<PartyMember>{

    @Override
    public Component getListCellRendererComponent(JList<? extends PartyMember> list, PartyMember value, int index, boolean isSelected, boolean cellHasFocus) {
        setOpaque(true);
        String species = MainActivity.species.get(value.species);
        String text = species == null ? value.species : " " +  species;
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

/*public class SpeciesRenderer extends JLabel implements ListCellRenderer<String>{

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
}*/
