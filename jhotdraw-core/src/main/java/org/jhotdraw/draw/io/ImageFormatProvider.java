/*
 * @(#)ImageFormatProvider.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;

/**
 * A provider interface for image format support.
 * 
 * <p>Implementations of this interface define how to create input and output
 * formats for specific image types (PNG, JPEG, GIF, etc.).</p>
 * 
 * <p>To add support for a new image format:</p>
 * <ol>
 *   <li>Implement this interface</li>
 *   <li>Register with {@link ImageFormatRegistry#registerProvider(ImageFormatProvider)}</li>
 *   <li>Or add to META-INF/services/org.jhotdraw.draw.io.ImageFormatProvider for SPI</li>
 * </ol>
 */
public interface ImageFormatProvider {
    
    /**
     * Returns the format name (e.g., "PNG", "JPEG").
     * 
     * @return the format name
     */
    String getFormatName();
    
    /**
     * Returns a human-readable description of the format.
     * 
     * @return the format description
     */
    String getDescription();
    
    /**
     * Returns the file extensions supported by this format.
     * 
     * @return array of file extensions (without dots)
     */
    String[] getFileExtensions();
    
    /**
     * Returns the MIME types supported by this format.
     * 
     * @return array of MIME types
     */
    String[] getMimeTypes();
    
    /**
     * Creates an input format for reading images of this type.
     * 
     * @param prototype the prototype figure for holding the image
     * @return an InputFormat instance
     */
    InputFormat createInputFormat(ImageHolderFigure prototype);
    
    /**
     * Creates an output format for writing images of this type.
     * 
     * @return an OutputFormat instance, or null if output is not supported
     */
    OutputFormat createOutputFormat();
}
