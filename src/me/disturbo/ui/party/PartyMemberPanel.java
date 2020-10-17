package me.disturbo.ui.party;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.types.PartyMember;
import me.disturbo.ui.MainFrame;
import me.disturbo.ui.extensions.AlphanumericUnderscoreFilter;
import me.disturbo.ui.extensions.ComboBoxFiltered;
import me.disturbo.ui.extensions.SimplifiedDocumentListener;
import me.disturbo.ui.extensions.TextFieldLimiter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class PartyMemberPanel extends JPanel{
    /*
            The PartyMemberPanel class contains Swing objects that allow for editing the fields of a PartyMember object
    */

    private final MainFrame frame;

    private JTextField iv;
    private JTextField level;
    private ComboBoxFiltered species;
    private ComboBoxFiltered heldItem;
    private MovesPanel movesPanel;

    public PartyMemberPanel(MainFrame frame){
        this.frame = frame;

        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel general = new JPanel();
        general.setBackground(Color.WHITE);
        general.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.gridx = 0; cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        general.add(Box.createVerticalStrut(5), cons);

        createIv(general, cons);
        createLevel(general, cons);
        createSpecies(general, cons);
        createHeldItem(general, cons);

        cons.gridx++; cons.gridy = 0;
        general.add(Box.createRigidArea(new Dimension(20, 5)), cons);


        JPanel moves = new JPanel();
        moves.setBackground(Color.WHITE);
        moves.setLayout(new GridBagLayout());

        cons.gridx = 0; moves.add(Box.createVerticalStrut(5), cons);
        createMoves(moves, cons);

        cons.gridx++; cons.gridy = 0;
        moves.add(Box.createRigidArea(new Dimension(10, 5)), cons);

        add(general); add(moves);
    }

    private final void createIv(JPanel panel, GridBagConstraints cons){
        JLabel ivLabel = new JLabel("IVs: ");
        ivLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; panel.add(ivLabel, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(5), cons);


        iv = new JTextField();
        iv.setDocument(new TextFieldLimiter(-1, 256));
        cons.gridy++; panel.add(iv, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(10), cons);
    }

    private final void createLevel(JPanel panel, GridBagConstraints cons){
        JLabel levelLabel = new JLabel("Level: ");
        levelLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; panel.add(levelLabel, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(5), cons);


        level = new JTextField();
        level.setDocument(new TextFieldLimiter(-1, 255));
        cons.gridy++; panel.add(level, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(10), cons);
    }

    private final void createSpecies(JPanel panel, GridBagConstraints cons){
        JLabel speciesLabel = new JLabel("Species: ");
        speciesLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; panel.add(speciesLabel, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(5), cons);

        LinkedList<String> keys = new LinkedList<>(MainActivity.species.keySet());
        species = new ComboBoxFiltered(keys, keys.get(0), new AlphanumericUnderscoreFilter());
        JTextField field = (JTextField) species.getEditor().getEditorComponent();
        field.getDocument().addDocumentListener(new SimplifiedDocumentListener(){
            @Override
            public void change(DocumentEvent event){
                // Repaint the frame to update the displayed species in the party member list
                frame.repaint();
            }
        });
        cons.gridy++; panel.add(species, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(10), cons);
    }

    private final void createHeldItem(JPanel panel, GridBagConstraints cons){
        JLabel itemLabel = new JLabel("Held item: ");
        itemLabel.setHorizontalAlignment(JLabel.LEFT);
        cons.gridy++; panel.add(itemLabel, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(5), cons);

        heldItem = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0), new AlphanumericUnderscoreFilter());
        heldItem.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items.toArray(new String[0])));
        cons.gridy++; panel.add(heldItem, cons);

        cons.gridy++; panel.add(Box.createVerticalStrut(10), cons);
    }

    private final void createMoves(JPanel panel, GridBagConstraints cons){
        movesPanel = new MovesPanel();
        cons.gridy++; panel.add(movesPanel, cons);
    }

    public final String getSpecies(){
        return ((JTextField) species.getEditor().getEditorComponent()).getText();
    }

    public final void loadPartyMemberData(PartyMember member){
        iv.setText(member.iv);
        level.setText(member.level);
        species.setSelectedItem(member.species);
        heldItem.setSelectedItem(!member.heldItem.equals("") ? member.heldItem : MainActivity.items.get(0));
        for(int moveIndex = 0, movesSize = member.moves.size(); moveIndex < MainActivity.MOVES_MAX; moveIndex++){
            if(moveIndex < movesSize) movesPanel.setMove(moveIndex, MainActivity.moves.get(member.moves.get(moveIndex)));
            else movesPanel.setMove(moveIndex, MainActivity.moves.values().toArray(new String[0])[0]);
        }
    }

    public final void savePartyMemberData(PartyMember member){
        member.iv = iv.getText();
        member.level = level.getText();
        member.species = this.species.getSelectedItem().toString();
        member.heldItem = heldItem.getSelectedItem().toString();
        LinkedList<String> keys = new LinkedList<>(MainActivity.moves.keySet());
        LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
        ArrayList<String> moves = new ArrayList<>();
        for(int moveIndex = 0; moveIndex < MainActivity.MOVES_MAX; moveIndex++){
            int indexOfMove = values.indexOf(movesPanel.getMove(moveIndex));
            moves.add(keys.get(indexOfMove));
        }
        member.moves = moves;
    }
}
