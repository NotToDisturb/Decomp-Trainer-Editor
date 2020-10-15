package ui;

import main.MainActivity;
import types.Trainer;
import utils.DataManager;
import utils.DirectoryChooser;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public GeneralTrainerInput generalInput;
    public AiFlagsInput aiInput;
    public PartyInput partyInput;

    public MainFrame(){
        setTitle("Decomp Trainer Editor");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUIStyle();
        populate();
        setVisible(true);
    }

    private void initUIStyle(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException exception){
            exception.printStackTrace();
        }
    }

    public void populate(){
        //Content pane of the frame
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        createMenu(contentPane);
        setContentPane(contentPane);
        revalidate();
    }

    public void loadProject(){
        JPanel contentPane = (JPanel) getContentPane();
        MainActivity.trainerIndexes = DataManager.indexTrainers();
        MainActivity.partyIndexes = DataManager.indexParties();
        MainActivity.trainerClasses = DataManager.loadTrainerClasses();
        MainActivity.items = DataManager.loadItems();
        MainActivity.moves = DataManager.loadMoves();
        MainActivity.species = DataManager.loadSpecies();
        MainActivity.music = DataManager.loadMusic();
        MainActivity.aiFlags = DataManager.loadAiFlags();
        MainActivity.picList = DataManager.loadTrainerPicsList();
        MainActivity.picPaths = DataManager.loadTrainerPicsPaths();
        createMenu(contentPane);
        createEditor(contentPane);
        loadTrainerData(MainActivity.currentTrainer);
        setContentPane(contentPane);
    }

    public void createMenu(JPanel panel){
        JMenuBar menu = new JMenuBar();
        JMenu project = new JMenu("Project");
        JMenuItem open = new JMenuItem("Open directory");
        open.addActionListener(event -> new DirectoryChooser());
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(event -> {
            //If there is a loaded trainer, allow saving trainers
            if(MainActivity.currentTrainer != null){
                MainActivity.screen.saveTrainerData(MainActivity.currentTrainer);
                for(Trainer trainer : MainActivity.loadedTrainers.values()){
                    DataManager.saveTrainer(trainer);
                }
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        project.add(open); project.add(save); project.add(new JSeparator()); project.add(exit);
        menu.add(project);
        panel.add(menu, BorderLayout.NORTH);
    }

    public void createEditor(JPanel panel){
        JPanel editor = new JPanel();
        editor.setLayout(new BorderLayout());
        panel.add(new TrainerListInput(), BorderLayout.WEST);
        createEditorTabs(editor);
        panel.add(editor, BorderLayout.CENTER);
    }

    public void createEditorTabs(JPanel pane){
        JPanel editorBorders = new JPanel();
        editorBorders.setLayout(new BorderLayout());
        JTabbedPane tabbedEditor = new JTabbedPane();
        tabbedEditor.setBackground(Color.WHITE);
        generalInput = new GeneralTrainerInput();
        tabbedEditor.add("General", generalInput);
        aiInput = new AiFlagsInput();
        tabbedEditor.add("AI", aiInput);
        partyInput = new PartyInput();
        tabbedEditor.add("Party", partyInput);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        editorBorders.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        editorBorders.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        editorBorders.add(tabbedEditor, BorderLayout.CENTER);
        pane.add(editorBorders, BorderLayout.CENTER);
    }

    public void loadTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        generalInput.loadTrainerGeneralData(trainer);
        aiInput.loadTrainerAiData(trainer);
        partyInput.loadTrainerPartyData(trainer);
    }

    public void saveTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        generalInput.saveTrainerGeneralData(trainer);
        aiInput.saveTrainerAiData(trainer);
        partyInput.saveTrainerPartyData(trainer);
        MainActivity.loadedTrainers.put(name, trainer);
    }
}
