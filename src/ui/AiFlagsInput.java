package ui;

import main.MainActivity;
import types.Trainer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AiFlagsInput extends JPanel {
    private LinkedHashMap<String, JCheckBox> aiFlags = new LinkedHashMap<>();

    public AiFlagsInput(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(5));
        JScrollPane scroll = new JScrollPane(this);
        scroll.setBorder(null);
        JPanel aiList = new JPanel();
        aiList.setBackground(Color.WHITE);
        aiList.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        aiList.add(Box.createVerticalStrut(5), cons);
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String ai = MainActivity.aiFlags.get(index);
            JPanel aiPanel = new JPanel();
            aiPanel.setBackground(Color.WHITE);
            aiPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JCheckBox aiCheck = new JCheckBox();
            aiCheck.setBackground(Color.WHITE);

            JLabel aiLabel = new JLabel(ai);
            aiLabel.setHorizontalAlignment(JLabel.LEFT);
            aiPanel.add(aiCheck); aiPanel.add(aiLabel);
            cons.gridy++;
            aiList.add(aiPanel, cons);
            aiFlags.put(ai, aiCheck);
        }
        cons.gridy++;
        aiList.add(Box.createVerticalStrut(10), cons);
        add(aiList, BorderLayout.CENTER);
    }

    public void loadTrainerAiData(Trainer trainer){
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            aiFlags.get(flag).setSelected(trainer.aiFlags.contains(flag));
        }
    }

    public void saveTrainerAiData(Trainer trainer){
        ArrayList<String> aiFlags = new ArrayList<>();
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            if(this.aiFlags.get(flag ).isSelected()) aiFlags.add(flag);
        }
        trainer.aiFlags = aiFlags;
    }
}
