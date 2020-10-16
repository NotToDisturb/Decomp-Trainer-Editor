package ui.extensions;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimplifiedDocumentListener implements DocumentListener {
    @Override
    public final void insertUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public final void removeUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public void changedUpdate(DocumentEvent event) {

    }
}
