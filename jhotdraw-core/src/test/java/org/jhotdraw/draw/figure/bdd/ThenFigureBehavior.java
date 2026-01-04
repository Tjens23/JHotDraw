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
package org.jhotdraw.draw.figure.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.figure.RectangleFigure;
import static org.assertj.core.api.Assertions.*;

public class ThenFigureBehavior extends Stage<ThenFigureBehavior> {

    @ScenarioState
    protected RectangleFigure rectangle;

    @ScenarioState
    protected Rectangle2D.Double resultingBounds;

    @ScenarioState
    protected boolean containmentResult;

    @ScenarioState(resolution = Resolution.NAME)
    protected Point2D.Double testPoint;

    public ThenFigureBehavior the_rectangle_should_have_position(double x, double y) {
        assertThat(resultingBounds.x).isEqualTo(x);
        assertThat(resultingBounds.y).isEqualTo(y);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_have_dimensions(double width, double height) {
        assertThat(resultingBounds.width).isEqualTo(width);
        assertThat(resultingBounds.height).isEqualTo(height);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_be_positioned_at(double x, double y) {
        Rectangle2D.Double bounds = rectangle.getBounds();
        assertThat(bounds.x).isEqualTo(x);
        assertThat(bounds.y).isEqualTo(y);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_have_size(double width, double height) {
        Rectangle2D.Double bounds = rectangle.getBounds();
        assertThat(bounds.width).isEqualTo(width);
        assertThat(bounds.height).isEqualTo(height);
        return self();
    }

    public ThenFigureBehavior the_point_should_be_contained() {
        assertThat(containmentResult).as("Point %s should be contained in rectangle", testPoint).isTrue();
        return self();
    }

    public ThenFigureBehavior the_point_should_not_be_contained() {
        assertThat(containmentResult).as("Point %s should not be contained in rectangle", testPoint).isFalse();
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_enforce_minimum_width(double minimumWidth) {
        assertThat(resultingBounds.width).isGreaterThanOrEqualTo(minimumWidth);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_enforce_minimum_height(double minimumHeight) {
        assertThat(resultingBounds.height).isGreaterThanOrEqualTo(minimumHeight);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_be_normalized() {
        // A normalized rectangle should have positive width and height
        assertThat(resultingBounds.width).isGreaterThanOrEqualTo(0);
        assertThat(resultingBounds.height).isGreaterThanOrEqualTo(0);
        return self();
    }

    public ThenFigureBehavior the_drawing_area_should_be_larger_than_bounds() {
        Rectangle2D.Double bounds = rectangle.getBounds();
        Rectangle2D.Double drawingArea = rectangle.getDrawingArea();

        assertThat(drawingArea.width).isGreaterThan(bounds.width);
        assertThat(drawingArea.height).isGreaterThan(bounds.height);
        return self();
    }

    public ThenFigureBehavior the_rectangle_should_maintain_aspect_ratio_after_uniform_scaling(double originalWidth, double originalHeight, double scaleFactor) {
        double expectedWidth = originalWidth * scaleFactor;
        double expectedHeight = originalHeight * scaleFactor;

        assertThat(resultingBounds.width).isCloseTo(expectedWidth, within(0.01));
        assertThat(resultingBounds.height).isCloseTo(expectedHeight, within(0.01));
        return self();
    }
}
