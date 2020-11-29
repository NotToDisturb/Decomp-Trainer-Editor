package me.disturbo.ui.extensions;

import me.disturbo.main.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;

public class ComboBoxFiltered extends JComboBox<String>{
    /*
            The ComboBoxFiltered class provides a Swing component that allows auto-filtering of a given set of elements according to what a text field contains
            To achieve this, multiple listeners are used, as well as a document filter:
             - Document filter: Prevents the input of unwanted symbols
             - Focus listener:
                 - Gain focus: applies the filter to the set of elements
                 - Lose focus: to prevent invalid leftover values, the contents are autocompleted to the first element in the filtered elements,
                               or to the first element if the filtered elements are empty
             - Text input listener: applies the filter every time a symbol accepted by the document filter is typed
             - Action listener: applies the filter upon detecting an action (pressing Enter)
            The field <showAll> is used by the filter to show all the elements without filtering in case the current value is equal to it
    */

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

    private final void setTypingModel(ComboBoxModel<String> model) {// Use a flag to prevent the recursive triggering of the DocumentListener
        shouldApplyEvent = false;
        // Changing the model results in the contents of the text field being erased,
        // meaning they must be restored after setModel
        JTextField field = (JTextField) editor.getEditorComponent();
        String text = field.getText();
        setModel(model);
        if(this.elements.contains(text)) setSelectedItem(text);
        else field.setText(text);
        shouldApplyEvent = true;
    }

    private final void configFocus(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Base case
                if(!showAll.equals("")){
                    String text = field.getText();
                    setModel(new DefaultComboBoxModel(getFilteredElements(field.getText())));
                    setSelectedItem(text);
                }
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
            public void change(DocumentEvent event){
                // Base cases
                if(shouldApplyEvent && !showAll.equals(""))
                    SwingUtilities.invokeLater(() -> {
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
        setSelectedItem(this.elements.contains(text) ? text : filtered[0]);
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
