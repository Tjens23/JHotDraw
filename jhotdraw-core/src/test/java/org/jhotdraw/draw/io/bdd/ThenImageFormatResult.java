package org.jhotdraw.draw.io.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ThenImageFormatResult extends Stage<ThenImageFormatResult> {

    @ScenarioState
    protected boolean formatSupported;

    @ScenarioState
    protected List<InputFormat> inputFormats;

    @ScenarioState
    protected List<OutputFormat> outputFormats;

    public ThenImageFormatResult the_format_should_be_supported() {
        assertThat(formatSupported)
            .as("Format should be supported")
            .isTrue();
        return self();
    }

    public ThenImageFormatResult the_format_should_not_be_supported() {
        assertThat(formatSupported)
            .as("Format should not be supported")
            .isFalse();
        return self();
    }

    public ThenImageFormatResult input_formats_should_be_available() {
        assertThat(inputFormats)
            .as("Input formats should be available")
            .isNotNull()
            .isNotEmpty();
        return self();
    }

    public ThenImageFormatResult output_formats_should_be_available() {
        assertThat(outputFormats)
            .as("Output formats should be available")
            .isNotNull()
            .isNotEmpty();
        return self();
    }

    public ThenImageFormatResult at_least_$_input_formats_exist(int count) {
        assertThat(inputFormats)
            .as("Should have at least %d input formats", count)
            .hasSizeGreaterThanOrEqualTo(count);
        return self();
    }

    public ThenImageFormatResult at_least_$_output_formats_exist(int count) {
        assertThat(outputFormats)
            .as("Should have at least %d output formats", count)
            .hasSizeGreaterThanOrEqualTo(count);
        return self();
    }

    public ThenImageFormatResult the_user_can_work_on_the_image() {
        assertThat(formatSupported)
            .as("User should be able to work on supported format")
            .isTrue();
        assertThat(inputFormats)
            .as("Input formats should be available for editing")
            .isNotNull()
            .isNotEmpty();
        return self();
    }
}

