package io.conwayslife;

import java.awt.*;

@FunctionalInterface
public interface Overlay<T> {
    void paint(Graphics g, T args) throws IllegalArgumentException;

    default void paint(Graphics g) {
        paint(g, null);
    }
}
