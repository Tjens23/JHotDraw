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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.figure.RectangleFigure;
import static org.assertj.core.api.Assertions.*;

/**
 * JGiven Given stage for BDD testing of figure creation and setup scenarios.
 *
 * @author JHotDraw Team
 */
public class GivenFigureCreation extends Stage<GivenFigureCreation> {

    @ProvidedScenarioState
    protected RectangleFigure rectangle;

    @ProvidedScenarioState
    protected Point2D.Double startPoint;

    @ProvidedScenarioState
    protected Point2D.Double endPoint;

    public GivenFigureCreation a_new_rectangle_figure() {
        rectangle = new RectangleFigure();
        return self();
    }

    public GivenFigureCreation a_rectangle_figure_with_dimensions(double x, double y, double width, double height) {
        rectangle = new RectangleFigure(x, y, width, height);
        return self();
    }

    public GivenFigureCreation an_existing_rectangle_at_position(double x, double y) {
        rectangle = new RectangleFigure(x, y, 50, 30); // Default size
        return self();
    }

    public GivenFigureCreation a_start_point_at(double x, double y) {
        startPoint = new Point2D.Double(x, y);
        return self();
    }

    public GivenFigureCreation an_end_point_at(double x, double y) {
        endPoint = new Point2D.Double(x, y);
        return self();
    }

    public GivenFigureCreation a_rectangle_with_zero_dimensions() {
        rectangle = new RectangleFigure(10, 10, 0, 0);
        return self();
    }
}
