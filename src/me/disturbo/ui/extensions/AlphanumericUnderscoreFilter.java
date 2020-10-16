package me.disturbo.ui.extensions;

public class AlphanumericUnderscoreFilter extends SimplifiedDocumentFilter{
    /*
            The AlphanumericUnderscoreFilter class prevents text input other than:
             - Alphabetic values
             - Numbers
             - Underscores
    */

    @Override
    public boolean test(String text){
        return text.matches("[a-zA-Z0-9_]*");
    }
}
