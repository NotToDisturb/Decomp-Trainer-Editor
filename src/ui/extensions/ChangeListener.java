package ui.extensions;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChangeListener implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public void changedUpdate(DocumentEvent event) {

    }
}
