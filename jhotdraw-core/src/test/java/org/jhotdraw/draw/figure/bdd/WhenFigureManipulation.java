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
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.figure.RectangleFigure;

/**
 * JGiven When stage for BDD testing of figure manipulation actions.
 *
 * @author JHotDraw Team
 */
public class WhenFigureManipulation extends Stage<WhenFigureManipulation> {

    @ExpectedScenarioState
    protected RectangleFigure rectangle;

    @ExpectedScenarioState
    protected Point2D.Double startPoint;

    @ExpectedScenarioState
    protected Point2D.Double endPoint;

    @ProvidedScenarioState
    protected Rectangle2D.Double resultingBounds;

    @ProvidedScenarioState
    protected boolean containmentResult;

    @ProvidedScenarioState
    protected Point2D.Double testPoint;

    public WhenFigureManipulation the_user_sets_bounds_from_start_to_end_point() {
        rectangle.setBounds(startPoint, endPoint);
        resultingBounds = rectangle.getBounds();
        return self();
    }

    public WhenFigureManipulation the_user_moves_the_figure_by(double deltaX, double deltaY) {
        AffineTransform translation = AffineTransform.getTranslateInstance(deltaX, deltaY);
        rectangle.transform(translation);
        resultingBounds = rectangle.getBounds();
        return self();
    }

    public WhenFigureManipulation the_user_scales_the_figure_by(double scaleX, double scaleY) {
        AffineTransform scaling = AffineTransform.getScaleInstance(scaleX, scaleY);
        rectangle.transform(scaling);
        resultingBounds = rectangle.getBounds();
        return self();
    }

    public WhenFigureManipulation checking_if_point_$_$_is_contained(double x, double y) {
        testPoint = new Point2D.Double(x, y);
        containmentResult = rectangle.contains(testPoint);
        return self();
    }

    public WhenFigureManipulation the_user_resizes_the_rectangle_to(double width, double height) {
        Rectangle2D.Double currentBounds = rectangle.getBounds();
        Point2D.Double anchor = new Point2D.Double(currentBounds.x, currentBounds.y);
        Point2D.Double lead = new Point2D.Double(currentBounds.x + width, currentBounds.y + height);
        rectangle.setBounds(anchor, lead);
        resultingBounds = rectangle.getBounds();
        return self();
    }

    public WhenFigureManipulation the_user_creates_a_rectangle_by_dragging_from_start_to_end() {
        rectangle.setBounds(startPoint, endPoint);
        resultingBounds = rectangle.getBounds();
        return self();
    }
}
