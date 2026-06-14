package io.conwayslife;

import javax.swing.*;
import java.awt.*;

public class ConwaysGameOfLife  {
    private final JFrame frame = new JFrame("Conway's Game of Life");
    private final int windowSize = 800;
    private final GamePanel gamePanel = new GamePanel(50);

    public ConwaysGameOfLife() {
        // Setup frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Setup gamePanel
        gamePanel.setPreferredSize(new Dimension(windowSize, windowSize));
        frame.add(gamePanel);
        frame.pack();

        // Finalize
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
