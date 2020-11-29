package me.disturbo.ui.party;

import me.disturbo.types.PartyMember;
import me.disturbo.types.Trainer;
import me.disturbo.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PartyPanel extends JPanel {
    private final MainFrame frame;
    private final PartyMemberListPanel partyMemberListPanel;
    private final PartyMemberPanel memberPanel;

    private PartyMember currentLoaded;

    public PartyPanel(MainFrame frame){
        this.frame = frame;

        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Box.createHorizontalStrut(10));
        JPanel spacer = new JPanel();
        spacer.setLayout(new BoxLayout(spacer, BoxLayout.PAGE_AXIS));
        spacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        partyMemberListPanel = new PartyMemberListPanel(frame, this);
        spacer.add(partyMemberListPanel);
        add(spacer);
        memberPanel = new PartyMemberPanel(frame);
        add(memberPanel);
    }

    public final PartyMember getCurrentLoaded() {
        return currentLoaded;
    }

    public final String getSpeciesText(){
        return memberPanel.getSpecies();
    }

    public final void loadTrainerPartyData(Trainer trainer){
        loadPartyMemberData(trainer.party.get(0));
        partyMemberListPanel.loadPartyToList(trainer.party);
    }

    public final void loadPartyMemberData(PartyMember member){
        currentLoaded = member;
        memberPanel.loadPartyMemberData(member);
    }

    public final void saveTrainerPartyData(Trainer trainer){
        memberPanel.savePartyMemberData(currentLoaded);

        trainer.party.clear();
        DefaultListModel<PartyMember> model = partyMemberListPanel.generateModel();
        for(int memberIndex = 0; memberIndex < partyMemberListPanel.getMembersCount(); memberIndex++){
            trainer.party.add(model.get(memberIndex));
        }
    }

    public final void switchPartyMemberData(PartyMember newMember){
        memberPanel.savePartyMemberData(currentLoaded);
        loadPartyMemberData(newMember);
    }
}
