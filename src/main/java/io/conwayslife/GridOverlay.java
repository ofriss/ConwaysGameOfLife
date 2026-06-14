package io.conwayslife;

import java.awt.*;

public class GridOverlay implements Overlay<int[]> {
    @Override
    public void paint(Graphics g, int[] args) throws IllegalArgumentException {
        if (args.length != 4)
            throw new IllegalArgumentException("GridOverlay args length has to be 4");

        // Getting the most updated width and height, can't take in constructor because the panel has no size then.
        int width = args[0];
        int height = args[1];
        int cellWidth = args[2];
        int cellHeight = args[3];

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(169, 169, 169, 80));

        for (int x = 0; x <= width; x += cellWidth) {
            g2.drawLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += cellHeight) {
            g2.drawLine(0, y, width, y);
        }
    }
}
