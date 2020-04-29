package main;

import types.Trainer;
import types.TrainerClass;
import ui.Screen;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MainActivity {
    public static Screen screen;
    public static File projectDirectory = null;

    public static String currentTrainer = null;
    public static LinkedHashMap<String, Integer> trainerIndexes;
    public static LinkedHashMap<String, Integer> partyIndexes;
    public static LinkedHashMap<String, TrainerClass> trainerClasses;
    public static LinkedList<String> items;
    public static LinkedHashMap<String, String> moves;
    public static LinkedHashMap<String, String> species;
    public static LinkedList<String> music;
    public static LinkedList<String> aiFlags;
    public static LinkedList<String> picList;
    public static LinkedHashMap<String, String> picPaths;
    public static LinkedHashMap<String, Trainer> loadedTrainers = new LinkedHashMap<>();

    public static void main(String[] args){
        screen = new Screen();
    }
}
