package org.example;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void setUpTestUI(){
        JFrame Space = new JFrame("Space Game");
        Space.setSize(400,400);
        Space.setVisible(true);

        JPanel spacePanel = new JPanel();
        spacePanel.setBackground(Color.BLACK);

        Space.add(spacePanel, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setUpTestUI();
            }
        });
    }
}