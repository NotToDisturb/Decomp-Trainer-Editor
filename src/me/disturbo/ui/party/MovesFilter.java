package me.disturbo.ui.party;

import me.disturbo.ui.extensions.SimplifiedDocumentFilter;

public class MovesFilter extends SimplifiedDocumentFilter {
    /*
            The MovesFilter class is a filter made specifically for pokemon moves that prevents text input other than:
             - Alphabetic values
             - Numbers
             - Spaces
             - Minus symbols
    */

    @Override
    public boolean test(String text){
        return text.matches("[a-zA-Z0-9 \\-]*");
    }
}
