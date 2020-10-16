package me.disturbo.ui.extensions;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimplifiedDocumentListener implements DocumentListener {
    /*
            The SimplifiedDocumentListener class provides an easier and cleaner implementation for DocumentListener
            Originally from StackOverflow (https://stackoverflow.com/a/3953219)
    */

    protected void change(DocumentEvent event){}

    @Override
    public final void insertUpdate(DocumentEvent event) {
        change(event);
    }

    @Override
    public final void removeUpdate(DocumentEvent event) {
        change(event);
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        change(event);
    }
}
