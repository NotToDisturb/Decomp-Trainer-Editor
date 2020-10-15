package ui.extensions;

import main.MainActivity;
import main.Utils;
import types.PartyMember;
import ui.PartyMemberInput;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PartyMemberSelector extends JPanel {
    private int maxAssociations;
    private boolean fillAssociations;
    private JList associationList;
    private JPanel associationPanel;
    public LinkedList<PartyMemberInput> associations = new LinkedList<>();

    public PartyMemberSelector(int maxAssociations, boolean fillAssociations){
        this.maxAssociations = maxAssociations;
        this.fillAssociations = fillAssociations;
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        createListPanel();
        add(Box.createHorizontalStrut(5));
        associationPanel = new JPanel();
        associationPanel.setBackground(Color.WHITE);
        add(associationPanel);
    }

    public void createListPanel(){
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

    public void createList(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createRigidArea(new Dimension(100, 10)), cons);
        cons.gridy++;
        associationList = new JList();
        associationList.setCellRenderer(new SpeciesRenderer());
        associationList.setPrototypeCellValue(Utils.getLongestString(MainActivity.species.values().toArray(new String[0])));
        associationList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        associationList.addListSelectionListener(event -> {
            if(!event.getValueIsAdjusting() && getSelectedIndex() != -1){
                if(!associationList.getSelectedValue().toString().equals(" ")) {
                    associationPanel.removeAll();
                    associationPanel.add(associations.get(getSelectedIndex()));
                    MainActivity.screen.repaint();
                }
            }
        });
        pane.add(associationList, cons);
    }

    public void createAddButton(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JButton add = new JButton("Add");
        add.addActionListener(event ->{
            associationPanel.removeAll();
            MainActivity.screen.partyInput.loadPartyData(new PartyMember());
            setSelectedIndex(associations.size() - 1);
        });
        pane.add(add, cons);
    }

    public void createRemoveButton(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JButton remove = new JButton("Remove");
        remove.addActionListener(event ->{
            if(associations.size() - 1 != 0){
                associationPanel.removeAll();
                int index = getSelectedIndex() != -1 ? getSelectedIndex() : associations.size() - 1;
                removeAssociation(index);
                int substractFromIndex = index != 0 ? 1 : 0;
                setSelectedIndex(index - substractFromIndex);
                revalidate();
            }
        });
        pane.add(remove, cons);
    }

    public void createMoveButtons(JPanel pane, GridBagConstraints cons){
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

    public void createMoveUpButton(JPanel pane, GridBagConstraints cons){
        JButton moveUp = new JButton("Move up");
        moveUp.addActionListener(event ->{
            int index = getSelectedIndex();
            if(index > 0 && associations.size() > 1){
                insertAssociation(removeAssociation(index), index - 1);
                setSelectedIndex(index - 1);
            }
        });
        pane.add(moveUp, cons);
        cons.gridx++;
        pane.add(Box.createHorizontalStrut(5), cons);
    }

    public void createMoveDownButton(JPanel pane, GridBagConstraints cons){
        cons.gridx++;
        JButton moveDown = new JButton("Move down");
        moveDown.addActionListener(event ->{
            int index = getSelectedIndex();
            if(index >= 0 && index < associations.size() - 1 && associations.size() > 1){
                if(index == associations.size() - 2) addAssociation(removeAssociation(index));
                else insertAssociation(removeAssociation(index), index + 1);
                setSelectedIndex(index + 1);
            }
        });
        pane.add(moveDown, cons);
    }

    public void setSelectedIndex(int index){
        associationList.setSelectedIndex(index);
    }

    public int getSelectedIndex(){
        return associationList.getSelectedIndex();
    }

    public void addAssociation(PartyMemberInput associated){
        if(associations.size() < maxAssociations){
            DefaultListModel newModel = new DefaultListModel();
            ListModel model = associationList.getModel();
            for(int index = 0; index < model.getSize(); index++){
                if(!model.getElementAt(index).equals(" ")) newModel.addElement(model.getElementAt(index));
            }
            newModel.addElement(associated.species.getSelectedItem());
            while(newModel.getSize() < maxAssociations && fillAssociations){
                newModel.addElement(" ");
            }
            associationList.setModel(newModel);
            associations.add(associated);
        }
    }

    public void insertAssociation(PartyMemberInput associated, int index){
        if(associations.size() < maxAssociations){
            DefaultListModel newModel = new DefaultListModel();
            LinkedList<PartyMemberInput> newAssociations = new LinkedList<>();
            ListModel model = associationList.getModel();
            int modelIndex = 0;
            for(int insertionIndex = 0; insertionIndex <= associations.size(); insertionIndex++){
                if(!model.getElementAt(index).equals(" ") && insertionIndex != index){
                    newModel.addElement(model.getElementAt(modelIndex));
                    newAssociations.add(associations.get(modelIndex));
                    modelIndex++;
                }
                else if(insertionIndex == index){
                    newModel.addElement(associated.species.getSelectedItem());
                    newAssociations.add(associated);
                }
            }
            while(newModel.getSize() < maxAssociations && fillAssociations){
                newModel.addElement(" ");
            }
            associationList.setModel(newModel);
            associations = newAssociations;
        }
    }

    public PartyMemberInput removeAssociation(int associationIndex){
        if(associationIndex < associations.size()) {
            DefaultListModel newModel = new DefaultListModel();
            ListModel model = associationList.getModel();
            for (int index = 0; index < model.getSize(); index++) {
                if (!model.getElementAt(index).equals(" ") && associationIndex != index)
                    newModel.addElement(model.getElementAt(index));
            }
            while (newModel.getSize() < maxAssociations && fillAssociations) {
                newModel.addElement(" ");
            }
            associationList.setModel(newModel);
            return associations.remove(associationIndex);
        }
        return null;
    }

    public void updateListAssociations(){
        DefaultListModel newModel = new DefaultListModel();
        ListModel model = associationList.getModel();
        for(int index = 0; index < model.getSize(); index++){
            if(!model.getElementAt(index).equals(" ")) newModel.addElement(associations.get(index).species.getSelectedItem());
        }
        while(newModel.getSize() < maxAssociations && fillAssociations){
            newModel.addElement(" ");
        }
        associationList.setModel(newModel);
    }

    public void clearAssociations(){
        associationList.setModel(new DefaultListModel());
        associationPanel.removeAll();
        associations = new LinkedList<>();
    }
}