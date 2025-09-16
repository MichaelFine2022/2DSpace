package org.example;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Star {
    private float x,y;
    private static final Random random = new Random();
    public Star(Rectangle worldBounds) {
        this.x = random.nextInt(worldBounds.width);
        this.y = random.nextInt(worldBounds.height);
    }

    public void update(Rectangle cameraBounds) {
        
        if (x < cameraBounds.x) {
            x += cameraBounds.width;
        }
        else if (x > cameraBounds.x + cameraBounds.width) {
            x -= cameraBounds.width;
        }

        if (y < cameraBounds.y) {
            y += cameraBounds.height;
        }
        else if (y > cameraBounds.y + cameraBounds.height) {
            y -= cameraBounds.height;
        }
    }
    
    public void draw(Graphics g) {
        g.fillRect((int) x, (int) y, 1, 1);
    }
}
