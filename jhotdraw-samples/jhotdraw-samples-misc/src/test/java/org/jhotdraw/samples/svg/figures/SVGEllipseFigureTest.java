package org.jhotdraw.samples.svg.figures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class SVGEllipseFigureTest {
    @Test
    public void testConstructorAndGetters() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(10, 20, 30, 40);
        assertEquals(10, ellipse.getX(), 0.001);
        assertEquals(20, ellipse.getY(), 0.001);
        assertEquals(30, ellipse.getWidth(), 0.001);
        assertEquals(40, ellipse.getHeight(), 0.001);
    }

    @Test
    public void testCopyConstructor() {
        SVGEllipseFigure original = new SVGEllipseFigure(5, 6, 7, 8);
        SVGEllipseFigure copy = new SVGEllipseFigure(original);
        assertEquals(5, copy.getX(), 0.001);
        assertEquals(6, copy.getY(), 0.001);
        assertEquals(7, copy.getWidth(), 0.001);
        assertEquals(8, copy.getHeight(), 0.001);
    }

    @Test
    public void testSetBounds() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure();
        ellipse.setBounds(new Point2D.Double(1, 2), new Point2D.Double(4, 6));
        assertEquals(1, ellipse.getX(), 0.001);
        assertEquals(2, ellipse.getY(), 0.001);
        assertEquals(3, ellipse.getWidth(), 0.001);
        assertEquals(4, ellipse.getHeight(), 0.001);
    }

    @Test
    public void testGetBounds() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(1, 2, 3, 4);
        Rectangle2D.Double bounds = ellipse.getBounds();
        assertEquals(1, bounds.x, 0.001);
        assertEquals(2, bounds.y, 0.001);
        assertEquals(3, bounds.width, 0.001);
        assertEquals(4, bounds.height, 0.001);
    }

    @Test
    public void testContains() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 10, 10);
        assertTrue(ellipse.contains(new Point2D.Double(5, 5)));
        assertFalse(ellipse.contains(new Point2D.Double(20, 20)));
    }

    @Test
    public void testTransform() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 10, 10);
        AffineTransform tx = AffineTransform.getTranslateInstance(10, 20);
        ellipse.transform(tx);
        assertEquals(10, ellipse.getX(), 0.001);
        assertEquals(20, ellipse.getY(), 0.001);
    }

    @Test
    public void testRestoreTransformToAndGetTransformRestoreData() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(1, 2, 3, 4);
        Object data = ellipse.getTransformRestoreData();
        SVGEllipseFigure restored = new SVGEllipseFigure();
        restored.restoreTransformTo(data);
        assertEquals(1, restored.getX(), 0.001);
        assertEquals(2, restored.getY(), 0.001);
        assertEquals(3, restored.getWidth(), 0.001);
        assertEquals(4, restored.getHeight(), 0.001);
    }

    @Test
    public void testIsEmpty() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 0, 0);
        assertTrue(ellipse.isEmpty());
        ellipse = new SVGEllipseFigure(0, 0, 1, 1);
        assertFalse(ellipse.isEmpty());
    }
}
