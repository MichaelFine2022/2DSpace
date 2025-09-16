package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// The View: Only knows how to draw the GameWorld
public class SpacePanel extends JPanel {
    private GameWorld world;

    public SpacePanel(GameWorld world) {
        this.world = world;
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        Player player = world.getPlayer();
        if (player == null) return;

        double cameraX = player.getX() - getWidth() / 2.0;
        double cameraY = player.getY() - getHeight() / 2.0;
        g2d.translate(-cameraX, -cameraY);

        for (Planet planet : world.getPlanets()) {
            planet.draw(g2d);
        }

        try {
            player.draw(g2d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.translate(cameraX, cameraY);
        drawUI(g2d);
    }
    
    private void drawUI(Graphics2D g) {
        Player player = world.getPlayer();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        if (player.isLanded()) {
            g.drawString("Landed. Press SPACE to take off.", 10, 20);
        } else if (player.canLand()) {
            g.drawString("In landing range. Press L to land.", 10, 20);
        }
    }
}