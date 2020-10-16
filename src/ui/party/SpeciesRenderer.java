package ui.party;

import main.MainActivity;
import types.PartyMember;

import javax.swing.*;
import java.awt.*;

public class SpeciesRenderer extends JLabel implements ListCellRenderer<PartyMember>{
    private final PartyPanel panel;

    public SpeciesRenderer(PartyPanel panel){
        this.panel = panel;
    }

    @Override
    public final Component getListCellRendererComponent(JList<? extends PartyMember> list, PartyMember value, int index, boolean isSelected, boolean cellHasFocus) {
        setOpaque(true);

        String display;
        String savedSpecies = MainActivity.species.get(value.species);

        if(value == panel.getCurrentLoaded()){
            String species = MainActivity.species.get(panel.getSpeciesText());
            if(species != null) display = " " + species;
            else{
                if(savedSpecies != null) display = " " + savedSpecies;
                else display = " " + value.species;
            }
        }
        else display = savedSpecies != null ? " " + savedSpecies : " " + value.species;

        setText(display);

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
