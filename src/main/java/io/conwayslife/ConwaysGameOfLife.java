package io.conwayslife;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ConwaysGameOfLife {
    private final Random random = new Random();
    private final JFrame frame = new JFrame("Conway's Game of Life");
    private final int windowSize = 800;
    private final int cellRowCount = 100;
    private final Color liveColor = Color.WHITE;
    private final Color deadColor = Color.BLACK;

    private final boolean[][] cellGridStatus = new boolean[cellRowCount][cellRowCount];

    private final JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cellWidth  = getWidth()  / cellRowCount;
            int cellHeight = getHeight() / cellRowCount;
            for (int row = 0; row < cellRowCount; row++) {
                for (int col = 0; col < cellRowCount; col++) {
                    g.setColor(cellGridStatus[row][col] ? liveColor : deadColor);
                    g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    };

    public ConwaysGameOfLife() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        gamePanel.setPreferredSize(new Dimension(windowSize, windowSize));
        frame.add(gamePanel);
        frame.pack();

        // Randomly generate a base grid to start off of
        // Set all cell panels to opaque, they are not by default
        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                cellGridStatus[row][col] = random.nextBoolean();
            }
        }

        new Timer(100, e -> {
            processCellsStatus();
            gamePanel.repaint();
        }).start();

        frame.setVisible(true);
    }

    // Counts the amount of live surrounding neighbors (8)
    private int[][] countLiveNeighbors() {
        int[][] countMatrix = new int[cellRowCount][cellRowCount];

        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                int count = 0;

                // Indexes and flags to improve readability
                int upRow = row - 1;
                int downRow = row + 1;
                int leftCol = col - 1;
                int rightCol = col + 1;

                boolean upRowExists = upRow >= 0;
                boolean downRowExists = downRow < cellRowCount;
                boolean leftColExists = leftCol >= 0;
                boolean rightColExists = rightCol < cellRowCount;

                // Up
                if (upRowExists) {
                    if (cellGridStatus[upRow][col]) count++;

                    // Up-left
                    if (leftColExists && cellGridStatus[upRow][leftCol]) count++;

                    // Up-right
                    if (rightColExists && cellGridStatus[upRow][rightCol]) count++;
                }

                // Down
                if (downRowExists) {
                    if (cellGridStatus[downRow][col]) count++;

                    // Down-left
                    if (leftColExists && cellGridStatus[downRow][leftCol]) count++;

                    // Down-right
                    if (rightColExists && cellGridStatus[downRow][rightCol]) count++;
                }

                // Left
                if (leftColExists && cellGridStatus[row][leftCol]) count++;

                // Right
                if (rightColExists && cellGridStatus[row][rightCol]) count++;

                countMatrix[row][col] = count;
            }
        }

        return countMatrix;
    }

    // Processes the cells life status by counting the live neighbors, updating the GUI, and swapping the matrices
    private void processCellsStatus() {
        int[][] countsOfLiveNeighbors = countLiveNeighbors();

        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                int count = countsOfLiveNeighbors[row][col];

                // If cell is dead
                if (!cellGridStatus[row][col]) {
                    // Reproduction, back to life
                    cellGridStatus[row][col] = count == 3;
                } else {
                    // Underpopulation (<2) or overpopulation (>3), dead
                    cellGridStatus[row][col] = count >= 2 && count <= 3;
                }
            }
        }
    }
}
