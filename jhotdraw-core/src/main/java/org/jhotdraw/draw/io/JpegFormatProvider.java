/*
 * @(#)JpegFormatProvider.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;
import java.awt.image.BufferedImage;

/**
 * Provider for JPEG (Joint Photographic Experts Group) image format.
 * 
 * <p>JPEG uses lossy compression, making it ideal for photographs
 * and images with smooth color gradients. Supports both .jpg and .jpeg extensions.</p>
 * 
 * @author JHotDraw
 * @version $Id$
 */
public class JpegFormatProvider implements ImageFormatProvider {
    
    @Override
    public String getFormatName() {
        return "JPEG";
    }
    
    @Override
    public String getDescription() {
        return "Joint Photographic Experts Group (JPEG)";
    }
    
    @Override
    public String[] getFileExtensions() {
        return new String[]{"jpg", "jpeg"};
    }
    
    @Override
    public String[] getMimeTypes() {
        return new String[]{"image/jpeg", "image/jpg"};
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
            "JPG",
            getDescription(),
            "jpg",
            BufferedImage.TYPE_INT_RGB
        );
    }
}
