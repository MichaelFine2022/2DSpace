package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class SpacePanel extends JPanel {
    private GameWorld world;
    private Starfield starfield;
    public SpacePanel(GameWorld world) {
        
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.BLACK);
        setFocusable(true);
        this.world = world;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(starfield == null) {
            this.starfield = new Starfield(1000, getWidth(), getHeight());
        }
        Player player = world.getPlayer();
        if (player == null) return;

        double cameraX = player.getX() - getWidth() / 2.0;
        double cameraY = player.getY() - getHeight() / 2.0;
        g2d.translate(-cameraX, -cameraY);
        starfield.draw(g2d);
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
            drawTradeUI(g,player);
        } else {
            g.drawString("System: " + world.getCurrentSystem().getName(), 10, 40);
            g.drawString("Press 'J' to Jump", 10, 60);
            if(player.canLand()) {
                g.drawString("In landing range. Press L to land.", 10, 20);
            }
        }
    }

    private void drawTradeUI(Graphics2D g, Player player) {
        Planet planet = world.getCurrentLandedPlanet();
        if(planet == null) {
            return;
        }
        int y = 100;
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.setColor(Color.CYAN);
        g.drawString(String.format("Credits: %d", player.getCredits()), 600, 40);
        g.drawString(String.format("Cargo: %d / %d", player.getCurrentCargoLoad(), player.getCargoCapacity()), 600, 60);
    
        g.setColor(Color.YELLOW);
        g.drawString("TRADE MENU (Use UP/DOWN, B to Buy, S to Sell)", 10, y);
        y += 30;
        g.drawString(String.format("%-15s %-10s %-10s %-10s", "Commodity", "Price", "Planet", "Player"), 10, y);
        y += 20;

        Map<Commodity, Integer> prices = planet.getPrices();
        Map<Commodity, Integer> playerCargo = player.getCargoHold();
        Commodity[] allCommodities = Commodity.values();
        int selectedIndex = world.getSelectedTradeItemIndex();
        for(int i = 0; i < allCommodities.length; i++) {
            Commodity item = allCommodities[i];
            String itemLine = String.format("%-15s %-10d %-10s %-10d",
                item.getName(),
                prices.get(item),
                "~",
                playerCargo.get(item));
            if(i == selectedIndex) {
                g.setColor(Color.YELLOW);
                g.drawString(">", 5, y);
            }
            else {
                g.setColor(Color.WHITE);
            }

            g.drawString(itemLine, 20, y);
            y += 20;
            
        }
    }

    public void updateStarfield() {
        Player player = world.getPlayer();
        int camX = (int) (player.getX() - getWidth() / 2.0);
        int camY = (int) (player.getY() - getHeight() / 2.0);
        Rectangle cameraBounds = new Rectangle(camX, camY, getWidth(), getHeight());

        starfield.update(cameraBounds);
    }
}