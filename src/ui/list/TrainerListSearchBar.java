package ui.list;

import main.MainActivity;
import ui.extensions.AlphanumericUnderscoreFilter;
import ui.extensions.ChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class TrainerListSearchBar extends JTextField {
    public TrainerListSearchBar(TrainerList list){
        ((PlainDocument) getDocument()).setDocumentFilter(new AlphanumericUnderscoreFilter());
        getDocument().addDocumentListener(new ChangeListener(){
            @Override
            public void changedUpdate(DocumentEvent event){
                SwingUtilities.invokeLater(() -> list.setModel(new DefaultComboBoxModel(getFilteredTrainers(getText()))));
            }
        });
    }

    private String[] getFilteredTrainers(String text){
        ArrayList<String> elements = new ArrayList<>();
        String[] keys = MainActivity.trainerIndexes.keySet().toArray(new String[0]);
        text = text.toLowerCase();
        for(String element : keys){
            if(element.toLowerCase().contains(text)) elements.add(element);
        }
        return elements.toArray(new String[0]);
    }
}
