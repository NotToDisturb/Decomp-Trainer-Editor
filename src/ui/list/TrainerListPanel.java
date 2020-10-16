package ui.list;

import main.MainActivity;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class TrainerListPanel extends JPanel {
    public TrainerListPanel(MainFrame frame){
        setLayout(new BorderLayout());
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.gridx = 0; cons.gridy = 0; cons.weighty = 0.1;
        cons.fill = GridBagConstraints.BOTH;
        TrainerList trainerList = new TrainerList(frame, MainActivity.trainerIndexes.keySet().toArray(new String[0]));
        listPanel.add(new JScrollPane(trainerList), cons);

        cons.gridy++; cons.weighty = 0;
        listPanel.add(Box.createVerticalStrut(5), cons);
        cons.gridy++; listPanel.add(new TrainerListSearchBar(trainerList), cons);

        add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        add(listPanel, BorderLayout.CENTER);
    }
}
