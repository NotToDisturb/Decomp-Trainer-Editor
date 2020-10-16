package me.disturbo.ui.party;

import me.disturbo.main.MainActivity;
import me.disturbo.main.Utils;
import me.disturbo.types.PartyMember;
import me.disturbo.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PartyMemberListPanel extends JPanel {
    /*
            The PartyMemberListPanel class contains the list of pokemon in a party and the control buttons:
            - Add: adds a new pokemon at the bottom of the party
            - Move up/down: moves the currently selected pokemon (if there is one) up or down but not further up than the first or the last pokemon in the party
            - Remove: removes the selected pokemon (or the last one in case there is none selected)

            <partyList> is updated when changing trainer, when using one of the control buttons and when inputting into PartyMemberPanel's species field
    */
    
    private JList<PartyMember> partyList;
    private int membersCount = 0;

    private final MainFrame frame;
    private final PartyPanel panel;

    public PartyMemberListPanel(MainFrame frame, PartyPanel panel){
        this.frame = frame;
        this.panel = panel;

        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        createListPanel();
        add(Box.createHorizontalStrut(5));
    }

    private final void createListPanel(){
        JPanel listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        createList(listPanel, cons);
        createAddButton(listPanel, cons);
        createRemoveButton(listPanel, cons);
        createMoveButtons(listPanel, cons);
        add(listPanel);
    }

    private final void createList(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createRigidArea(new Dimension(100, 10)), cons);
        cons.gridy++;
        partyList = new JList<>();
        partyList.setCellRenderer(new SpeciesRenderer(panel));
        partyList.setPrototypeCellValue(new PartyMember(Utils.getLongestString(MainActivity.species.values().toArray(new String[0]))));
        partyList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        partyList.addListSelectionListener(event -> {
            if(!event.getValueIsAdjusting() && partyList.getSelectedIndex() != -1){
                //If the selected value is not an empty row in the list
                if(!partyList.getSelectedValue().species.equals(" ")) {
                    panel.switchPartyMemberData(partyList.getSelectedValue());
                    frame.repaint();
                }
            }
        });
        pane.add(partyList, cons);
    }

    private final void createAddButton(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JButton add = new JButton("Add");
        add.addActionListener(event ->{
            if(membersCount < MainActivity.PARTY_MAX) {
                PartyMember member = new PartyMember();
                addToPartyList(member);
                panel.loadPartyMemberData(member);
                partyList.setSelectedIndex(membersCount - 1);
            }
        });
        pane.add(add, cons);
    }

    private final void createRemoveButton(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JButton remove = new JButton("Remove");
        remove.addActionListener(event ->{
            if(membersCount - 1 > 0){
                int index = partyList.getSelectedIndex();
                // If there is no index selected set the index to the last pokemon
                index = index != -1 ? index : membersCount - 1;
                // If the selected value is not an empty row in the list
                if(!partyList.getModel().getElementAt(index).equals(" ")){
                    removeFromPartyList(index);
                    partyList.setSelectedIndex(index - (index - 1 >= 0 ? index - 1 : 0));
                }
            }
        });
        pane.add(remove, cons);
    }

    private final void createMoveButtons(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JPanel move = new JPanel();
        move.setBackground(Color.WHITE);
        move.setLayout(new GridBagLayout());
        GridBagConstraints moveCons = new GridBagConstraints();
        moveCons.gridx = 0;
        moveCons.gridy = 0;
        moveCons.fill = GridBagConstraints.BOTH;
        createMoveUpButton(move, moveCons);
        createMoveDownButton(move, moveCons);
        pane.add(move, cons);
        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);
    }

    private final void createMoveUpButton(JPanel pane, GridBagConstraints cons){
        JButton moveUp = new JButton("Move up");
        moveUp.addActionListener(event ->{
            int index = partyList.getSelectedIndex();
            // If there is an index selected other than 0 and ...
            if(index > 0 && membersCount > 1){
                moveInPartyList(index, index - 1);
                partyList.setSelectedIndex(index - 1);
            }
        });
        pane.add(moveUp, cons);
        cons.gridx++;
        pane.add(Box.createHorizontalStrut(5), cons);
    }

    private final void createMoveDownButton(JPanel pane, GridBagConstraints cons){
        cons.gridx++;
        JButton moveDown = new JButton("Move down");
        moveDown.addActionListener(event ->{
            int index = partyList.getSelectedIndex();
            // If there is an index selected and ...
            if(index > -1 && index < membersCount - 1 && membersCount > 1){
                moveInPartyList(index, index + 1);
                partyList.setSelectedIndex(index + 1);
            }
        });
        pane.add(moveDown, cons);
    }

    public final int getMembersCount(){
        return membersCount;
    }

    // Creates a modifiable version of the combo box's model
    public final DefaultListModel<PartyMember> generateModel(){
        DefaultListModel<PartyMember> model = new DefaultListModel<>();
        ListModel<PartyMember> current = partyList.getModel();
        for(int index = 0; index < current.getSize(); index++){
            model.addElement(current.getElementAt(index));
        }
        return model;
    }

    public final void loadPartyToList(LinkedList<PartyMember> party){
        DefaultListModel<PartyMember> model = generateModel();

        // Clear current party, reset members count and add new members
        model.removeAllElements();
        membersCount = 0;
        for(PartyMember member : party){
            model.addElement(member);
            membersCount++;
        }

        // Fill the party with empty slots until PARTY_MAX is reached
        while(model.getSize() < MainActivity.PARTY_MAX){
            model.addElement(new PartyMember(" "));
        }
        partyList.setModel(model);
        partyList.setSelectedIndex(0);
    }

    public final void addToPartyList(PartyMember member){
        DefaultListModel<PartyMember> model = generateModel();
        model.set(membersCount, member);
        membersCount++;
        partyList.setModel(model);
    }

    public final void moveInPartyList(int from, int to){
        DefaultListModel<PartyMember> model = generateModel();
        PartyMember member = model.get(from);
        //Base cases
        if(!member.species.equals(" ") && from != to) {
            if(from >= to) from++;
            else to++;
            model.add(to, member);
            model.removeElementAt(from);
            partyList.setModel(model);
        }
    }

    public final void removeFromPartyList(int index){
        DefaultListModel<PartyMember> model = generateModel();
        model.removeElementAt(index);
        model.addElement(new PartyMember(" "));
        membersCount--;
        partyList.setModel(model);
    }
}