package org.jhotdraw.draw.io.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.figure.ImageFigure;

public class GivenImageFormat extends Stage<GivenImageFormat> {

    @ScenarioState
    protected ImageFigure prototype;

    @ScenarioState
    protected String fileExtension;

    public GivenImageFormat the_image_format_registry_is_initialized() {
        prototype = new ImageFigure();
        return self();
    }

    public GivenImageFormat a_png_file_extension() {
        fileExtension = "png";
        return self();
    }

    public GivenImageFormat a_jpeg_file_extension() {
        fileExtension = "jpeg";
        return self();
    }

    public GivenImageFormat a_jpg_file_extension() {
        fileExtension = "jpg";
        return self();
    }

    public GivenImageFormat an_unsupported_file_extension() {
        fileExtension = "xyz";
        return self();
    }

    public GivenImageFormat an_uppercase_file_extension(String ext) {
        fileExtension = ext.toUpperCase();
        return self();
    }

    public GivenImageFormat a_file_with_extension(String ext) {
        fileExtension = ext;
        return self();
    }
}
