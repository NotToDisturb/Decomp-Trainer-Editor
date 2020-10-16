package ui.extensions;

import main.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;

public class ComboBoxFiltered extends JComboBox<String>{
    private LinkedList<String> elements;
    private String showAll = "";
    private boolean shouldApplyEvent = true;

    public ComboBoxFiltered(LinkedList<String> elements, String showAll){
        super(elements.toArray(new String[0]));
        setSelectedIndex(0);
        setPrototypeDisplayValue(Utils.getLongestString(elements.toArray(new String[0])));
        this.elements = elements;
        this.showAll = showAll.toLowerCase();
        setEditable(true);
        configFocus();
        configFilter();
        configPressEnter();
    }

    public void setTypingModel(ComboBoxModel<String> model) {
        if(editor == null){
            super.setModel(model);
            return;
        }

        shouldApplyEvent = false;
        JTextField field = (JTextField) editor.getEditorComponent();
        String text = field.getText();
        super.setModel(model);
        field.setText(text);
        shouldApplyEvent = true;
    }

    private void configFocus(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!showAll.equals("")) setModel(new DefaultComboBoxModel(getFilteredElements(field.getText())));
                setPopupVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                autocomplete(field);
            }
        });
    }

    private void configFilter(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        ((PlainDocument) field.getDocument()).setDocumentFilter(new AlphanumericUnderscoreFilter());
        field.getDocument().addDocumentListener(new ChangeListener(){
            @Override
            public void changedUpdate(DocumentEvent event){
                if(!shouldApplyEvent) return;
                if(!showAll.equals("")) SwingUtilities.invokeLater(() -> {
                    setTypingModel(new DefaultComboBoxModel(getFilteredElements(field.getText())));
                    if(field.isFocusOwner()) setPopupVisible(true);
                });

            }
        });
    }

    public void configPressEnter(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.addActionListener(event ->{
            autocomplete(field);
        });
    }

    public void autocomplete(JTextField field){
        String text = field.getText();
        String[] filtered = getFilteredElements(text);
        if(filtered.length == 0) filtered = elements.toArray(new String[0]);
        setModel(new DefaultComboBoxModel(filtered));
        setSelectedIndex(0);
    }

    private String[] getFilteredElements(String text){
        //Base case
        if(text.equals(showAll)) return this.elements.toArray(new String[0]);

        LinkedList<String> elements = new LinkedList<>();
        text = text.toLowerCase();
        for(String element : this.elements){
            if(element.toLowerCase().contains(text)) elements.add(element);
        }
        return elements.toArray(new String[0]);
    }
}
