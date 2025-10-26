package org.example;

import java.io.IOException;
public class BaseShip  {
    protected double x, y;
    protected int size = 50;
    public double centerX = x + size/2;
    public double centerY = y + size/2;
    
    protected double angle = 0.0;
    protected double rotationSpeed = 0.1;
    protected double thrust = 1;
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double drag = 0.0;
    protected boolean isLanded = false;
    protected boolean canLand = false;
    BaseShip() {
        x = 0;
        y = 0;
    }
    BaseShip(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        
    }
}
