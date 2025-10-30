package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Planet {
    private int x, y;
    private int radius;
    private Color color;
    public EconomicProfile profile;
    public Map<Commodity, Integer> inventory = new HashMap<>();
    private Map<Commodity, Integer> prices = new HashMap<>();

    public Planet(int x, int y, int radius, Color color, EconomicProfile profile) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.profile = profile;
        initializeEconomy();
    }
    private void initializeEconomy() {
        for(Commodity item: Commodity.values()) {
            inventory.put(item, 100);
        }
        inventory.put(profile.getPrimaryExport(), 500);
        inventory.put(profile.getSecondaryExport(), 300);

        inventory.put(profile.getPrimaryImport(), 20);
        inventory.put(profile.getSecondaryImport(), 40);
        updatePrices();
    }
    public void updatePrices() {
        for(Commodity item: Commodity.values()) {
            int base = item.getBasePrice();
            int stock = inventory.get(item);
            double demandFactor = Math.max(0.2, Math.min(5.0, 100.0/stock));
            int finalPrice = (int)(base * demandFactor);
            prices.put(item, finalPrice);
        }
    }
    public Map<Commodity, Integer> getPrices() {
        return prices;
    }
    public void buyFromPlanet(Commodity item, int quantity) {
        inventory.put(item, inventory.get(item) - quantity);
        updatePrices();
    }
    
    public void sellToPlanet(Commodity item, int quantity) {
        inventory.put(item, inventory.get(item) + quantity);
        updatePrices();
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
}