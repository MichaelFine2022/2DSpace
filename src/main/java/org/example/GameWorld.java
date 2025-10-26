package org.example;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class GameWorld {
    private Player player;
    private ArrayList<StarSystem> universe = new ArrayList<>();
    private int currentSystemIndex = 0;
    private Planet currentLandedPlanet = null;
    private int selectedTradeItemIndex = 0;

    public GameWorld() throws IOException {
        player = new Player(50, 50);

        StarSystem sol = new StarSystem("Sol");
        sol.addPlanet(new Planet(200, 200, 50, Color.decode("#42a5f5"), EconomicProfile.INDUSTRIAL)); // Earth
        sol.addPlanet(new Planet(600, 450, 80, Color.decode("#ef5350"), EconomicProfile.MINING)); // Mars
        universe.add(sol);

        StarSystem alphaCentauri = new StarSystem("Alpha Centauri");
        alphaCentauri.addPlanet(new Planet(350, 400, 120, Color.decode("#ffca28"), EconomicProfile.AGRICULTURAL)); 
        universe.add(alphaCentauri);
    }

    public void update() {
        player.update();
        checkLandingProximity();
    }
    public void jumpToNextSystem() {
        if (player.isLanded()) {
            return;
        }

        currentSystemIndex = (currentSystemIndex + 1) % universe.size();
        
        player.setPosition(50, 50);
        player.resetVelocity();
    }
    private void checkLandingProximity() {
        if (player.isLanded()) {
            player.setCanLand(false);
            return;
        }
        currentLandedPlanet = null;
        double playerCenterX = player.getX() + player.getSize() / 2.0;
        double playerCenterY = player.getY() + player.getSize() / 2.0;

        boolean isNearAnyPlanet = false;
        for (Planet planet : getCurrentSystem().getPlanets()) {
            double dx = playerCenterX - planet.getX();
            double dy = playerCenterY - planet.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < planet.getRadius() + 30) {
                isNearAnyPlanet = true;
                currentLandedPlanet = planet;
                break;
            }
        }
        player.setCanLand(isNearAnyPlanet);
    }
    public Planet landOnPlanet() {
        if(player.attemptLanding()) {
            return currentLandedPlanet;
        }
        return null;
    }

    public Planet getCurrentLandedPlanet() {
        return currentLandedPlanet;
    }

    public int getSelectedTradeItemIndex() {
        return selectedTradeItemIndex;
    }
    
    public void setSelectedTradeItemIndex(int index) {
        this.selectedTradeItemIndex = index;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Planet> getPlanets() {
        return getCurrentSystem().getPlanets();
    }

    public StarSystem getCurrentSystem() {
        return universe.get(currentSystemIndex);
    }
}