package org.example;

import java.util.ArrayList;

public class StarSystem {
    private String name;
    private ArrayList<Planet> planets = new ArrayList<>();

    public StarSystem(String name) {
        this.name = name;
    }

    public void addPlanet(Planet planet) {
        this.planets.add(planet);
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public String getName() {
        return name;
    }
}