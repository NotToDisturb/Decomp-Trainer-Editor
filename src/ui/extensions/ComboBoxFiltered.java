package ui.extensions;

import main.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;

public class ComboBoxFiltered extends JComboBox<String>{
    private LinkedList<String> elements;
    private String showAll;
    private boolean shouldApplyEvent = true;

    public ComboBoxFiltered(LinkedList<String> elements, String showAll, DocumentFilter filter){
        super(elements.toArray(new String[0]));
        setSelectedIndex(0);
        setPrototypeDisplayValue(Utils.getLongestString(elements.toArray(new String[0])));
        this.elements = elements;
        this.showAll = showAll.toLowerCase();
        setEditable(true);
        configFocus();
        configDocumentFilter(filter);
        configAutofilter();
        configPressEnter();
    }

    public ComboBoxFiltered(LinkedList<String> elements, DocumentFilter filter){
        this(elements, "", filter);
    }

    private final void setTypingModel(ComboBoxModel<String> model) {
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

    private final void configFocus(){
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

    private final void configDocumentFilter(DocumentFilter filter){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        ((PlainDocument) field.getDocument()).setDocumentFilter(filter);
    }

    private final void configAutofilter(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.getDocument().addDocumentListener(new SimplifiedDocumentListener(){
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

    private final void configPressEnter(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.addActionListener(event -> autocomplete(field));
    }

    private final void autocomplete(JTextField field){
        String text = field.getText();
        String[] filtered = getFilteredElements(text);
        if(filtered.length == 0) filtered = elements.toArray(new String[0]);
        if(!showAll.equals("")) setModel(new DefaultComboBoxModel(filtered));
        setSelectedItem(filtered[0]);
    }

    private final String[] getFilteredElements(String text){
        //Base case
        if(text.equalsIgnoreCase(showAll)) return this.elements.toArray(new String[0]);

        LinkedList<String> elements = new LinkedList<>();
        text = text.toLowerCase();
        for(String element : this.elements){
            if(element.toLowerCase().contains(text)) elements.add(element);
        }
        return elements.toArray(new String[0]);
    }
}
