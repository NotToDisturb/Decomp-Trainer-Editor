package me.disturbo.ui;

import me.disturbo.main.MainActivity;
import me.disturbo.types.Trainer;
import me.disturbo.ui.ai.AiFlagsPanel;
import me.disturbo.ui.list.TrainerListPanel;
import me.disturbo.ui.party.PartyPanel;
import me.disturbo.ui.trainer.TrainerPanel;
import me.disturbo.data.DataManager;
import me.disturbo.main.DirectoryChooser;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    /*
            The MainFrame class is responsible for initialising the menu and the editor tabs
    */

    private TrainerPanel generalInput;
    private AiFlagsPanel aiInput;
    private PartyPanel partyPanel;

    public MainFrame(){
        setTitle("Decomp Trainer Editor");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUIStyle();
        initNoProject();
        setVisible(true);
    }

    private final void initUIStyle(){
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

    private final void initNoProject(){
        //Content pane of the frame
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        createMenu(contentPane);
        setContentPane(contentPane);
        revalidate();
    }

    public final void initProject(){
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

    private final void createMenu(JPanel panel){
        JMenuBar menu = new JMenuBar();
        JMenu project = new JMenu("Project");
        JMenuItem open = new JMenuItem("Open directory");
        open.addActionListener(event -> new DirectoryChooser());
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(event -> {
            //If there is a loaded trainer, allow saving trainers
            if(MainActivity.currentTrainer != null){
                saveTrainerData(MainActivity.currentTrainer);
                LinkedList<Trainer> saveQueue = new LinkedList<>();
                for(Trainer trainer : MainActivity.loadedTrainers.values()){
                    if(saveQueue.isEmpty()) saveQueue.add(trainer);
                    else {
                        boolean inserted = false;
                        for(int insertionIndex = 0; insertionIndex < saveQueue.size(); insertionIndex++){
                            int savingIndex = MainActivity.trainerIndexes.get(trainer.name);
                            int savedIndex = MainActivity.trainerIndexes.get(saveQueue.get(insertionIndex).name);
                            if(savingIndex < savedIndex){
                                saveQueue.add(insertionIndex, trainer);
                                inserted = true;
                                break;
                            }
                        }
                        if(!inserted) saveQueue.add(trainer);
                    }
                }
                DataManager.saveTrainers(saveQueue);
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        project.add(open); project.add(save); project.add(new JSeparator()); project.add(exit);
        menu.add(project);
        panel.add(menu, BorderLayout.NORTH);
    }

    private final void createEditor(JPanel panel){
        JPanel editor = new JPanel();
        editor.setLayout(new BorderLayout());
        panel.add(new TrainerListPanel(this), BorderLayout.WEST);
        createEditorTabs(editor);
        panel.add(editor, BorderLayout.CENTER);
    }

    private final void createEditorTabs(JPanel pane){
        JPanel editorBorders = new JPanel();
        editorBorders.setLayout(new BorderLayout());
        JTabbedPane tabbedEditor = new JTabbedPane();
        tabbedEditor.setBackground(Color.WHITE);
        generalInput = new TrainerPanel();
        tabbedEditor.add("General", generalInput);
        aiInput = new AiFlagsPanel();
        tabbedEditor.add("AI", aiInput);
        partyPanel = new PartyPanel(this);
        tabbedEditor.add("Party", partyPanel);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        editorBorders.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        editorBorders.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        editorBorders.add(tabbedEditor, BorderLayout.CENTER);
        pane.add(editorBorders, BorderLayout.CENTER);
    }

    public final void loadTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        generalInput.loadTrainerGeneralData(trainer);
        aiInput.loadTrainerAiData(trainer);
        partyPanel.loadTrainerPartyData(trainer);
    }

    public final void saveTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        generalInput.saveTrainerGeneralData(trainer);
        aiInput.saveTrainerAiData(trainer);
        partyPanel.saveTrainerPartyData(trainer);
        MainActivity.loadedTrainers.put(name, trainer);
    }
}
