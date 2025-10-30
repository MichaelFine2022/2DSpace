package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Player extends BaseShip{
    public String imageUrl;
    public Image unScaledImage; 
    public Image resultingImage;
    String filename = "/player.png";
    private int credits = 200;
    private int cargoCapacity = 50;
    private HashMap<Commodity, Integer> cargoHold = new HashMap<>();
    

    public Player(double x, double y) throws IOException{
        super(x,y);
        imageUrl = filename;
        unScaledImage = ImageIO.read(getClass().getResource(imageUrl));
        resultingImage = unScaledImage.getScaledInstance(size, size, Image.SCALE_DEFAULT);

        for(Commodity item : Commodity.values()) {
            cargoHold.put(item, 0);
        }
        cargoHold.put(Commodity.FOOD,10);
    }
    public int getCredits() {return credits;}

    public int getCurrentCargoLoad() {
        return cargoHold.values().stream().mapToInt(Integer::intValue).sum();
    }
    public int getCargoCapacity() {
        return cargoCapacity;
    }
    public Map<Commodity, Integer> getCargoHold() { return cargoHold; }

    public boolean spendCredits(int amount) {
        if(credits >= amount) {
            credits -= amount;
            return true;
        }
        return false;
    }
    public void addCredits(int amount) {
        credits += amount;
    }

    public boolean addCargo(Commodity item, int quantity) {
        int currentLoad = getCurrentCargoLoad();
        if(currentLoad + quantity <= cargoCapacity) {
            cargoHold.put(item, cargoHold.get(item) + quantity);
            return true;
        }
        return false;
    }
    public boolean removeCargo(Commodity item, int quantity) {
        if(cargoHold.get(item) >= quantity) {
            cargoHold.put(item, cargoHold.get(item)-quantity);
            return true;
        }
        return false;
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
        setCanLand(true);
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
    
    public boolean attemptLanding() {
        if (canLand && !isLanded) {
            land();
            return true;
        }
        else{
            return false;
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
