package org.jhotdraw.samples.svg.figures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class SVGEllipseFigureBoundaryTest {
    @Test
    public void testConstructorWithZeroWidthHeight() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 0, 0);
        assertEquals(0, ellipse.getWidth(), 0.001);
        assertEquals(0, ellipse.getHeight(), 0.001);
        assertTrue(ellipse.isEmpty());
    }

    @Test
    public void testCopyConstructorWithZeroEllipse() {
        SVGEllipseFigure original = new SVGEllipseFigure(0, 0, 0, 0);
        SVGEllipseFigure copy = new SVGEllipseFigure(original);
        assertEquals(0, copy.getWidth(), 0.001);
        assertEquals(0, copy.getHeight(), 0.001);
        assertTrue(copy.isEmpty());
    }

    @Test
    public void testSetBoundsWithSamePoints() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure();
        ellipse.setBounds(new Point2D.Double(5, 5), new Point2D.Double(5, 5));
        assertEquals(5, ellipse.getX(), 0.001);
        assertEquals(5, ellipse.getY(), 0.001);
        assertEquals(0.1, ellipse.getWidth(), 0.001); // minimum enforced
        assertEquals(0.1, ellipse.getHeight(), 0.001);
    }

    @Test
    public void testGetBoundsWithZeroEllipse() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 0, 0);
        Rectangle2D.Double bounds = ellipse.getBounds();
        assertEquals(0, bounds.width, 0.001);
        assertEquals(0, bounds.height, 0.001);
    }

    @Test
    public void testContainsOnBoundary() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 10, 10);
        assertTrue(ellipse.contains(new Point2D.Double(0, 5)));
        assertTrue(ellipse.contains(new Point2D.Double(10, 5)));
        assertTrue(ellipse.contains(new Point2D.Double(5, 0)));
        assertTrue(ellipse.contains(new Point2D.Double(5, 10)));
        assertFalse(ellipse.contains(new Point2D.Double(-1, 5)));
        assertFalse(ellipse.contains(new Point2D.Double(11, 5)));
    }

    @Test
    public void testTransformWithZeroTransform() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 10, 10);
        AffineTransform tx = new AffineTransform(); // identity
        ellipse.transform(tx);
        assertEquals(0, ellipse.getX(), 0.001);
        assertEquals(0, ellipse.getY(), 0.001);
    }

    @Test
    public void testRestoreTransformToWithZeroEllipse() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, 0, 0);
        Object data = ellipse.getTransformRestoreData();
        SVGEllipseFigure restored = new SVGEllipseFigure();
        restored.restoreTransformTo(data);
        assertEquals(0, restored.getWidth(), 0.001);
        assertEquals(0, restored.getHeight(), 0.001);
        assertTrue(restored.isEmpty());
    }

    @Test
    public void testIsEmptyWithNegativeDimensions() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(0, 0, -1, -1);
        assertTrue(ellipse.isEmpty());
    }
}
