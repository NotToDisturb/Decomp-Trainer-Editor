package ui.party;

import main.MainActivity;
import main.Utils;
import ui.extensions.SpeciesFilter;
import ui.extensions.ComboBoxFiltered;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MovesPanel extends JPanel {
    private ComboBoxFiltered[] movesBoxes = new ComboBoxFiltered[MainActivity.MOVES_MAX];

    public MovesPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;


        JLabel movesLabel = new JLabel("Moves");
        movesLabel.setHorizontalAlignment(JLabel.LEFT);
        add(movesLabel, cons);

        LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
        for(int index = 0; index < MainActivity.MOVES_MAX; index++){
            cons.gridy++; add(Box.createVerticalStrut(10), cons);

            ComboBoxFiltered moveBox = new ComboBoxFiltered(values, values.get(0), new SpeciesFilter());
            moveBox.setPrototypeDisplayValue(Utils.getLongestString(values.toArray(new String[0])));
            movesBoxes[index] = moveBox;
            cons.gridy++; add(moveBox, cons);
        }

        cons.gridy++; add(Box.createVerticalStrut(10), cons);
    }

    public String getMove(int index){
        return movesBoxes[index].getSelectedItem().toString();
    }

    public void setMove(int index, String move){
        movesBoxes[index].setSelectedItem(move);
    }
}
