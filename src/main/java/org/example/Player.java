package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player{
    private double x, y;
    private final int size = 50;
    public double centerX = x + size/2;
    public double centerY = y + size/2;
    public String imageUrl = "/player.png";
    public Image unScaledImage = ImageIO.read(getClass().getResource(imageUrl));
    public Image resultingImage = unScaledImage.getScaledInstance(size, size, Image.SCALE_DEFAULT);
    private double angle = 0.0;
    private double rotationSpeed = 0.1;
    private double thrust = 1;
    private double velocityX = 0;
    private double velocityY = 0;
    private double drag = 0.0;
    private boolean isLanded = false;
    private boolean canLand = false;

    

    public Player(double x, double y) throws IOException{
        this.x = x;
        this.y = y;

        
    }
    

    public void draw(Graphics g) throws IOException{
        
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        g2d.rotate(angle, x + size / 2, y + size / 2);
        g2d.drawImage(resultingImage, (int)(x), (int)(y), null);
        g2d.setTransform(old);

        g2d.setColor(Color.RED);
        g2d.drawLine((int)centerX, (int)centerY, (int)(centerX + velocityX * 30), (int)(centerY + velocityY * 30));


    }
    public void update() {
        if(isLanded){
            return;
        }
        x += velocityX;
        y += velocityY;
        velocityX *= (1 - drag);
        velocityY *= (1 - drag);
        centerX = x + size/2;
        centerY = y + size/2;
    }
    
    public void land(){
        isLanded = true;
        velocityX = 0;
        velocityY = 0;
    }
    public void takeoff(){
        isLanded = false;
    }

    public boolean isLanded() {
        return isLanded;
    }
    public void setCanLand(boolean canLand) { this.canLand = canLand; }
    
    public boolean canLand() { return canLand; }

    public double getX() { return x; }

    public double getY() { return y; }

    public int getSize() { return size; }

    public void rotateLeft() {
        if (!isLanded) angle -= rotationSpeed;
    }
    
    public void rotateRight() {
        if (!isLanded) angle += rotationSpeed;
    }
    
    public void applyThrust() {
        if (!isLanded) {
            velocityX += Math.cos(angle - Math.PI / 2) * thrust;
            velocityY += Math.sin(angle - Math.PI / 2) * thrust;
        }
    }
    
    public void attemptLanding() {
        if (canLand && !isLanded) {
            land();
        }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y =y;
    }

    public void resetVelocity() {
        this.velocityX = 0;
        this.velocityY = 0;
    }
}
