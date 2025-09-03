package org.example;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player{
    private int x, y;
    private final int size = 50; 
    public String imageUrl = "/player.png";
    public Image unScaledImage = ImageIO.read(getClass().getResource(imageUrl));
    public Image resultingImage = unScaledImage.getScaledInstance(size, size, Image.SCALE_DEFAULT);

    public Player(int x, int y) throws IOException{
        this.x = x-size/2;
        this.y = y-size/2;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) throws IOException{
        g.drawImage(resultingImage, x, y, null);
    }
}
