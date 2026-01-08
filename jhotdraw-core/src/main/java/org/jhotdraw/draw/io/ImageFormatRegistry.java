package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageHolderFigure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

public final class ImageFormatRegistry {
    
    private static final List<ImageFormatProvider> providers = new ArrayList<>();
    
    static {
        registerProvider(new PngFormatProvider());
        registerProvider(new JpegFormatProvider());
        
        ServiceLoader<ImageFormatProvider> loader = ServiceLoader.load(ImageFormatProvider.class);
        for (ImageFormatProvider provider : loader) {
            registerProvider(provider);
        }
    }
    
    private ImageFormatRegistry() {
    }
    
    public static void registerProvider(ImageFormatProvider provider) {
        if (provider != null && !providers.contains(provider)) {
            providers.add(provider);
        }
    }
    
    public static void unregisterProvider(ImageFormatProvider provider) {
        providers.remove(provider);
    }
    
    public static List<ImageFormatProvider> getProviders() {
        return Collections.unmodifiableList(providers);
    }
    
    public static List<InputFormat> createInputFormats(ImageHolderFigure prototype) {
        List<InputFormat> formats = new ArrayList<>();
        for (ImageFormatProvider provider : providers) {
            formats.add(provider.createInputFormat(prototype));
        }
        return formats;
    }
    
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
