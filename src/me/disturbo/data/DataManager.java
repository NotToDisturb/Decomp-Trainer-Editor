package me.disturbo.data;

import me.disturbo.data.composers.PartiesComposer;
import me.disturbo.data.composers.TrainersComposer;
import me.disturbo.data.indexers.PartyIndexer;
import me.disturbo.data.indexers.TrainerIndexer;
import me.disturbo.data.parsers.*;
import me.disturbo.main.MainActivity;
import me.disturbo.types.Party;
import me.disturbo.types.Trainer;
import me.disturbo.types.TrainerClass;

import java.io.*;
import java.util.*;

public class DataManager {
    /*
            COMMENTS PENDING
    */

    public static final LinkedHashMap<String, TrainerClass> loadTrainerClasses(){
        File trainerClasses = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "trainer_class_names.h");

        return (new TrainerClassesParser()).parse(trainerClasses, new LinkedHashMap<>());
    }

    public static final LinkedList<String> loadItems(){
        File items = new File(MainActivity.projectDirectory
                + File.separator + "include" + File.separator + "constants" + File.separator + "items.h");

        return (new ItemsParser()).parse(items, new LinkedList<>());
    }

    public static final LinkedHashMap<String, String> loadMoves(){
        File moves = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "move_names.h");

        return (new MovesParser()).parse(moves, new LinkedHashMap<>());
    }

    public static final LinkedHashMap<String, String> loadSpecies(){
        File species = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "species_names.h");

        return (new SpeciesParser()).parse(species, new LinkedHashMap<>());
    }

    public static final LinkedList<String> loadMusic(){
        File music = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "trainers.h");

        return (new MusicParser()).parse(music, new LinkedList<>());
    }

    public static final LinkedList<String> loadAiFlags(){
        File aiFlags = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "battle_ai.h");

        return (new AiFlagsParser()).parse(aiFlags, new LinkedList<>());
    }

    public static final LinkedList<String> loadTrainerPicsList(){
        File picList = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "trainers.h");

        return (new TrainerPicsListParser()).parse(picList, new LinkedList<>());
    }

    public static final LinkedHashMap<String, String> loadTrainerPicsPaths(){
        File picsPaths = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "graphics" + File.separator + "trainers.h");
        LinkedHashMap<String, String> picTable = loadTrainerPicTable();

        return (new TrainerPicsPathsParser(picTable)).parse(picsPaths, new LinkedHashMap<>());
    }

    public static final LinkedHashMap<String, String> loadTrainerPicTable(){
        File picTable = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "trainer_graphics" + File.separator + "front_pic_tables.h");

        return (new TrainerPicTableParser()).parse(picTable, new LinkedHashMap<>());
    }

    public static final LinkedHashMap<String, Integer> indexTrainers(){
        File trainers = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h");

        return (new TrainerIndexer()).parse(trainers, new LinkedHashMap<>());
    }
    
    public static final Trainer loadTrainer(String name){
        File trainers = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h");

        return (new TrainerParser()).parser(trainers, MainActivity.trainerIndexes, name);
    }

    public static final void saveTrainers(LinkedList<Trainer> orderedTrainers){
        File trainers = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h");

        (new TrainersComposer()).compose(trainers, orderedTrainers, MainActivity.trainerIndexes);
    }

    public static final LinkedHashMap<String, Integer> indexParties(){
        File parties = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h");

        return (new PartyIndexer()).parse(parties, new LinkedHashMap<>());
    }

    public static final Party loadParty(String flags, String size, String name){
        File parties = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h");

        return (new PartyParser(flags, size)).parser(parties, MainActivity.partyIndexes, name);
    }

    public static final void saveParties(LinkedList<Party> orderedParties){
        File parties = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h");

        (new PartiesComposer()).compose(parties, orderedParties, MainActivity.partyIndexes);
    }
}
