package ui.ai;

import javax.swing.*;
import java.awt.*;

public class FlagPanel extends JPanel {
    private final JCheckBox flagCheck;

    public FlagPanel(String flag){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        flagCheck = new JCheckBox();
        flagCheck.setBackground(Color.WHITE);

        JLabel aiLabel = new JLabel(flag);
        aiLabel.setHorizontalAlignment(JLabel.LEFT);
        add(flagCheck); add(aiLabel);
    }

    public final void setSelected(boolean selected){
        flagCheck.setSelected(selected);
    }

    public final boolean isSelected(){
        return flagCheck.isSelected();
    }
}
