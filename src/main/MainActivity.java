package main;

import types.Trainer;
import types.TrainerClass;
import ui.MainFrame;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MainActivity {
    public static MainFrame screen;
    public static File projectDirectory = null;

    public static final int ITEMS_MAX = 4;

    public static String currentTrainer = null;
    public static HashMap<String, Integer> trainerIndexes, partyIndexes;
    public static HashMap<String, TrainerClass> trainerClasses;
    public static LinkedList<String> items;
    public static HashMap<String, String> moves, species;
    public static LinkedList<String> music, aiFlags, picList;
    public static HashMap<String, String> picPaths;
    public static HashMap<String, Trainer> loadedTrainers = new LinkedHashMap<>();

    public static void main(String[] args){
        screen = new MainFrame();
    }
}
