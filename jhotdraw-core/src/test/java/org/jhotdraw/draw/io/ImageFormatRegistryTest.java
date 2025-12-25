/*
 * @(#)ImageFormatRegistryTest.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageFigure;
import org.testng.annotations.Test;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for the modular ImageFormatRegistry.
 */
public class ImageFormatRegistryTest {

    @Test
    public void testPngFormatSupported() {
        assertTrue(ImageFormatRegistry.isFormatSupported("png"), 
            "PNG format should be supported");
        assertTrue(ImageFormatRegistry.isFormatSupported("PNG"),
            "PNG format should be supported (uppercase)");
    }

    @Test
    public void testJpegFormatSupported() {
        assertTrue(ImageFormatRegistry.isFormatSupported("jpg"),
            "JPG format should be supported");
        assertTrue(ImageFormatRegistry.isFormatSupported("jpeg"),
            "JPEG format should be supported");
    }

    @Test
    public void testUnsupportedFormat() {
        assertFalse(ImageFormatRegistry.isFormatSupported("xyz"),
            "XYZ format should not be supported");
    }

    @Test
    public void testCreateInputFormats() {
        ImageFigure prototype = new ImageFigure();
        List<InputFormat> formats = ImageFormatRegistry.createInputFormats(prototype);
        
        assertNotNull(formats, "Input formats should not be null");
        assertTrue(formats.size() >= 2,
            "Should have at least 2 input formats (PNG, JPEG)");
    }

    @Test
    public void testCreateOutputFormats() {
        List<OutputFormat> formats = ImageFormatRegistry.createOutputFormats();
        
        assertNotNull(formats, "Output formats should not be null");
        assertTrue(formats.size() >= 2,
            "Should have at least 2 output formats (PNG, JPEG)");
    }

    @Test
    public void testProviderCount() {
        List<ImageFormatProvider> providers = ImageFormatRegistry.getProviders();
        
        assertNotNull(providers, "Providers should not be null");
        assertTrue(providers.size() >= 2, "Should have at least 2 providers");
    }

    @Test
    public void testPngProvider() {
        PngFormatProvider provider = new PngFormatProvider();
        
        assertEquals(provider.getFormatName(), "PNG");
        assertEquals(provider.getFileExtensions(), new String[]{"png"});
        assertEquals(provider.getMimeTypes(), new String[]{"image/png"});
    }

    @Test
    public void testJpegProvider() {
        JpegFormatProvider provider = new JpegFormatProvider();
        
        assertEquals(provider.getFormatName(), "JPEG");
        assertEquals(provider.getFileExtensions(), new String[]{"jpg", "jpeg"});
    }
}
