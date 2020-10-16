package me.disturbo.ui.list;

import me.disturbo.main.MainActivity;
import me.disturbo.ui.extensions.AlphanumericUnderscoreFilter;
import me.disturbo.ui.extensions.SimplifiedDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class TrainerListSearchBar extends JTextField {
    /*
            The TrainerListSearchBar class allows for filtering the trainer list to the characters inputted
    */

    public TrainerListSearchBar(TrainerList list){
        ((PlainDocument) getDocument()).setDocumentFilter(new AlphanumericUnderscoreFilter());
        getDocument().addDocumentListener(new SimplifiedDocumentListener(){
            @Override
            public void change(DocumentEvent event){
                SwingUtilities.invokeLater(() -> list.setModel(new DefaultComboBoxModel(getFilteredTrainers(getText()))));
            }
        });
    }

    private final String[] getFilteredTrainers(String text){
        ArrayList<String> elements = new ArrayList<>();
        String[] keys = MainActivity.trainerIndexes.keySet().toArray(new String[0]);
        text = text.toLowerCase();
        for(String element : keys){
            if(element.toLowerCase().contains(text)) elements.add(element);
        }
        return elements.toArray(new String[0]);
    }
}
