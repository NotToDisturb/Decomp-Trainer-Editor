package main;

import java.util.List;

public class Utils {
    public static boolean isNumeric(String text){
        if (text == null) return false;
        try {
            double d = Double.parseDouble(text);
        }
        catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public static String getLongestString(List<String> list){
        String longest = list.get(0);
        for(String element : list){
            if(element != null)
                if(element.length() > longest.length()) longest = element;
        }
        return longest;
    }
}
