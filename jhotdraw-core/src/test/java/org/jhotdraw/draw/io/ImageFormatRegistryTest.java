/*
 * @(#)ImageFormatRegistryTest.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageFigure;
import org.junit.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for the modular ImageFormatRegistry using JUnit 4 and AssertJ.
 */
public class ImageFormatRegistryTest {

    @Test
    public void testPngFormatSupported() {
        assertThat(ImageFormatRegistry.isFormatSupported("png"))
            .as("PNG format should be supported")
            .isTrue();
        assertThat(ImageFormatRegistry.isFormatSupported("PNG"))
            .as("PNG format should be supported (uppercase)")
            .isTrue();
    }

    @Test
    public void testJpegFormatSupported() {
        assertThat(ImageFormatRegistry.isFormatSupported("jpg"))
            .as("JPG format should be supported")
            .isTrue();
        assertThat(ImageFormatRegistry.isFormatSupported("jpeg"))
            .as("JPEG format should be supported")
            .isTrue();
    }

    @Test
    public void testUnsupportedFormat() {
        assertThat(ImageFormatRegistry.isFormatSupported("xyz"))
            .as("XYZ format should not be supported")
            .isFalse();
    }

    @Test
    public void testCreateInputFormats() {
        ImageFigure prototype = new ImageFigure();
        List<InputFormat> formats = ImageFormatRegistry.createInputFormats(prototype);
        
        assertThat(formats)
            .as("Input formats should not be null")
            .isNotNull();
        assertThat(formats)
            .as("Should have at least 2 input formats (PNG, JPEG)")
            .hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void testCreateOutputFormats() {
        List<OutputFormat> formats = ImageFormatRegistry.createOutputFormats();
        
        assertThat(formats)
            .as("Output formats should not be null")
            .isNotNull();
        assertThat(formats)
            .as("Should have at least 2 output formats (PNG, JPEG)")
            .hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void testProviderCount() {
        List<ImageFormatProvider> providers = ImageFormatRegistry.getProviders();
        
        assertThat(providers)
            .as("Providers should not be null")
            .isNotNull();
        assertThat(providers)
            .as("Should have at least 2 providers")
            .hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    public void testPngProvider() {
        PngFormatProvider provider = new PngFormatProvider();
        
        assertThat(provider.getFormatName()).isEqualTo("PNG");
        assertThat(provider.getFileExtensions()).isEqualTo(new String[]{"png"});
        assertThat(provider.getMimeTypes()).isEqualTo(new String[]{"image/png"});
    }

    @Test
    public void testJpegProvider() {
        JpegFormatProvider provider = new JpegFormatProvider();
        
        assertThat(provider.getFormatName()).isEqualTo("JPEG");
        assertThat(provider.getFileExtensions()).isEqualTo(new String[]{"jpg", "jpeg"});
    }
}
