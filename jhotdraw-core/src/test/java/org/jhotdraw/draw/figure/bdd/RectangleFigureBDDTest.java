package org.jhotdraw.draw.figure.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class RectangleFigureBDDTest extends ScenarioTest<GivenFigureCreation, WhenFigureManipulation, ThenFigureBehavior> {

    @Test
    public void user_can_create_rectangle_by_dragging() {
        given().a_new_rectangle_figure()
                .and().a_start_point_at(10, 10)
                .and().an_end_point_at(50, 40);

        when().the_user_creates_a_rectangle_by_dragging_from_start_to_end();

        then().the_rectangle_should_have_position(10, 10)
                .and().the_rectangle_should_have_dimensions(40, 30);
    }

    @Test
    public void user_can_resize_existing_rectangle() {
        given().a_rectangle_figure_with_dimensions(10, 10, 50, 30);

        when().the_user_resizes_the_rectangle_to(100, 60);

        then().the_rectangle_should_have_dimensions(100, 60)
                .and().the_rectangle_should_be_positioned_at(10, 10);
    }

    @Test
    public void user_can_move_rectangle_to_new_position() {
        given().a_rectangle_figure_with_dimensions(10, 10, 50, 30);

        when().the_user_moves_the_figure_by(20, 15);

        then().the_rectangle_should_have_position(30, 25)
                .and().the_rectangle_should_have_dimensions(50, 30);
    }

    @Test
    public void user_can_scale_rectangle_proportionally() {
        given().a_rectangle_figure_with_dimensions(10, 10, 40, 20);

        when().the_user_scales_the_figure_by(2.0, 2.0);

        then().the_rectangle_should_maintain_aspect_ratio_after_uniform_scaling(40, 20, 2.0);
    }

    @Test
    public void rectangle_enforces_minimum_dimensions_when_dragging() {
        given().a_new_rectangle_figure()
                .and().a_start_point_at(10, 10)
                .and().an_end_point_at(10, 10); // Same point - zero dimensions

        when().the_user_creates_a_rectangle_by_dragging_from_start_to_end();

        then().the_rectangle_should_enforce_minimum_width(0.1)
                .and().the_rectangle_should_enforce_minimum_height(0.1);
    }

    @Test
    public void rectangle_normalizes_bounds_when_dragging_backwards() {
        given().a_new_rectangle_figure()
                .and().a_start_point_at(50, 40)  // Start from bottom-right
                .and().an_end_point_at(10, 10);   // End at top-left

        when().the_user_creates_a_rectangle_by_dragging_from_start_to_end();

        then().the_rectangle_should_have_position(10, 10)  // Should normalize to top-left
                .and().the_rectangle_should_have_dimensions(40, 30)
                .and().the_rectangle_should_be_normalized();
    }

    @Test
    public void point_inside_rectangle_is_detected_correctly() {
        given().a_rectangle_figure_with_dimensions(10, 10, 40, 30);

        when().checking_if_point_$_$_is_contained(25, 20); // Point inside

        then().the_point_should_be_contained();
    }

    @Test
    public void point_outside_rectangle_is_detected_correctly() {
        given().a_rectangle_figure_with_dimensions(10, 10, 40, 30);

        when().checking_if_point_$_$_is_contained(5, 5); // Point outside

        then().the_point_should_not_be_contained();
    }

    @Test
    public void rectangle_drawing_area_accommodates_stroke_width() {
        given().a_rectangle_figure_with_dimensions(10, 10, 40, 30);

        when().the_user_moves_the_figure_by(0, 0); // Trigger bounds calculation

        then().the_drawing_area_should_be_larger_than_bounds();
    }

    @Test
    public void user_can_create_thin_horizontal_line() {
        given().a_new_rectangle_figure()
                .and().a_start_point_at(10, 20)
                .and().an_end_point_at(50, 20); // Same y-coordinate

        when().the_user_creates_a_rectangle_by_dragging_from_start_to_end();

        then().the_rectangle_should_have_position(10, 20)
                .and().the_rectangle_should_have_dimensions(40, 0.1); // Minimum height enforced
    }

    @Test
    public void user_can_create_thin_vertical_line() {
        given().a_new_rectangle_figure()
                .and().a_start_point_at(15, 10)
                .and().an_end_point_at(15, 40); // Same x-coordinate

        when().the_user_creates_a_rectangle_by_dragging_from_start_to_end();

        then().the_rectangle_should_have_position(15, 10)
                .and().the_rectangle_should_have_dimensions(0.1, 30); // Minimum width enforced
    }
}
