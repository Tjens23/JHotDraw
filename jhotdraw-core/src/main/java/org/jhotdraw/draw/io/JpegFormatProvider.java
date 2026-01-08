package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;
import java.awt.image.BufferedImage;

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
