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
    private GameState currentState = GameState.FLYING;
    private Planet currentPlanet = null;
    private int selectedCommodityIndex = 0;
    private Commodity[] allCommodities = Commodity.values();

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
        if(currentState == GameState.LANDED) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                selectedCommodityIndex = (selectedCommodityIndex - 1 + allCommodities.length) % allCommodities.length;
                world.setSelectedTradeItemIndex(selectedCommodityIndex);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                selectedCommodityIndex = (selectedCommodityIndex + 1) % allCommodities.length;
                world.setSelectedTradeItemIndex(selectedCommodityIndex);
            }
            if (e.getKeyCode() == KeyEvent.VK_B && !world.isPlayerCargoFocused()) {
                handleBuy();
            }
            if (e.getKeyCode() == KeyEvent.VK_S && world.isPlayerCargoFocused()) {
                handleSell();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                world.setPlayerCargoFocused(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                world.setPlayerCargoFocused(true);
            }
        }
        else {
            pressedKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(currentState == GameState.FLYING) {
            pressedKeys.remove(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_L) {
                Planet landedPlanet = world.landOnPlanet();
                if(landedPlanet != null) {
                    currentState = GameState.LANDED;
                    currentPlanet = landedPlanet;
                    selectedCommodityIndex = 0;
                }
            }
            if (currentState == GameState.FLYING && e.getKeyCode() == KeyEvent.VK_J) {
                world.jumpToNextSystem();
            }
        }
        
        if (currentState == GameState.LANDED && e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (world.getPlayer().isLanded()) {
                world.getPlayer().takeoff();
                currentState = GameState.FLYING;
                currentPlanet = null;
            }
        }

        
    }
    private void handleBuy() {
        Player player = world.getPlayer();
        Commodity item = allCommodities[selectedCommodityIndex];
        int price = currentPlanet.getPrices().get(item);

        if(player.getCredits() >= price && player.getCurrentCargoLoad() < player.getCargoCapacity()) {
            player.spendCredits(price);
            player.addCargo(item, 1);
            currentPlanet.buyFromPlanet(item, 1);
        }
    }
    private void handleSell() {
        Player player = world.getPlayer();
        Commodity item = allCommodities[selectedCommodityIndex];
        int price = currentPlanet.getPrices().get(item);

        if(player.getCargoHold().get(item) > 0){
            player.addCredits(price);
            player.removeCargo(item, 1);
            currentPlanet.sellToPlanet(item, 1);
        }
    }
    private void handleInput() {
        Player player = world.getPlayer();
        if(currentState == GameState.FLYING){
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
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(currentState == GameState.FLYING) {
            handleInput();
            world.update();
        }
        view.updateStarfield();
        view.repaint();
    }
}