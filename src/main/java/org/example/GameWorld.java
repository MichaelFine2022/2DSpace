package org.example;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

// The Model: Holds all game data and logic.
public class GameWorld {
    private Player player;
    private ArrayList<Planet> planets = new ArrayList<>();

    public GameWorld() throws IOException {
        //create player and planets
        player = new Player(50, 50);

        planets.add(new Planet(200, 200, 50, Color.decode("#42a5f5")));
        planets.add(new Planet(600, 450, 80, Color.decode("#ef5350")));
        planets.add(new Planet(900, 150, 65, Color.decode("#66bb6a")));
    }

    // updates game state
    public void update() {
        player.update();
        checkLandingProximity();
    }

    private void checkLandingProximity() {
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

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }
}