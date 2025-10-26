package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Starfield {
    private final ArrayList<Star> stars;


    public Starfield(int numStars, int worldWidth, int worldHeight) {
        stars = new ArrayList<>();
        Rectangle bounds = new Rectangle(0, 0, worldWidth, worldHeight);
        for (int i = 0; i < numStars; i++) {
            stars.add(new Star(bounds));
        }
    }
    public void update(Rectangle bounds){
        for(Star star : stars){
            star.update(bounds);
        }
    }
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        for (Star star : stars) {
            star.draw(g);
        }
    }
}