package utils;

import main.MainActivity;
import ui.Screen;

import javax.swing.*;
import java.io.File;

public class DirectoryChooser {
    public DirectoryChooser(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showOpenDialog(MainActivity.screen) == JFileChooser.APPROVE_OPTION){
            MainActivity.projectDirectory = chooser.getSelectedFile();
            MainActivity.screen.getContentPane().removeAll();
            MainActivity.screen.initLoadProject();
        }
    }
}
