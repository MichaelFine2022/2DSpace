package org.example;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AIShip extends BaseShip{
    public AIShip(double x, double y) throws IOException{
        super(x, y);
        BufferedImage unscaled = ImageIO.read(getClass().getResource("/player.png"));
        BufferedImage scaled = (BufferedImage) unscaled.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        this.velocityX = 0.5;
        this.velocityY = 0.5;
        this.thrust = 0;
        this.drag = 0;
    }
}
