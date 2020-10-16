package ui.party;

import main.MainActivity;
import types.PartyMember;
import types.Trainer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PartyPanel extends JPanel {
    public PartyMemberListPanel partyMemberListPanel;

    public PartyPanel(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(10));
        JPanel spacer = new JPanel();
        spacer.setLayout(new BoxLayout(spacer, BoxLayout.PAGE_AXIS));
        spacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        partyMemberListPanel = new PartyMemberListPanel(6, true);
        spacer.add(partyMemberListPanel);
        add(spacer);
    }

    public void loadTrainerPartyData(Trainer trainer){
        partyMemberListPanel.clearAssociations();
        for(int index = 0; index < trainer.party.size(); index++){
            loadPartyMemberData(trainer.party.get(index));
        }
        partyMemberListPanel.setSelectedIndex(0);
    }

    public void loadPartyMemberData(PartyMember member){
        PartyMemberPanel memberInputs = new PartyMemberPanel();
        memberInputs.iv.setText(member.iv);
        memberInputs.level.setText(member.level);
        memberInputs.species.setSelectedItem(member.species);
        if(!member.heldItem.equals("")) memberInputs.heldItem.setSelectedItem(member.heldItem);
        for(int subindex = 0; subindex < member.moves.size(); subindex++){
            memberInputs.moves.get(subindex).setSelectedItem(MainActivity.moves.get(member.moves.get(subindex)));
        }
        partyMemberListPanel.addAssociation(memberInputs);
    }

    public void saveTrainerPartyData(Trainer trainer){
        LinkedList<PartyMember> party = new LinkedList<>();
        for(int index = 0; index < partyMemberListPanel.associations.size(); index++){
            PartyMemberPanel memberInputs = partyMemberListPanel.associations.get(index);
            PartyMember member = new PartyMember();
            member.iv = memberInputs.iv.getText();
            member.level = memberInputs.level.getText();
            String species = memberInputs.species.getSelectedItem().toString();
            member.species = species;
            member.heldItem = memberInputs.heldItem.getSelectedItem().toString();
            LinkedList<String> keys = new LinkedList<>(MainActivity.moves.keySet());
            LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
            for(int subindex = 0; subindex < memberInputs.moves.size(); subindex++){
                String move = memberInputs.moves.get(subindex).getSelectedItem().toString();
                int indexOfMove = values.indexOf(move);
                member.moves.add(keys.get(indexOfMove));
            }
            party.add(member);
        }
        trainer.party = party;
    }
}
