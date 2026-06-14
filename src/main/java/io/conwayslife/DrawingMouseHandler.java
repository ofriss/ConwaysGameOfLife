package io.conwayslife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingMouseHandler extends MouseAdapter {
    private final Container parentContainer;
    private final int cellRowCount;
    private final boolean[][] cellGrid;
    private boolean enabled = true;

    public DrawingMouseHandler(Container parentContainer, int cellRowCount, boolean[][] cellGrid) {
        this.parentContainer = parentContainer;
        this.cellRowCount = cellRowCount;
        this.cellGrid = cellGrid;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!enabled) return;
        drawCell(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!enabled) return;
        drawCell(e);
    }

    private void drawCell(MouseEvent e) {
        // Check if the drag happened with the left mouse button
        if (!SwingUtilities.isLeftMouseButton(e)) return;

        int cellRowSize = parentContainer.getHeight() / cellRowCount;
        int cellColSize = parentContainer.getWidth() / cellRowCount;
        int cellRow = e.getY() / cellRowSize;
        int cellCol = e.getX() / cellColSize;

        if (cellRow >= 0 && cellRow < cellGrid.length && cellCol >= 0 && cellCol < cellGrid[0].length)
            cellGrid[cellRow][cellCol] = true;
    }
}
