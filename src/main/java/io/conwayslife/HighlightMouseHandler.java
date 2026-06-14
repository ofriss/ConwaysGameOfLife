package io.conwayslife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

// Its job is to trigger a repaint and update the mouse coordinates for use of parent
public class HighlightMouseHandler extends MouseMotionAdapter {
    private final Container parentContainer;
    private int mouseX;
    private int mouseY;

    public HighlightMouseHandler(JPanel parentContainer) {
        this.parentContainer = parentContainer;
        Point p = parentContainer.getMousePosition();
        if (p != null) {
            mouseX = p.x;
            mouseY = p.y;
        }
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateHighlight(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateHighlight(e);
    }

    private void updateHighlight(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        parentContainer.repaint();
    }
}
