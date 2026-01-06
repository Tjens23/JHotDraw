/*
 * @(#)PngFormatProvider.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;
import java.awt.image.BufferedImage;

/**
 * Provider for PNG (Portable Network Graphics) image format.
 * 
 * <p>PNG supports lossless compression and transparency (alpha channel),
 * making it ideal for graphics with sharp edges or text.</p>
 */
public class PngFormatProvider implements ImageFormatProvider {
    
    @Override
    public String getFormatName() {
        return "PNG";
    }
    
    @Override
    public String getDescription() {
        return "Portable Network Graphics (PNG)";
    }
    
    @Override
    public String[] getFileExtensions() {
        return new String[]{"png"};
    }
    
    @Override
    public String[] getMimeTypes() {
        return new String[]{"image/png"};
    }
    
    @Override
    public InputFormat createInputFormat(ImageHolderFigure prototype) {
        return new ImageInputFormat(
            prototype,
            getFormatName(),
            getDescription(),
            getFileExtensions(),
            getMimeTypes()
        );
    }
    
    @Override
    public OutputFormat createOutputFormat() {
        return new ImageOutputFormat(
            getFormatName(),
            getDescription(),
            "png",
            BufferedImage.TYPE_INT_ARGB
        );
    }
}
