package ui.trainer;

import javax.swing.*;
import java.awt.*;

public class GenderPanel extends JPanel {
    private final JRadioButton maleButton, femaleButton;

    public GenderPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0; cons.gridy = 0; cons.weightx = 1;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;

        add(Box.createVerticalStrut(30), cons);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; add(genderLabel, cons);

        cons.gridy++; add(Box.createVerticalStrut(5), cons);

        maleButton = new JRadioButton("Male");
        maleButton.setBackground(Color.WHITE);
        cons.gridy++; add(maleButton, cons);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBackground(Color.WHITE);
        cons.gridy++; add(femaleButton, cons);

        addButtonListeners();
    }

    private final void addButtonListeners(){
        maleButton.addActionListener(event -> {
            femaleButton.setSelected(false);
        });

        femaleButton.addActionListener(event -> {
            maleButton.setSelected(false);
        });
    }

    public void doSelection(String gender){
        boolean male = gender.equals("");
        maleButton.setSelected(male);
        femaleButton.setSelected(!male);
    }

    public String getGender(){
        return maleButton.isSelected() ? "" : "F_TRAINER_FEMALE";
    }
}
