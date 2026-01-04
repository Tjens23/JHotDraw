package org.jhotdraw.draw.io.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.figure.ImageFigure;
import org.jhotdraw.draw.io.ImageFormatRegistry;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import java.util.List;

public class WhenOpeningImage extends Stage<WhenOpeningImage> {

    @ScenarioState
    protected ImageFigure prototype;

    @ScenarioState
    protected String fileExtension;

    @ScenarioState
    protected boolean formatSupported;

    @ScenarioState
    protected List<InputFormat> inputFormats;

    @ScenarioState
    protected List<OutputFormat> outputFormats;

    public WhenOpeningImage the_user_checks_if_format_is_supported() {
        formatSupported = ImageFormatRegistry.isFormatSupported(fileExtension);
        return self();
    }

    public WhenOpeningImage the_user_requests_input_formats() {
        inputFormats = ImageFormatRegistry.createInputFormats(prototype);
        return self();
    }

    public WhenOpeningImage the_user_requests_output_formats() {
        outputFormats = ImageFormatRegistry.createOutputFormats();
        return self();
    }

    public WhenOpeningImage the_user_opens_an_image_file() {
        formatSupported = ImageFormatRegistry.isFormatSupported(fileExtension);
        if (formatSupported) {
            inputFormats = ImageFormatRegistry.createInputFormats(prototype);
        }
        return self();
    }
}

