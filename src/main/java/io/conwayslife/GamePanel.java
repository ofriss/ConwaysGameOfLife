package io.conwayslife;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private final int cellRowCount;
    private final boolean[][] cellGrid;

    private final Color liveColor = Color.WHITE;
    private final Color deadColor = Color.BLACK;

    private final GridOverlay gridOverlay = new GridOverlay();
    private final HighlightOverlay highlightOverlay = new HighlightOverlay();

    private final HighlightMouseHandler highlightMouseHandler = new HighlightMouseHandler(this);
    private final DrawingMouseHandler drawingMouseHandler;

    public GamePanel(int cellRowCount) {
        this.cellRowCount = cellRowCount;
        this.cellGrid = new boolean[cellRowCount][cellRowCount];
        drawingMouseHandler = new DrawingMouseHandler(this, cellRowCount, cellGrid);

        // Randomly generate a base grid to start off of
        // Set all cell panels to opaque, they are not by default
        Random random = new Random();
        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                cellGrid[row][col] = random.nextBoolean();
            }
        }

        // HIGHLIGHTING
        // Add highlightMouseHandler mouse motion listener to the game panel
        this.addMouseMotionListener(highlightMouseHandler);

        // DRAWING
        this.addMouseListener(drawingMouseHandler);
        this.addMouseMotionListener(drawingMouseHandler);

        // PROCESSING TIMER
        new Timer(100, _ -> {
            processCells();
            repaint(); // Manually trigger repaint after updating cellGrid
        }).start();
    }

    // Painting the cells, highlights, and grid!
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int cellWidth  = getWidth()  / cellRowCount;
        int cellHeight = getHeight() / cellRowCount;

        // Paint cells
        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                g.setColor(cellGrid[row][col] ? liveColor : deadColor);
                g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
            }
        }

        highlightOverlay.paint(g, new int[] { cellWidth, cellHeight, highlightMouseHandler.getMouseX()
                , highlightMouseHandler.getMouseY() });
        gridOverlay.paint(g, new int[] { getWidth(), getHeight(), cellWidth, cellHeight });
    }

    // Processes the cells life status by counting the live neighbors
    private void processCells() {
        int[][] countsOfLiveNeighbors = countLiveNeighbors();

        for (int row = 0; row < cellRowCount; row++) {
            for (int col = 0; col < cellRowCount; col++) {
                int count = countsOfLiveNeighbors[row][col];

                // If cell is dead
                if (!cellGrid[row][col]) {
                    // Reproduction, back to life
                    cellGrid[row][col] = count == 3;
                } else {
                    // Underpopulation (<2) or overpopulation (>3), dead
                    cellGrid[row][col] = count >= 2 && count <= 3;
                }
            }
        }
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
                    if (cellGrid[upRow][col]) count++;

                    // Up-left
                    if (leftColExists && cellGrid[upRow][leftCol]) count++;

                    // Up-right
                    if (rightColExists && cellGrid[upRow][rightCol]) count++;
                }

                // Down
                if (downRowExists) {
                    if (cellGrid[downRow][col]) count++;

                    // Down-left
                    if (leftColExists && cellGrid[downRow][leftCol]) count++;

                    // Down-right
                    if (rightColExists && cellGrid[downRow][rightCol]) count++;
                }

                // Left
                if (leftColExists && cellGrid[row][leftCol]) count++;

                // Right
                if (rightColExists && cellGrid[row][rightCol]) count++;

                countMatrix[row][col] = count;
            }
        }

        return countMatrix;
    }

}
