package org.example;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;


class GameController extends KeyAdapter implements ActionListener {
    private GameWorld world;
    private SpacePanel view;
    private Timer gameLoop;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public GameController(GameWorld world, SpacePanel view) {
        this.world = world;
        this.view = view;
        view.addKeyListener(this);
    }

    public void start() {
        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        
        if (e.getKeyCode() == KeyEvent.VK_L) {
            world.getPlayer().attemptLanding();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (world.getPlayer().isLanded()) {
                world.getPlayer().takeoff();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            world.jumpToNextSystem();
        }
    }

    private void handleInput() {
        Player player = world.getPlayer();
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            player.applyThrust();
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            player.rotateLeft();
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            player.rotateRight();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleInput();
        view.updateStarfield();
        world.update();
        view.repaint();
    }
}