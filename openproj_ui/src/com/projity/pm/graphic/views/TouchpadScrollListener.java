package com.projity.pm.graphic.views;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class TouchpadScrollListener implements MouseWheelListener {
    private JScrollPane pane;

    public TouchpadScrollListener(JScrollPane pane) {
        this.pane = pane;
    }

    public static void applyTo(JScrollPane pane) {
        if (pane == null) return;
        if (Boolean.TRUE.equals(pane.getClientProperty("TouchpadScrollApplied"))) {
            return; // prevent duplicate listeners
        }
        pane.putClientProperty("TouchpadScrollApplied", Boolean.TRUE);
        
        // We do NOT disable pane.setWheelScrollingEnabled(false) to avoid breaking 
        // internal Synchronizers, but we add our listener to enhance it.
        pane.addMouseWheelListener(new TouchpadScrollListener(pane));
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isConsumed()) {
            return;
        }

        JScrollBar bar = e.isShiftDown() ? pane.getHorizontalScrollBar() : pane.getVerticalScrollBar();
        if (bar == null || !bar.isVisible()) {
            return;
        }

        double rotation = e.getWheelRotation();
        try {
            java.lang.reflect.Method m = e.getClass().getMethod("getPreciseWheelRotation");
            rotation = ((Double) m.invoke(e)).doubleValue();
        } catch (Exception ex) {
            // ignore
        }

        if (rotation == 0.0) {
            return;
        }

        int direction = rotation < 0 ? -1 : 1;
        double absRotation = Math.abs(rotation);

        int increment = bar.getUnitIncrement(direction);
        if (increment <= 0) {
            increment = 16;
        }

        int scrollAmount = e.getScrollAmount();
        if (scrollAmount <= 0) {
            scrollAmount = 3;
        }

        int delta = (int) Math.round(increment * absRotation * scrollAmount * direction);
        if (delta == 0) {
            delta = direction;
        }

        int newValue = bar.getValue() + delta;
        int min = bar.getMinimum();
        int max = bar.getMaximum() - bar.getModel().getExtent();
        
        newValue = Math.max(min, Math.min(max, newValue));
        
        System.out.println("TouchpadScroll: shift=" + e.isShiftDown() + 
                           " orientation=" + (bar.getOrientation() == 0 ? "HORIZ" : "VERT") + 
                           " rot=" + rotation + " delta=" + delta + 
                           " barVal=" + bar.getValue() + " newVal=" + newValue);

        if (bar.getValue() != newValue) {
            bar.setValue(newValue);
            e.consume();
        }
    }
}
