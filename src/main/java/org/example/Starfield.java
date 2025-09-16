package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Starfield {
    private final ArrayList<Star> stars;

    /**
     * Creates a field of stars.
     * @param numStars The number of stars to generate.
     * @param worldWidth The total width of the game world.
     * @param worldHeight The total height of the game world.
     */
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

    /**
     * Draws all the stars onto the graphics context.
     * @param g The graphics context to draw on.
     */
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        for (Star star : stars) {
            star.draw(g);
        }
    }
}