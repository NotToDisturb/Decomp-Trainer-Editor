package ui.extensions;

import main.Utils;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class ComboBoxFiltered extends JComboBox<String>{
    private LinkedList<String> elements;
    private String showAll = "";

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

    private void configFocus(){
        JTextField field = (JTextField) getEditor().getEditorComponent();
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = field.getText();
                Object[] filtered = getFilteredElements(text);
                if(showAll.equals("")) filtered = elements.toArray();
                setModel(new DefaultComboBoxModel(filtered));
                field.setText(text);
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
        /*((PlainDocument) field.getDocument()).setDocumentFilter(new SimplifiedDocumentFilter(){
            @Override
            protected boolean test(String text) {
                //System.out.println(text);

                if(!text.matches("[a-zA-Z0-9_]*")) return false;
                String[] filtered = showAll.equals("") ? elements.toArray(new String[0]) : getFilteredElements(text);
                setModel(new DefaultComboBoxModel(filtered));
                setPopupVisible(true);
                return true;
            }
        });*/
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
                String previousText, text;
                previousText = text = field.getText();
                if(String.valueOf(event.getKeyChar()).matches("[a-zA-Z]") || String.valueOf(event.getKeyChar()).equals("_")) text += event.getKeyChar();
                Object[] filtered = getFilteredElements(text);
                if(showAll.equals("")) filtered = elements.toArray();
                if(filtered.length == 0) SwingUtilities.invokeLater(() -> field.setText(previousText));
                else{
                    setModel(new DefaultComboBoxModel(filtered));
                    field.setText(previousText);
                }
                setPopupVisible(true);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
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
