package me.disturbo.main;

public class Utils {
    /*
            The Utils class contains general purpose functions
    */

    public static boolean isNumeric(String text){
        if (text == null) return false;
        try {
            Double.parseDouble(text);
        }
        catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public static String getLongestString(String[] list){
        String longest = list[0];
        for(String element : list){
            if(element != null && element.length() > longest.length()) longest = element;
        }
        return longest;
    }
}
