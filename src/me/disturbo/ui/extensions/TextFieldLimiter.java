package me.disturbo.ui.extensions;

import me.disturbo.main.Utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFieldLimiter extends PlainDocument {
    /*
            The TextFieldLimiter class allows for limiting the amount of characters that can be entered into a document and their maximum value
    */

    private int maxLength;
    private int maxValue;

    public TextFieldLimiter(int maxLength, int maxValue) {
        super();
        this.maxLength = maxLength;
        this.maxValue = maxValue;
    }

    @Override
    public final void insertString(int offset, String str, AttributeSet attr){
        if (str == null)
            return;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(getText(0, getLength()));
            sb.insert(offset, str);

            if ((sb.toString().length() <= maxLength || maxLength < 0)
                    && (maxValue < 0 || (Utils.isNumeric(sb.toString()) && Integer.parseInt(sb.toString()) < maxValue)))
                super.insertString(offset, str, attr);
        }
        catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }
}
