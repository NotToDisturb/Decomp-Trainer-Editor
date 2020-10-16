package ui.ai;

import main.MainActivity;
import types.Trainer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AiFlagsPanel extends JPanel {
    private HashMap<String, FlagPanel> aiFlags = new LinkedHashMap<>();

    public AiFlagsPanel(){
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
        for(String flag : MainActivity.aiFlags){
            FlagPanel flagPanel = new FlagPanel(flag);
            cons.gridy++;
            aiList.add(flagPanel, cons);
            aiFlags.put(flag, flagPanel);
        }
        cons.gridy++;
        aiList.add(Box.createVerticalStrut(10), cons);
        add(aiList, BorderLayout.CENTER);
    }

    public final void loadTrainerAiData(Trainer trainer){
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            aiFlags.get(flag).setSelected(trainer.aiFlags.contains(flag));
        }
    }

    public final void saveTrainerAiData(Trainer trainer){
        ArrayList<String> aiFlags = new ArrayList<>();
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            if(this.aiFlags.get(flag ).isSelected()) aiFlags.add(flag);
        }
        trainer.aiFlags = aiFlags;
    }
}
