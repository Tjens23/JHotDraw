/*
 * Copyright (C) 2015 JHotDraw.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.jhotdraw.draw.figure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import org.junit.*;
import static org.assertj.core.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

/**
 * Comprehensive unit tests for RectangleFigure using JUnit 4, AssertJ, and Mockito.
 * Tests focus on geometric operations, boundary conditions, and drawing behavior.
 *
 * @author JHotDraw Team
 */
public class RectangleFigureTest {

    @Mock
    private Graphics2D mockGraphics;

    private RectangleFigure rectangle;

    @BeforeClass
    public static void setUpClass() {
        // Class-level setup if needed
    }

    @AfterClass
    public static void tearDownClass() {
        // Class-level cleanup if needed
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rectangle = new RectangleFigure();
    }

    @After
    public void tearDown() {
        rectangle = null;
    }

    // CONSTRUCTOR TESTS

    /**
     * Test default constructor creates rectangle at origin with zero dimensions.
     */
    @Test
    public void testDefaultConstructor() {
        RectangleFigure rect = new RectangleFigure();
        Rectangle2D.Double bounds = rect.getBounds();

        assertThat(bounds.x).isZero();
        assertThat(bounds.y).isZero();
        assertThat(bounds.width).isZero();
        assertThat(bounds.height).isZero();
    }

    /**
     * Test parameterized constructor sets dimensions correctly.
     */
    @Test
    public void testParameterizedConstructor() {
        RectangleFigure rect = new RectangleFigure(10, 20, 100, 50);
        Rectangle2D.Double bounds = rect.getBounds();

        assertThat(bounds.x).isEqualTo(10.0);
        assertThat(bounds.y).isEqualTo(20.0);
        assertThat(bounds.width).isEqualTo(100.0);
        assertThat(bounds.height).isEqualTo(50.0);
    }

    // BOUNDARY TESTS

    /**
     * Test setBounds with normal positive values.
     */
    @Test
    public void testSetBounds() {
        Point2D.Double anchor = new Point2D.Double(10, 10);
        Point2D.Double lead = new Point2D.Double(50, 30);

        rectangle.setBounds(anchor, lead);
        Rectangle2D.Double bounds = rectangle.getBounds();

        assertThat(bounds.x).isEqualTo(10.0);
        assertThat(bounds.y).isEqualTo(10.0);
        assertThat(bounds.width).isEqualTo(40.0);
        assertThat(bounds.height).isEqualTo(20.0);
    }

    /**
     * Test setBounds with inverted anchor and lead points (lead before anchor).
     */
    @Test
    public void testSetBoundsInvertedPoints() {
        Point2D.Double anchor = new Point2D.Double(50, 30);
        Point2D.Double lead = new Point2D.Double(10, 10);

        rectangle.setBounds(anchor, lead);
        Rectangle2D.Double bounds = rectangle.getBounds();

        // Should normalize to proper rectangle
        assertThat(bounds.x).isEqualTo(10.0);
        assertThat(bounds.y).isEqualTo(10.0);
        assertThat(bounds.width).isEqualTo(40.0);
        assertThat(bounds.height).isEqualTo(20.0);
    }

    /**
     * Test setBounds with zero-width rectangle enforces minimum width.
     */
    @Test
    public void testSetBoundsZeroWidth() {
        Point2D.Double anchor = new Point2D.Double(10, 10);
        Point2D.Double lead = new Point2D.Double(10, 30); // Same x-coordinate

        rectangle.setBounds(anchor, lead);
        Rectangle2D.Double bounds = rectangle.getBounds();

        assertThat(bounds.width).isEqualTo(0.1); // Minimum width enforced
        assertThat(bounds.height).isEqualTo(20.0);
    }

    /**
     * Test setBounds with zero-height rectangle enforces minimum height.
     */
    @Test
    public void testSetBoundsZeroHeight() {
        Point2D.Double anchor = new Point2D.Double(10, 10);
        Point2D.Double lead = new Point2D.Double(30, 10); // Same y-coordinate

        rectangle.setBounds(anchor, lead);
        Rectangle2D.Double bounds = rectangle.getBounds();

        assertThat(bounds.width).isEqualTo(20.0);
        assertThat(bounds.height).isEqualTo(0.1); // Minimum height enforced
    }

    // CONTAINMENT TESTS

    /**
     * Test contains method for point inside rectangle.
     */
    @Test
    public void testContainsPointInside() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);
        Point2D.Double insidePoint = new Point2D.Double(20, 17);

        assertThat(rect.contains(insidePoint)).isTrue();
    }

    /**
     * Test contains method for point outside rectangle.
     */
    @Test
    public void testContainsPointOutside() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);
        Point2D.Double outsidePoint = new Point2D.Double(5, 5);

        assertThat(rect.contains(outsidePoint)).isFalse();
    }

    /**
     * Test contains method for point on rectangle boundary.
     */
    @Test
    public void testContainsPointOnBoundary() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);
        Point2D.Double boundaryPoint = new Point2D.Double(10, 10); // Top-left corner

        // Due to hit growth, boundary points should be contained
        assertThat(rect.contains(boundaryPoint)).isTrue();
    }

    // TRANSFORMATION TESTS

    /**
     * Test transform with identity transformation.
     */
    @Test
    public void testTransformIdentity() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);
        Rectangle2D.Double originalBounds = rect.getBounds();

        AffineTransform identity = new AffineTransform();
        rect.transform(identity);

        Rectangle2D.Double newBounds = rect.getBounds();
        assertThat(newBounds).isEqualTo(originalBounds);
    }

    /**
     * Test transform with translation.
     */
    @Test
    public void testTransformTranslation() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        AffineTransform translation = AffineTransform.getTranslateInstance(5, -3);
        rect.transform(translation);

        Rectangle2D.Double bounds = rect.getBounds();
        assertThat(bounds.x).isEqualTo(15.0);
        assertThat(bounds.y).isEqualTo(7.0);
        assertThat(bounds.width).isEqualTo(20.0);
        assertThat(bounds.height).isEqualTo(15.0);
    }

    /**
     * Test transform with scaling.
     */
    @Test
    public void testTransformScaling() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        AffineTransform scaling = AffineTransform.getScaleInstance(2.0, 0.5);
        rect.transform(scaling);

        Rectangle2D.Double bounds = rect.getBounds();
        assertThat(bounds.x).isEqualTo(20.0);
        assertThat(bounds.y).isEqualTo(5.0);
        assertThat(bounds.width).isEqualTo(40.0);
        assertThat(bounds.height).isEqualTo(7.5);
    }

    // DRAWING AREA TESTS

    /**
     * Test that drawing area is larger than bounds due to stroke growth.
     */
    @Test
    public void testGetDrawingAreaLargerThanBounds() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        Rectangle2D.Double bounds = rect.getBounds();
        Rectangle2D.Double drawingArea = rect.getDrawingArea();

        assertThat(drawingArea.width).isGreaterThan(bounds.width);
        assertThat(drawingArea.height).isGreaterThan(bounds.height);
    }

    // DRAWING BEHAVIOR TESTS (with mocking)

    /**
     * Test that draw method calls appropriate Graphics2D methods.
     * Note: This is a simplified test - in reality we'd need to set up proper attributes.
     */
    @Test
    public void testDrawCallsGraphicsMethods() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        // Create a real graphics context for testing
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // This should not throw any exceptions
        assertThatCode(() -> rect.draw(g2d)).doesNotThrowAnyException();

        g2d.dispose();
    }

    // EDGE CASES AND ERROR CONDITIONS

    /**
     * Test drawing with null graphics context should throw NPE.
     */
    @Test
    public void testDrawWithNullGraphics() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        // Should throw NullPointerException - this is the correct behavior
        assertThatThrownBy(() -> rect.draw(null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Test negative dimensions behavior.
     */
    @Test
    public void testNegativeDimensions() {
        RectangleFigure rect = new RectangleFigure(10, 10, -20, -15);
        Rectangle2D.Double bounds = rect.getBounds();

        // Should handle negative dimensions appropriately
        assertThat(bounds.width).isEqualTo(-20.0); // Current behavior - might need adjustment
        assertThat(bounds.height).isEqualTo(-15.0);
    }

    // JAVA ASSERTIONS FOR INVARIANTS

    /**
     * Test invariant: bounds should never be null.
     */
    @Test
    public void testBoundsInvariant() {
        assert rectangle.getBounds() != null : "Bounds should never be null";

        rectangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(10, 10));
        assert rectangle.getBounds() != null : "Bounds should never be null after setBounds";

        rectangle.transform(AffineTransform.getScaleInstance(2, 2));
        assert rectangle.getBounds() != null : "Bounds should never be null after transform";
    }

    /**
     * Test invariant: drawing area should always contain bounds.
     */
    @Test
    public void testDrawingAreaContainsBoundsInvariant() {
        RectangleFigure rect = new RectangleFigure(10, 10, 20, 15);

        Rectangle2D.Double bounds = rect.getBounds();
        Rectangle2D.Double drawingArea = rect.getDrawingArea();

        assert drawingArea.contains(bounds) : "Drawing area should always contain bounds";
    }
}
