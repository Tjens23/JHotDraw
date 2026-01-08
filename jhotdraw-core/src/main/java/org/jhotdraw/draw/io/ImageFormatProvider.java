package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;

public interface ImageFormatProvider {
    
    String getFormatName();
    
    String getDescription();
    
    String[] getFileExtensions();
    
    String[] getMimeTypes();
    
    InputFormat createInputFormat(ImageHolderFigure prototype);
    
    OutputFormat createOutputFormat();
}
