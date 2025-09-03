package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    static Player player;
    public static void setUpTestUI(){
        JFrame Space = new JFrame("Space Game");
        Space.setSize(400,400);
        Space.setVisible(true);

        SpacePanel spacePanel = new SpacePanel();
        spacePanel.setBackground(Color.BLACK);

        Space.add(spacePanel, BorderLayout.CENTER);
    }
    public static void setUpPlayer() throws IOException{
        player = new Player(0, 0);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setUpTestUI();
                try {
                    setUpPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}