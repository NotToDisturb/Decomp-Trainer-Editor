package ui;

import main.MainActivity;
import ui.extensions.SimplifiedDocumentFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class TrainerListSearchBar extends JTextField {
    public TrainerListSearchBar(TrainerList list){
        ((PlainDocument) getDocument()).setDocumentFilter(new SimplifiedDocumentFilter(){
            @Override
            protected boolean test(String text) {
                // If text contains anything other than letters or underscores
                if(!text.matches("[a-zA-Z0-9_]*")) return false;

                list.setModel(new DefaultComboBoxModel(getFilteredTrainers(text)));
                return true;
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
