package me.disturbo.main;

import me.disturbo.types.PartyMember;
import me.disturbo.types.Trainer;
import me.disturbo.types.TrainerClass;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class DataManager {
    /*
            COMMENTS PENDING
    */

    public static final String readFile(File file){
        String data = "";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!=null) {
                data += line + System.lineSeparator();
            }
            fr.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        return data;
    }

    public static final LinkedHashMap<String, TrainerClass> loadTrainerClasses(){
        File file = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "trainer_class_names.h");
        LinkedHashMap<String, TrainerClass> trainerClasses = new LinkedHashMap<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("[TRAINER_")){
                    line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
                    String declaration = line.substring(1, line.indexOf("]"));
                    String name = line.substring(line.indexOf("_(\""), line.indexOf("\")"));
                    trainerClasses.put(declaration, new TrainerClass(name, "0"));
                }
            }
            fr.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }

        return trainerClasses;
    }

    public static final LinkedList<String> loadItems(){
        File file = new File(MainActivity.projectDirectory
                + File.separator + "include" + File.separator + "constants" + File.separator + "items.h");
        LinkedList<String> items = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            boolean reachedCount = false;
            while((line = br.readLine()) != null && !reachedCount) {
                if(line.contains("ITEMS_COUNT")) reachedCount = true;
                else if(line.contains("ITEM_")) items.add(line.split(" ")[1]);
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return items;
    }

    public static final LinkedHashMap<String, String> loadMoves(){
        File file = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "move_names.h");
        LinkedHashMap<String, String> moves = new LinkedHashMap<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("[MOVE_") && !line.contains("[MOVE_NAME_LENGTH")) {
                    line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
                    String declaration = line.substring(1, line.indexOf("]"));
                    String name = line.substring(line.indexOf("_(\"") + 3, line.indexOf("\")"));
                    moves.put(declaration, name);
                }
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return moves;
    }

    public static final LinkedHashMap<String, String> loadSpecies(){
        File file = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "text" + File.separator + "species_names.h");
        LinkedHashMap<String, String> species = new LinkedHashMap<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("[SPECIES_")){
                    line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
                    String declaration = line.substring(1, line.indexOf("]"));
                    String name = line.substring(line.indexOf("_(\"") + 3, line.indexOf("\")"));
                    species.put(declaration, name);
                }
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return species;
    }

    public static final LinkedList<String> loadMusic(){
        File file = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "trainers.h");
        LinkedList<String> music = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("TRAINER_ENCOUNTER_MUSIC_")) music.add(line.split(" ")[1]);
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return music;
    }

    public static final LinkedList<String> loadAiFlags(){
        File file = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "battle_ai.h");
        LinkedList<String> aiFlags = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("AI_SCRIPT_")) aiFlags.add(line.split(" ")[1]);
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return aiFlags;
    }

    public static final LinkedList<String> loadTrainerPicsList(){
        File file = new File(MainActivity.projectDirectory + File.separator + "include"
                + File.separator + "constants" + File.separator + "trainers.h");
        LinkedList<String> picList = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("TRAINER_PIC_")) picList.add(line.split(" ")[1]);
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return picList;
    }

    public static final LinkedHashMap<String, String> loadTrainerPicsPaths(){
        File file = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "graphics" + File.separator + "trainers.h");
        LinkedHashMap<String, String> picAssociations = loadTrainerPicsAssociation();
        LinkedHashMap<String, String> picPaths = new LinkedHashMap<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
                if(line.contains("constu32gTrainerFrontPic_")){
                    String [] frontPic = line.substring("constu32".length()).split("=");
                    String picDeclaration = frontPic[0].replace("[]", "");
                    String picPath = frontPic[1].substring("INCBIN_U32(.".length(), frontPic[1].length() - ".4bpp.lz.);".length()) + ".png";
                    picPaths.put(picAssociations.get(picDeclaration), picPath);
                }
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return picPaths;
    }

    public static final LinkedHashMap<String, String> loadTrainerPicsAssociation(){
        File file = new File(MainActivity.projectDirectory + File.separator + "src"
                + File.separator + "data" + File.separator + "trainer_graphics" + File.separator + "front_pic_tables.h");
        LinkedHashMap<String, String> picAssociations = new LinkedHashMap<>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("TRAINER_SPRITE")){
                    line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
                    String[] association = line.substring("TRAINER_SPRITE(".length(), line.length() - 2).split(",");
                    picAssociations.put(association[1], "TRAINER_PIC_" + association[0]);
                }
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return picAssociations;
    }

    public static final LinkedHashMap<String, Integer> indexTrainers(){
        File file = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h");
        LinkedHashMap<String, Integer> trainerIndexes = new LinkedHashMap<>();
        int currentIndex = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("[TRAINER_") && !line.contains("[TRAINER_NONE]")){
                    String trainerName = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                    trainerIndexes.put(trainerName, currentIndex);
                }
                currentIndex += line.length() + System.lineSeparator().length();
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return trainerIndexes;
    }

    public static final Trainer loadTrainer(String name){
        String trainers = DataManager.readFile(new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h"));

        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        int trainerIndex = MainActivity.trainerIndexes.get(name);
        int nextTrainerIndex = keys.indexOf(name) + 1 == keys.size()
                ? trainers.length()
                : MainActivity.trainerIndexes.get(keys.get(keys.indexOf(name) + 1));
        trainers = trainers.substring(trainerIndex, nextTrainerIndex).replace("};", "");
        trainers = trainers.replaceAll(System.lineSeparator(), "");
        trainers = trainers.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
        trainers = trainers.substring(("[" + name + "]={.").length(), trainers.length() - 3);

        HashMap<String, String> values = Trainer.templateValues();
        String[] trainer = trainers.split(",\\.");

        for(String field : trainer){
            String key = field.substring(0, field.indexOf("="));
            String value = field.substring(field.indexOf("=") + 1);
            values.put(key, value);
        }
        return new Trainer(name, values);
    }

    public static final void saveTrainer(Trainer trainer){
        File file = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainers.h");
        String trainers = DataManager.readFile(file);

        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        int trainerIndex = MainActivity.trainerIndexes.get(trainer.key);
        int nextTrainerIndex = keys.indexOf(trainer.key) + 1 == keys.size()
                ? trainers.length()
                : MainActivity.trainerIndexes.get(keys.get(keys.indexOf(trainer.key) + 1));
        boolean isLast = nextTrainerIndex == trainers.length();
        String trainerStruct = trainer.buildTrainerStruct();
        if(isLast) trainerStruct += "};";
        String[] lines = (trainers.substring(0, trainerIndex) + trainerStruct + trainers.substring(nextTrainerIndex))
                .split(System.lineSeparator(), -1);
        try {
            Files.write(file.toPath(), Arrays.asList(lines));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        MainActivity.trainerIndexes = DataManager.indexTrainers();
        saveParty(trainer);
    }

    public static final LinkedHashMap<String, Integer> indexParties(){
        File file = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h");
        LinkedHashMap<String, Integer> partyIndexes = new LinkedHashMap<>();
        int currentIndex = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!=null) {
                if(line.contains("static")){
                    String partyName = line.substring(line.indexOf("sParty"), line.indexOf("[")).replace(" ", "");
                    partyIndexes.put(partyName, currentIndex);
                }
                currentIndex += line.length() + System.lineSeparator().length();
            }
            fr.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                fr.close();
                br.close();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return partyIndexes;
    }

    public static final LinkedList<PartyMember> loadParty(String name){
        String parties = DataManager.readFile(new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h"));

        LinkedList<String> keys = new LinkedList<>(MainActivity.partyIndexes.keySet());
        int partyIndex = MainActivity.partyIndexes.get(name);
        int nextPartyIndex = keys.indexOf(name) + 1 == keys.size()
                            ? parties.length()
                            : MainActivity.partyIndexes.get(keys.get(keys.indexOf(name) + 1));
        parties = parties.substring(partyIndex, nextPartyIndex);
        String partyType = parties.substring(parties.indexOf("TrainerMon"), parties.indexOf("Moves") + "Moves".length());
        String partyDeclaration = "staticconststruct" + partyType + name + "[]={{";
        parties = parties.replaceAll(System.lineSeparator(), "").replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
        parties = parties.substring(partyDeclaration.length(), parties.length() - 3);

        String[] party = parties.split("},\\{");
        LinkedList<PartyMember> members = new LinkedList<>();
        for(String member : party){
            HashMap<String, String> values = PartyMember.templateValues();
            int commaIndex = member.endsWith(",") ? 1 : 0;
            member = member.substring(1, member.length() - commaIndex); //Remove first "." and last ","
            String[] memberData = member.split(",\\.");
            for(String field : memberData){
                String key = field.substring(0, field.indexOf("="));
                String value = field.substring(field.indexOf("=") + 1);
                values.put(key, value);
            }
            members.add(new PartyMember(values));
        }
        return members;
    }

    public static final void saveParty(Trainer trainer){
        File file = new File(MainActivity.projectDirectory
                + File.separator + "src" + File.separator + "data" + File.separator + "trainer_parties.h");
        String parties = DataManager.readFile(file);

        LinkedList<String> keys = new LinkedList<>(MainActivity.partyIndexes.keySet());
        int partyIndex = MainActivity.partyIndexes.get(trainer.partyName);
        int nextPartyIndex = keys.indexOf(trainer.partyName) + 1 == keys.size()
                ? parties.length()
                : MainActivity.partyIndexes.get(keys.get(keys.indexOf(trainer.partyName) + 1));
        String partyStruct = trainer.buildPartyStruct();
        String[] lines = (parties.substring(0, partyIndex) + partyStruct + parties.substring(nextPartyIndex))
                .split(System.lineSeparator(), -1);
        try {
            Files.write(file.toPath(), Arrays.asList(lines));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        MainActivity.partyIndexes = DataManager.indexParties();
    }
}
