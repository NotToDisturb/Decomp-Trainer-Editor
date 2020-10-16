package ui.extensions;

public class SpeciesFilter extends SimplifiedDocumentFilter{
    @Override
    public boolean test(String text){
        return text.matches("[a-zA-Z0-9 \\-]*");
    }
}
