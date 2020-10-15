package ui;

import main.MainActivity;
import main.Utils;
import ui.extensions.ComboBoxFiltered;
import ui.extensions.TextFieldLimiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class PartyMemberInput extends JPanel{
    public JTextField iv;
    public JTextField level;
    public ComboBoxFiltered species;
    public ComboBoxFiltered heldItem;
    public ArrayList<ComboBoxFiltered> moves = new ArrayList<>();

    public PartyMemberInput(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel general = new JPanel();
        general.setBackground(Color.WHITE);
        general.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        general.add(Box.createVerticalStrut(5), cons);

        createIv(general, cons);
        createLevel(general, cons);
        createSpecies(general, cons);
        createHeldItem(general, cons);

        cons.gridx++;
        cons.gridy = 0;
        general.add(Box.createRigidArea(new Dimension(20, 5)), cons);


        JPanel moves = new JPanel();
        moves.setBackground(Color.WHITE);
        moves.setLayout(new GridBagLayout());
        cons.gridx = 0;
        moves.add(Box.createVerticalStrut(5), cons);
        createMoves(moves, cons);

        cons.gridx++;
        cons.gridy = 0;
        moves.add(Box.createRigidArea(new Dimension(10, 5)), cons);

        add(general); add(moves);
    }

    private void createIv(JPanel panel, GridBagConstraints cons){
        cons.gridy++;
        JLabel ivLabel = new JLabel("IVs: ");
        ivLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(ivLabel, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        iv = new JTextField();
        iv.setDocument(new TextFieldLimiter(-1, 256));
        panel.add(iv, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(10), cons);
    }

    private void createLevel(JPanel panel, GridBagConstraints cons){
        cons.gridy++;
        JLabel levelLabel = new JLabel("Level: ");
        levelLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(levelLabel, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        level = new JTextField();
        level.setDocument(new TextFieldLimiter(-1, 255));
        panel.add(level, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(10), cons);
    }

    private void createSpecies(JPanel panel, GridBagConstraints cons){
        cons.gridy++;
        JLabel speciesLabel = new JLabel("Species: ");
        speciesLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(speciesLabel, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(5), cons);

        LinkedList<String> keys = new LinkedList<>(MainActivity.species.keySet());
        species = new ComboBoxFiltered(keys, keys.get(0));
        species.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent event) {
                MainActivity.screen.partyInput.partyMemberSelector.updateListAssociations();
            }
        });
        species.addActionListener(e -> {
            MainActivity.screen.partyInput.partyMemberSelector.updateListAssociations();
        });
        panel.add(species, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(10), cons);
    }

    private void createHeldItem(JPanel panel, GridBagConstraints cons){
        cons.gridy++;
        JLabel itemLabel = new JLabel("Held item: ");
        itemLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(itemLabel, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(5), cons);

        heldItem = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0));
        heldItem.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items.toArray(new String[0])));
        panel.add(heldItem, cons);

        cons.gridy++;
        panel.add(Box.createVerticalStrut(10), cons);
    }

    private void createMoves(JPanel panel, GridBagConstraints cons){
        cons.gridy++;
        JLabel movesLabel = new JLabel("Moves");
        movesLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(movesLabel, cons);

        for(int index = 0; index < 4; index++){
            cons.gridy++;
            panel.add(Box.createVerticalStrut(10), cons);

            cons.gridy++;
            LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
            ComboBoxFiltered move = new ComboBoxFiltered(values, values.get(0));
            move.setPrototypeDisplayValue(Utils.getLongestString(values.toArray(new String[0])));
            panel.add(move, cons);
            moves.add(move);
        }

        cons.gridy++;
        panel.add(Box.createVerticalStrut(10), cons);
    }
}
