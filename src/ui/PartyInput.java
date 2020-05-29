package ui;

import main.MainActivity;
import types.PartyMember;
import types.Trainer;
import ui.extensions.PartyMemberSelector;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PartyInput extends JPanel {
    public PartyMemberSelector partyMemberSelector;

    public PartyInput(){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(10));
        JPanel spacer = new JPanel();
        spacer.setLayout(new BoxLayout(spacer, BoxLayout.PAGE_AXIS));
        spacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        partyMemberSelector = new PartyMemberSelector(6, true);
        spacer.add(partyMemberSelector);
        add(spacer);
    }

    public void loadTrainerPartyData(Trainer trainer){
        partyMemberSelector.clearAssociations();
        for(int index = 0; index < trainer.party.size(); index++){
            loadPartyData(trainer.party.get(index));
        }
        partyMemberSelector.setSelectedIndex(0);
    }

    public void loadPartyData(PartyMember member){
        PartyMemberInput memberInputs = new PartyMemberInput();
        memberInputs.iv.setText(member.iv);
        memberInputs.level.setText(member.level);
        memberInputs.species.setSelectedItem(member.species);
        if(!member.heldItem.equals("")) memberInputs.heldItem.setSelectedItem(member.heldItem);
        for(int subindex = 0; subindex < member.moves.size(); subindex++){
            memberInputs.moves.get(subindex).setSelectedItem(MainActivity.moves.get(member.moves.get(subindex)));
        }
        partyMemberSelector.addAssociation(memberInputs);
    }

    public void saveTrainerPartyData(Trainer trainer){
        LinkedList<PartyMember> party = new LinkedList<>();
        for(int index = 0; index < partyMemberSelector.associations.size(); index++){
            PartyMemberInput memberInputs = partyMemberSelector.associations.get(index);
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
