package ui.extensions;

import main.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImagePanel extends JPanel {
    private String path = "";
    private int width = 64, height = 64;


    public void setImage(String path){
        this.path = path;
        ImageIcon image = new ImageIcon(MainActivity.projectDirectory.getPath() + File.separator + path);
        width = image.getIconWidth(); height = image.getIconHeight();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize(){
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize(){
        return getPreferredSize();
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if(!path.equals("")){
            ImageIcon image = new ImageIcon(MainActivity.projectDirectory.getPath() + File.separator + path);
            graphics.drawImage(image.getImage(), getWidth() / 3, 0, this);
        }
    }
}
