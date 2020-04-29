package ui;

import main.MainActivity;
import main.Utils;
import types.PartyMember;
import types.Trainer;
import utils.DataManager;
import utils.DirectoryChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Screen extends JFrame {
    public MemberSelector memberSelector;

    private JTextField nameInput;
    private ComboBoxFiltered classInput;
    private JCheckBox doubleBattleCheck;
    private LinkedList<ComboBoxFiltered> itemsInput = new LinkedList<>();
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private ComboBoxFiltered musicInput;
    private ImagePanel picDisplay;
    private ComboBoxFiltered picInput;
    private LinkedHashMap<String, JCheckBox> aiFlags = new LinkedHashMap<>();

    public Screen(){
        setTitle("Decomp Trainer Editor");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUIStyle();
        initStartup();
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

    public void initStartup(){
        //Content pane of the frame
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        createMenu(contentPane);
        setContentPane(contentPane);
        revalidate();
    }

    public void initLoadProject(){
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
        createTrainerScrollable(editor);
        createEditorTabs(editor);
        panel.add(editor, BorderLayout.CENTER);
    }

    public void createTrainerScrollable(JPanel panel){
        JPanel general = new JPanel();
        general.setLayout(new BorderLayout());
        JPanel search = new JPanel();
        search.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weighty = 0.1;
        cons.fill = GridBagConstraints.BOTH;
        JList trainers = new JList();
        createTrainerList(trainers);
        JScrollPane trainersScrollable = new JScrollPane(trainers);
        search.add(trainersScrollable, cons);
        createTrainerSearchBar(search, trainers, cons);
        general.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        general.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        general.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        general.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        general.add(search, BorderLayout.CENTER);
        panel.add(general, BorderLayout.WEST);
    }

    public void createTrainerList(JList list){
        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        list.setPrototypeCellValue(Utils.getLongestString(keys));
        list.setListData(keys.toArray());
        list.setSelectedIndex(0);
        MainActivity.currentTrainer = list.getSelectedValue().toString();
        MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));
        list.addListSelectionListener(event -> {
            if(!event.getValueIsAdjusting() && list.getSelectedIndex() != -1) {
                saveTrainerData(MainActivity.currentTrainer);
                MainActivity.currentTrainer = list.getSelectedValue().toString();
                if(!MainActivity.loadedTrainers.containsKey(MainActivity.currentTrainer))
                    MainActivity.loadedTrainers.put(MainActivity.currentTrainer, DataManager.loadTrainer(MainActivity.currentTrainer));
                loadTrainerData(MainActivity.currentTrainer);
                revalidate();
            }
        });
    }

    private void createTrainerSearchBar(JPanel pane, JList list, GridBagConstraints cons){
        cons.gridy++;
        cons.weighty = 0;
        pane.add(Box.createVerticalStrut(5), cons);
        cons.gridy++;
        JTextField field = new JTextField();
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event) {
                String previousText, text;
                previousText = text = field.getText();
                if(String.valueOf(event.getKeyChar()).matches("[a-zA-Z]") || String.valueOf(event.getKeyChar()).equals("_")) text += event.getKeyChar();
                Object[] filtered = getFilteredTrainers(text);
                if(filtered.length == 0) SwingUtilities.invokeLater(() -> field.setText(previousText));
                else{
                    list.setModel(new DefaultComboBoxModel(filtered));
                    field.setText(previousText);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pane.add(field, cons);
    }

    public void createEditorTabs(JPanel pane){
        JPanel editorBorders = new JPanel();
        editorBorders.setLayout(new BorderLayout());
        JTabbedPane tabbedEditor = new JTabbedPane();
        tabbedEditor.setBackground(Color.WHITE);
        createGeneralTab(tabbedEditor);
        createAITab(tabbedEditor);
        createPartyTab(tabbedEditor);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        editorBorders.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        editorBorders.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        editorBorders.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        editorBorders.add(tabbedEditor, BorderLayout.CENTER);
        pane.add(editorBorders, BorderLayout.CENTER);
    }

    public void createGeneralTab(JTabbedPane pane){
        JPanel general = new JPanel();
        general.setBackground(Color.WHITE);
        general.setLayout(new FlowLayout(FlowLayout.LEFT));
        general.add(Box.createHorizontalStrut(5));
        createGeneralLeftColumn(general);
        general.add(Box.createHorizontalStrut(30));
        createGeneralRightColumn(general);
        pane.add("General", general);
    }

    public void createGeneralLeftColumn(JPanel pane){
        JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        createNameInput(column, cons);
        createTrainerClassInput(column, cons);
        createDoubleBattleInput(column, cons);
        createItemsInput(column, cons);
        pane.add(column);
    }

    public void createGeneralRightColumn(JPanel pane){
        JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.LINE_START;
        createTrainerPicInput(column, cons);
        createGenderInput(column, cons);
        createMusicInput(column, cons);
        pane.add(column);
    }

    public void createNameInput(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(nameLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        cons.fill = GridBagConstraints.NONE;
        nameInput = new JTextField(11);
        nameInput.setDocument(new TextFieldLimiter(11, -1));
        pane.add(nameInput, cons);
    }

    public void createTrainerClassInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        cons.fill = GridBagConstraints.BOTH;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel classLabel = new JLabel("Trainer class: ");
        classLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(classLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        LinkedList<String> values = new LinkedList<>(MainActivity.trainerClasses.keySet());
        classInput = new ComboBoxFiltered(values, "");
        classInput.setPrototypeDisplayValue(Utils.getLongestString(new ArrayList<>(MainActivity.trainerClasses.keySet())));
        pane.add(classInput, cons);
    }

    public void createDoubleBattleInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JPanel doubleBattlePanel = new JPanel();
        doubleBattlePanel.setBackground(Color.WHITE);
        doubleBattlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        doubleBattleCheck = new JCheckBox();
        doubleBattleCheck.setBackground(Color.WHITE);
        JLabel doubleBattleLabel = new JLabel("Double battle");
        doubleBattleLabel.setHorizontalAlignment(JLabel.LEFT);
        doubleBattlePanel.add(doubleBattleCheck); doubleBattlePanel.add(doubleBattleLabel);
        pane.add(doubleBattlePanel, cons);
    }

    public void createItemsInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel itemsLabel = new JLabel("Items: ");
        itemsLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(itemsLabel, cons);
        for(int index = 0; index < 4; index++){
            cons.gridy++;
            pane.add(Box.createVerticalStrut(5), cons);

            cons.gridy++;
            cons.fill = GridBagConstraints.NONE;
            ComboBoxFiltered itemInput = new ComboBoxFiltered(MainActivity.items, MainActivity.items.get(0));
            itemInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.items));
            pane.add(itemInput, cons);
            itemsInput.add(itemInput);
        }
        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);
    }

    public void createTrainerPicInput(JPanel pane, GridBagConstraints cons){
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel picLabel = new JLabel("Trainer pic: ");
        picLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(picLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);

        cons.gridy++;
        picDisplay = new ImagePanel();
        picDisplay.setBackground(Color.WHITE);
        pane.add(picDisplay, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(10), cons);

        cons.gridy++;
        picInput = new ComboBoxFiltered(MainActivity.picList, "");
        picInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.picList));
        picInput.addActionListener(e -> {
            picDisplay.imagePath = MainActivity.picPaths.get(picInput.getSelectedItem());
            picDisplay.repaint();
        });
        pane.add(picInput, cons);
    }

    public void createGenderInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(genderLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        maleButton = new JRadioButton("Male");
        maleButton.setBackground(Color.WHITE);
        maleButton.addActionListener(event -> {
            femaleButton.setSelected(false);
        });
        pane.add(maleButton, cons);

        cons.gridy++;
        femaleButton = new JRadioButton("Female");
        femaleButton.setBackground(Color.WHITE);
        femaleButton.addActionListener(event -> {
            maleButton.setSelected(false);
        });
        pane.add(femaleButton, cons);
    }

    public void createMusicInput(JPanel pane, GridBagConstraints cons){
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
        cons.gridy++;
        JLabel musicLabel = new JLabel("Encounter music: ");
        musicLabel.setHorizontalAlignment(JLabel.LEFT);
        pane.add(musicLabel, cons);

        cons.gridy++;
        pane.add(Box.createVerticalStrut(5), cons);

        cons.gridy++;
        musicInput = new ComboBoxFiltered(MainActivity.music, "");
        musicInput.setPrototypeDisplayValue(Utils.getLongestString(MainActivity.music));
        pane.add(musicInput, cons);
        cons.gridy++;
        pane.add(Box.createVerticalStrut(30), cons);
    }

    public void createAITab(JTabbedPane pane){
        JPanel general = new JPanel();
        general.setBackground(Color.WHITE);
        general.setLayout(new FlowLayout(FlowLayout.LEFT));
        general.add(Box.createHorizontalStrut(5));
        JScrollPane scroll = new JScrollPane(general);
        scroll.setBorder(null);
        JPanel aiList = new JPanel();
        aiList.setBackground(Color.WHITE);
        aiList.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        aiList.add(Box.createVerticalStrut(5), cons);
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String ai = MainActivity.aiFlags.get(index);
            JPanel aiPanel = new JPanel();
            aiPanel.setBackground(Color.WHITE);
            aiPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JCheckBox aiCheck = new JCheckBox();
            aiCheck.setBackground(Color.WHITE);

            JLabel aiLabel = new JLabel(ai);
            aiLabel.setHorizontalAlignment(JLabel.LEFT);
            aiPanel.add(aiCheck); aiPanel.add(aiLabel);
            cons.gridy++;
            aiList.add(aiPanel, cons);
            aiFlags.put(ai, aiCheck);
        }
        cons.gridy++;
        aiList.add(Box.createVerticalStrut(10), cons);
        general.add(aiList, BorderLayout.CENTER);
        pane.add("AI", scroll);
    }

    public void createPartyTab(JTabbedPane pane){
        JPanel general = new JPanel();
        general.setBackground(Color.WHITE);
        general.setLayout(new FlowLayout(FlowLayout.LEFT));
        general.add(Box.createHorizontalStrut(10));
        JPanel spacer = new JPanel();
        spacer.setLayout(new BoxLayout(spacer, BoxLayout.PAGE_AXIS));
        spacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        memberSelector = new MemberSelector(6, true);
        spacer.add(memberSelector);
        general.add(spacer);
        pane.add("Party", general);
    }

    public Object[] getFilteredTrainers(String text){
        LinkedList<String> elements = new LinkedList<>();
        LinkedList<String> keys = new LinkedList<>(MainActivity.trainerIndexes.keySet());
        text = text.toLowerCase();
        for(String element : keys){
            if(element.toLowerCase().contains(text)){
                    elements.add(element);
                }
        }
        return elements.toArray();
    }

    public void loadTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        nameInput.setText(trainer.trainerName);
        classInput.setSelectedItem(trainer.trainerClass);
        doubleBattleCheck.setSelected(trainer.doubleBattle);
        picDisplay.imagePath = MainActivity.picPaths.get(trainer.trainerPic);
        picInput.setSelectedItem(trainer.trainerPic);
        musicInput.setSelectedItem(trainer.music);
        boolean male = trainer.gender.equals("");
        maleButton.setSelected(male);
        femaleButton.setSelected(!male);
        for(int index = 0; index < 4; index++){
            String item = MainActivity.items.get(0);
            if(trainer.items.size() > 0){
                item = trainer.items.get(index);
            }
            itemsInput.get(index).setSelectedItem(item);
        }
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            aiFlags.get(flag).setSelected(trainer.aiFlags.contains(flag));
        }
        memberSelector.clearAssociations();
        for(int index = 0; index < trainer.party.size(); index++){
            loadPartyData(trainer.party.get(index));
        }
        memberSelector.setSelectedIndex(0);
    }

    public void loadPartyData(PartyMember member){
        PartyMemberInput memberInputs = new PartyMemberInput();
        memberInputs.iv.setText(member.iv);
        memberInputs.level.setText(member.level);
        memberInputs.species.setSelectedItem(member.species);
        if(!member.heldItem.equals("")) memberInputs.heldItem.setSelectedItem(member.heldItem);
        for(int subindex = 0; subindex < member.moves.size(); subindex++){
            memberInputs.moves.get(subindex).setSelectedItem(MainActivity.moves.get(member.moves.get(subindex)));
        }
        memberSelector.addAssociation(memberInputs);
    }

    public void saveTrainerData(String name){
        Trainer trainer = MainActivity.loadedTrainers.get(name);
        trainer.trainerName = nameInput.getText();
        trainer.trainerClass = classInput.getSelectedItem().toString();
        trainer.doubleBattle = doubleBattleCheck.isSelected();
        trainer.trainerPic = picInput.getSelectedItem().toString();
        trainer.music = musicInput.getSelectedItem().toString();
        String gender = "";
        if(femaleButton.isSelected()) gender += "F_TRAINER_FEMALE";
        trainer.gender = gender;
        ArrayList<String> aiFlags = new ArrayList<>();
        for(int index = 0; index < MainActivity.aiFlags.size(); index++){
            String flag = MainActivity.aiFlags.get(index);
            if(this.aiFlags.get(flag ).isSelected()) aiFlags.add(flag);
        }
        trainer.aiFlags = aiFlags;
        ArrayList<String> items = new ArrayList<>();
        boolean areAllItemsNone = true;
        for(ComboBoxFiltered item : itemsInput){
            JTextField field = (JTextField) item.getEditor().getEditorComponent();
            if(!field.getText().equals(MainActivity.items.get(0))) areAllItemsNone = false;
            items.add(field.getText());
        }
        if(areAllItemsNone) items = new ArrayList<>();
        trainer.items = items;
        savePartyData(trainer);
        MainActivity.loadedTrainers.put(name, trainer);
    }

    public void savePartyData(Trainer trainer){
        LinkedList<PartyMember> party = new LinkedList<>();
        for(int index = 0; index < memberSelector.associations.size(); index++){
            PartyMemberInput memberInputs = memberSelector.associations.get(index);
            PartyMember member = new PartyMember();
            member.iv = memberInputs.iv.getText();
            member.level = memberInputs.level.getText();
            String species = memberInputs.species.getSelectedItem().toString();
            member.species = species;
            member.heldItem = memberInputs.heldItem.getSelectedItem().toString();
            LinkedList<String> keys = new LinkedList<>(MainActivity.moves.keySet());
            LinkedList<String> values = new LinkedList<>(MainActivity.moves.values());
            for(int subindex = 0; subindex < memberInputs.moves.size(); subindex++){
                String move = memberInputs.moves.get(subindex).getSelectedItem().toString();
                int indexOfMove = values.indexOf(move);
                member.moves.add(keys.get(indexOfMove));
            }
            party.add(member);
        }
        trainer.party = party;
    }
}
