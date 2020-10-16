package me.disturbo.main;

import javax.swing.*;
import java.io.File;

public class DirectoryChooser {
    /*
            The DirectoryClass class serves the purpose of opening the file explorer and selecting the project path
    */

    public DirectoryChooser(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showOpenDialog(MainActivity.mainFrame) == JFileChooser.APPROVE_OPTION){
            MainActivity.projectDirectory = chooser.getSelectedFile();
            MainActivity.mainFrame.getContentPane().removeAll();
            MainActivity.mainFrame.initProject();
        }
    }
}
