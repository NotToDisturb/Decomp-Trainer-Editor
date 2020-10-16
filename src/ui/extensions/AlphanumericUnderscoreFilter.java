package ui.extensions;

public class AlphanumericUnderscoreFilter extends SimplifiedDocumentFilter{
    @Override
    public boolean test(String text){
        return text.matches("[a-zA-Z0-9_]*");
    }
}
