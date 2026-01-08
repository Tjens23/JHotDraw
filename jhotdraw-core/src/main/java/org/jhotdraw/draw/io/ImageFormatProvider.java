package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;

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
