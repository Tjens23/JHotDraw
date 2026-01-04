package org.jhotdraw.draw.io.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 * BDD tests for the user story:
 * "As a user, I want to be able to open various formats (PNG, JPEG)
 * so I can work on top of my previous work"
 */
public class ImageFormatBDDTest extends ScenarioTest<GivenImageFormat, WhenOpeningImage, ThenImageFormatResult> {

    @Test
    public void user_can_open_png_image_to_work_on_previous_work() {
        given().the_image_format_registry_is_initialized()
               .and().a_png_file_extension();

        when().the_user_opens_an_image_file();

        then().the_format_should_be_supported()
              .and().input_formats_should_be_available()
              .and().the_user_can_work_on_the_image();
    }

    @Test
    public void user_can_open_jpeg_image_to_work_on_previous_work() {
        given().the_image_format_registry_is_initialized()
               .and().a_jpeg_file_extension();

        when().the_user_opens_an_image_file();

        then().the_format_should_be_supported()
              .and().input_formats_should_be_available()
              .and().the_user_can_work_on_the_image();
    }

    @Test
    public void user_can_open_jpg_image_to_work_on_previous_work() {
        given().the_image_format_registry_is_initialized()
               .and().a_jpg_file_extension();

        when().the_user_opens_an_image_file();

        then().the_format_should_be_supported()
              .and().input_formats_should_be_available()
              .and().the_user_can_work_on_the_image();
    }

    @Test
    public void system_rejects_unsupported_image_format() {
        given().the_image_format_registry_is_initialized()
               .and().an_unsupported_file_extension();

        when().the_user_checks_if_format_is_supported();

        then().the_format_should_not_be_supported();
    }

    @Test
    public void png_format_is_recognized_regardless_of_case() {
        given().the_image_format_registry_is_initialized()
               .and().an_uppercase_file_extension("png");

        when().the_user_checks_if_format_is_supported();

        then().the_format_should_be_supported();
    }

    @Test
    public void jpeg_format_is_recognized_regardless_of_case() {
        given().the_image_format_registry_is_initialized()
               .and().an_uppercase_file_extension("jpeg");

        when().the_user_checks_if_format_is_supported();

        then().the_format_should_be_supported();
    }

    @Test
    public void system_provides_input_formats_for_opening_images() {
        given().the_image_format_registry_is_initialized();

        when().the_user_requests_input_formats();

        then().input_formats_should_be_available()
              .and().at_least_$_input_formats_exist(2);
    }

    @Test
    public void system_provides_output_formats_for_saving_images() {
        given().the_image_format_registry_is_initialized();

        when().the_user_requests_output_formats();

        then().output_formats_should_be_available()
              .and().at_least_$_output_formats_exist(2);
    }
}
