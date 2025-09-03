package org.example;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

public class SpacePanel extends JPanel {
    public SpacePanel(){
        super();

    }
    public void scalePlayerImage(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Main.player.draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
