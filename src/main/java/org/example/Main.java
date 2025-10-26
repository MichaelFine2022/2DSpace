package org.example;

import javax.swing.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static Player player;
    static SpacePanel spacePanel;
    static ArrayList<Planet> planets = new ArrayList<>();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                try {
                    GameWorld world = new GameWorld();

                    SpacePanel spacePanel = new SpacePanel(world);

                    JFrame frame = new JFrame("2D Space");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setUndecorated(true);
                    frame.add(spacePanel);

                    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    device.setFullScreenWindow(frame);
                    
                    frame.setVisible(true);
                    

                    GameController controller = new GameController(world, spacePanel);
                    controller.start();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        });
    }
    public static void updateGameState() {
        if (player.isLanded()) {
            player.setCanLand(false);
            return;
        }

        double playerCenterX = player.getX() + player.getSize() / 2.0;
        double playerCenterY = player.getY() + player.getSize() / 2.0;

        boolean isNearAnyPlanet = false;
        for (Planet planet : planets) {
            double dx = playerCenterX - planet.getX();
            double dy = playerCenterY - planet.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < planet.getRadius() + 30) {
                isNearAnyPlanet = true;
                break; 
            }
        }
        player.setCanLand(isNearAnyPlanet);
    }
}