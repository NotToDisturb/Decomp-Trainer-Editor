package me.disturbo.ui.party;

import me.disturbo.main.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MovesPanel extends JPanel {
    /*
            The MovesPanel class contains MOVES_MAX MovePanels, one for each move of a pokemon
    */

    private MovePanel[] movesPanels = new MovePanel[MainActivity.MOVES_MAX];

    public MovesPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.LINE_START;

        JLabel movesLabel = new JLabel("Moves: ");
        movesLabel.setHorizontalAlignment(JLabel.LEFT);
        add(movesLabel, cons);

        for(int index = 0; index < MainActivity.MOVES_MAX; index++){
            MovePanel movePanel = new MovePanel();
            movesPanels[index] = movePanel;
            cons.gridy++; add(movePanel, cons);
        }
    }

    public String getMove(int index){
        return movesPanels[index].getSelectedItem().toString();
    }

    public void setMove(int index, String move){
        movesPanels[index].setSelectedItem(move);
    }
}
