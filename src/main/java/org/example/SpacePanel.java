package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class SpacePanel extends JPanel {
    private GameWorld world;
    private Starfield starfield;
    private Font ui;
    private Font uiBold;
    public SpacePanel(GameWorld world) {
        
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.BLACK);
        setFocusable(true);
        this.world = world;
        this.ui = FontManager.loadFont("/fonts/Orbitron-Regular.ttf", 14f);
        this.uiBold = FontManager.loadFont("/fonts/Orbitron-Regular.ttf", 16f);
        
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
        if(player.isLanded()) {
            g.setColor(Color.WHITE);
            g.setFont(uiBold);
            g.drawString("Landed. Press SPACE to take off.", 10, 20);
                drawTradeUI(g,player);
            

        }
        else {
            drawFlyingHUD(g, player);
        }
        
    }
    private void drawFlyingHUD(Graphics2D g, Player player) {
        
        String systemName = world.getCurrentSystem().getName();
        g.setFont(uiBold);
        int stringWidth = g.getFontMetrics().stringWidth(systemName);
        g.setColor(new Color(0,0,0, 150));
        g.fillRect(getWidth() / 2 - stringWidth / 2 - 10, 10, stringWidth + 20, 30);
        g.setColor(Color.WHITE);
        g.drawString(systemName, getWidth() / 2 - stringWidth / 2, 32);


        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(10, getHeight() - 110, 250, 100);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(10, getHeight() - 110, 250, 100);

        g.setFont(uiBold);
        g.setColor(Color.CYAN);

        g.drawString(String.format("Credits: %d", player.getCredits()), 20, getHeight() - 85);

        double velocity = Math.sqrt(player.velocityX * player.velocityX + player.velocityY * player.velocityY);
        g.drawString(String.format("Velocity: %.1f", velocity * 10), 20, getHeight() - 65);

        g.setColor(Color.WHITE);
        g.drawString(String.format("Cargo: %d / %d", player.getCurrentCargoLoad(), player.getCargoCapacity()), 20, getHeight() - 45);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(20, getHeight() - 35, 230, 15);

        double cargoPercent = (double)player.getCurrentCargoLoad() / player.getCargoCapacity();
        g.setColor(Color.ORANGE);
        g.fillRect(20, getHeight() - 35, (int)(230 * cargoPercent), 15);

        g.setColor(Color.GRAY);
        g.drawRect(20, getHeight() - 35, 230, 15);
        
        if(player.canLand()) { 
            g.setFont(uiBold);
            String landMsg = "In landing range. Press L to land.";
            int landMsgWidth = g.getFontMetrics().stringWidth(landMsg);
            g.setColor(new Color(0, 100, 0, 200));
            g.fillRect(getWidth() / 2 - landMsgWidth / 2 - 10, getHeight() - 50, landMsgWidth + 20, 30);
            g.setColor(Color.GREEN);
            g.drawString(landMsg, getWidth() / 2 - landMsgWidth / 2, getHeight() - 30);
        }
    }
    private void drawTradeUI(Graphics2D g, Player player) {
        Planet planet = world.getCurrentLandedPlanet();
        if(planet == null) return;
        int panelWidth = 800;
        int panelHeight = 600;
        int panelX = (getWidth() - panelWidth) / 2;
        int panelY = (getHeight() - panelHeight) / 2;

        g.setColor(new Color(0, 0, 20, 220)); 
        g.fillRect(panelX, panelY, panelWidth, panelHeight);
        g.setColor(Color.CYAN);
        g.drawRect(panelX, panelY, panelWidth, panelHeight);

        g.setFont(ui);
        g.setColor(Color.YELLOW);
        g.drawString(planet.profile.toString() + " MARKET", panelX + 10, panelY + 25);
        
        g.setFont(ui);
        g.setColor(Color.GRAY);
        g.drawString("Use LEFT/RIGHT to switch panels, UP/DOWN to select", panelX + 10, panelY + 50);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(panelX, panelY + panelHeight - 60, panelWidth, 60);
        g.setColor(Color.CYAN);
        g.drawRect(panelX, panelY + panelHeight - 60, panelWidth, 60);

        g.setFont(uiBold);
        g.drawString(String.format("Credits: %d", player.getCredits()), panelX + 10, panelY + panelHeight - 35);
        
        g.setColor(Color.WHITE);
        g.drawString(String.format("Cargo: %d / %d", player.getCurrentCargoLoad(), player.getCargoCapacity()), panelX + 250, panelY + panelHeight - 35);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(panelX + 400, panelY + panelHeight - 40, 380, 20);
        double cargoPercent = (double)player.getCurrentCargoLoad() / player.getCargoCapacity();
        g.setColor(Color.ORANGE);
        g.fillRect(panelX + 400, panelY + panelHeight - 40, (int)(380 * cargoPercent), 20);
        g.setColor(Color.GRAY);
        g.drawRect(panelX + 400, panelY + panelHeight - 40, 380, 20);


        Map<Commodity, Integer> prices = planet.getPrices();
        Map<Commodity, Integer> playerCargo = player.getCargoHold();
        Commodity[] allCommodities = Commodity.values();
        int selectedIndex = world.getSelectedTradeItemIndex();
        boolean isPlayerFocused = world.isPlayerCargoFocused();

        int listX_Planet = panelX + 10;
        int listY_Start = panelY + 80;
        g.setFont(uiBold);
        g.setColor(isPlayerFocused ? Color.GRAY : Color.WHITE); 
        g.drawString("PLANET INVENTORY (B to Buy)", listX_Planet, listY_Start);
        g.drawString(String.format("%-15s %-8s %-8s", "Item", "Price", "Stock"), listX_Planet, listY_Start + 25);
        
        g.setFont(ui);
        for (int i = 0; i < allCommodities.length; i++) {
            Commodity item = allCommodities[i];
            int itemY = listY_Start + 50 + (i * 20);
            String line = String.format("%-15s %-8d %-8d", 
                item.getName(), 
                prices.get(item), 
                planet.inventory.get(item)); 
                
            g.setColor(isPlayerFocused ? Color.DARK_GRAY : Color.WHITE);
            if (i == selectedIndex && !isPlayerFocused) {
                g.setColor(Color.YELLOW);
                g.drawString(">", listX_Planet - 10, itemY);
            }
            g.drawString(line, listX_Planet, itemY);
        }

        int listX_Player = panelX + 410;
        g.setFont(uiBold);
        g.setColor(isPlayerFocused ? Color.WHITE : Color.GRAY);
        g.drawString("PLAYER CARGO (S to Sell)", listX_Player, listY_Start);
        g.drawString(String.format("%-15s %-8s %-8s", "Item", "Price", "Held"), listX_Player, listY_Start + 25);

        g.setFont(ui);
        for (int i = 0; i < allCommodities.length; i++) {
            Commodity item = allCommodities[i];
            int itemY = listY_Start + 50 + (i * 20);
            String line = String.format("%-15s %-8d %-8d", 
                item.getName(), 
                prices.get(item), 
                playerCargo.get(item));
                
            g.setColor(isPlayerFocused ? Color.WHITE : Color.DARK_GRAY);
            if (i == selectedIndex && isPlayerFocused) {
                g.setColor(Color.YELLOW);
                g.drawString(">", listX_Player - 10, itemY);
            }
            g.drawString(line, listX_Player, itemY);
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