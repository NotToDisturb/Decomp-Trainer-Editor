package ui;

import main.MainActivity;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImagePanel extends JPanel {
    public String imagePath = "";

    public ImagePanel(){
        super();
        add(Box.createRigidArea(new Dimension(64, 64)));
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if(!imagePath.equals("")){
            ImageIcon image = new ImageIcon(MainActivity.projectDirectory.getPath() + File.separator + imagePath);
            graphics.drawImage(image.getImage(), getWidth() / 3, 0, this);
        }
    }
}
