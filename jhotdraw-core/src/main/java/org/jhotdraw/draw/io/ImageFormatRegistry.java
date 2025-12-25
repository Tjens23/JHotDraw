/*
 * @(#)ImageFormatRegistry.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

/**
 * A registry for image format handlers that provides a modular way to 
 * add support for various image formats (PNG, JPEG, GIF, etc.).
 * 
 * <p>This class uses the Service Provider Interface (SPI) pattern to allow
 * dynamic registration of image format handlers.</p>
 * 
 * <p><b>Usage:</b></p>
 * <pre>
 * // Get all registered input formats for a prototype figure
 * List&lt;InputFormat&gt; formats = ImageFormatRegistry.getInputFormats(myImageFigure);
 * drawing.getInputFormats().addAll(formats);
 * </pre>
 * 
 * @author JHotDraw
 * @version $Id$
 */
public final class ImageFormatRegistry {
    
    private static final List<ImageFormatProvider> providers = new ArrayList<>();
    
    static {
        // Register default providers
        registerProvider(new PngFormatProvider());
        registerProvider(new JpegFormatProvider());
        
        // Load additional providers via SPI
        ServiceLoader<ImageFormatProvider> loader = ServiceLoader.load(ImageFormatProvider.class);
        for (ImageFormatProvider provider : loader) {
            registerProvider(provider);
        }
    }
    
    private ImageFormatRegistry() {
        // Prevent instantiation
    }
    
    /**
     * Registers a new image format provider.
     * 
     * @param provider the provider to register
     */
    public static void registerProvider(ImageFormatProvider provider) {
        if (provider != null && !providers.contains(provider)) {
            providers.add(provider);
        }
    }
    
    /**
     * Unregisters an image format provider.
     * 
     * @param provider the provider to unregister
     */
    public static void unregisterProvider(ImageFormatProvider provider) {
        providers.remove(provider);
    }
    
    /**
     * Returns all registered providers.
     * 
     * @return an unmodifiable list of providers
     */
    public static List<ImageFormatProvider> getProviders() {
        return Collections.unmodifiableList(providers);
    }
    
    /**
     * Creates input formats for all registered image format providers.
     * 
     * @param prototype the prototype figure to use for creating image holders
     * @return a list of input formats
     */
    public static List<InputFormat> createInputFormats(ImageHolderFigure prototype) {
        List<InputFormat> formats = new ArrayList<>();
        for (ImageFormatProvider provider : providers) {
            formats.add(provider.createInputFormat(prototype));
        }
        return formats;
    }
    
    /**
     * Creates output formats for all registered image format providers.
     * 
     * @return a list of output formats
     */
    public static List<OutputFormat> createOutputFormats() {
        List<OutputFormat> formats = new ArrayList<>();
        for (ImageFormatProvider provider : providers) {
            OutputFormat format = provider.createOutputFormat();
            if (format != null) {
                formats.add(format);
            }
        }
        return formats;
    }
    
    /**
     * Checks if a specific format is supported.
     * 
     * @param extension the file extension (e.g., "png", "jpg")
     * @return true if the format is supported
     */
    public static boolean isFormatSupported(String extension) {
        String ext = extension.toLowerCase();
        for (ImageFormatProvider provider : providers) {
            for (String supported : provider.getFileExtensions()) {
                if (supported.equalsIgnoreCase(ext)) {
                    return true;
                }
            }
        }
        return false;
    }
}
