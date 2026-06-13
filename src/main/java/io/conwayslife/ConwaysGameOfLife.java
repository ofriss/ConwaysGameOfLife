package io.conwayslife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ConwaysGameOfLife {
    private final JFrame frame = new JFrame("Conway's Game of Life");
    private final int cellRowCount = 60;
    private final Color liveColor = Color.WHITE;
    private final Color deadColor = Color.BLACK;

    // Cell Grids; 2 grids so processing of new grid is relative to the same original grid
    private final boolean[][] cellGridStatus = new boolean[cellRowCount][cellRowCount];
    private final JPanel[][] cellPanels = new JPanel[cellRowCount][cellRowCount]; // To panels to display

    public ConwaysGameOfLife() {
        int windowSize = 600;
        frame.setSize(windowSize, windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(cellRowCount, cellRowCount)); // create a grid cellRowCount X cellRowCount

        // Randomly generate a base grid to start off of
        // Set all cell panels to opaque, they are not by default
        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                Random random = new Random();
                cellGridStatus[row][col] = random.nextBoolean();
                JPanel cellPanel = new JPanel();
                cellPanel.setOpaque(true);
                cellPanel.setBackground(cellGridStatus[row][col] ? liveColor : deadColor);
                frame.add(cellPanel);
                cellPanels[row][col] = cellPanel;
            }
        }

        Timer procTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCellsStatus();
                drawGrid();
            }
        });
        procTimer.start();

        frame.setVisible(true);
    }

    // Flushes the cellGridStatus to the GUI
    private void drawGrid() {
        for (int row = 0; row < cellRowCount; row++)
            for (int col = 0; col < cellRowCount; col++)
                cellPanels[row][col].setBackground(cellGridStatus[row][col] ? liveColor : deadColor);
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
