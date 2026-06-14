package io.conwayslife;

import java.awt.*;

// Provide painting functionality for mouse highlighting
public class HighlightOverlay implements Overlay<int[]>{
    @Override
    public void paint(Graphics g, int[] args) throws IllegalArgumentException {
        if (args.length != 4) {
            throw new IllegalArgumentException("HighlightOverlay args length has to be 4");
        }

        int cellWidth = args[0];
        int cellHeight = args[1];
        int mouseX = args[2];
        int mouseY = args[3];

        int cellRow = mouseY / cellHeight;
        int cellCol = mouseX / cellWidth;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(169, 169, 169, 110));
        g2.fillRect(cellCol * cellWidth, cellRow * cellHeight, cellWidth, cellHeight);
    }
}
