/*
 * @(#)ImageFormatRegistryTest.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import org.jhotdraw.draw.figure.ImageFigure;
import org.jhotdraw.draw.figure.ImageHolderFigure;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ImageFormatRegistry - supports the user story:
 * "As a user, I want to be able to open various formats (PNG, JPEG)
 * so I can work on top of my previous work"
 *
 * Tests use JUnit 4, Mockito for mocking, AssertJ for assertions,
 * and Java assertions for invariants.
 */
public class ImageFormatRegistryTest {

    @Mock
    private ImageHolderFigure mockPrototype;

    @Mock
    private ImageFormatProvider mockProvider;

    private AutoCloseable mockCloseable;

    @BeforeClass
    public static void setUpClass() {
        // Enable Java assertions for invariant testing
        ImageFormatRegistryTest.class.getClassLoader().setDefaultAssertionStatus(true);
    }

    @Before
    public void setUp() {
        mockCloseable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        mockCloseable.close();
    }

    // ==================== BEST CASE SCENARIO TESTS ====================

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
        assertThat(ImageFormatRegistry.isFormatSupported("JPEG"))
            .as("JPEG format should be supported (uppercase)")
            .isTrue();
    }

    @Test
    public void testCreateInputFormatsWithRealPrototype() {
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

    // ==================== BOUNDARY CASE TESTS ====================

    @Test
    public void testUnsupportedFormat() {
        assertThat(ImageFormatRegistry.isFormatSupported("xyz"))
            .as("XYZ format should not be supported")
            .isFalse();
    }

    @Test
    public void testEmptyExtension() {
        assertThat(ImageFormatRegistry.isFormatSupported(""))
            .as("Empty extension should not be supported")
            .isFalse();
    }

    @Test
    public void testCaseInsensitiveFormatCheck() {
        assertThat(ImageFormatRegistry.isFormatSupported("PnG"))
            .as("Format check should be case-insensitive")
            .isTrue();
        assertThat(ImageFormatRegistry.isFormatSupported("JpEg"))
            .as("Format check should be case-insensitive")
            .isTrue();
    }

    @Test
    public void testProviderCount() {
        List<ImageFormatProvider> providers = ImageFormatRegistry.getProviders();
        
        assertThat(providers)
            .as("Providers should not be null")
            .isNotNull();
        assertThat(providers)
            .as("Should have at least 2 providers (PNG, JPEG)")
            .hasSizeGreaterThanOrEqualTo(2);
    }

    // ==================== MOCKITO MOCK TESTS ====================

    @Test
    public void testCreateInputFormatWithMockedPrototype() {
        when(mockPrototype.clone()).thenReturn(mockPrototype);

        List<InputFormat> formats = ImageFormatRegistry.createInputFormats(mockPrototype);

        assertThat(formats)
            .as("Should create input formats with mocked prototype")
            .isNotEmpty();
    }

    @Test
    public void testMockedProviderBehavior() {
        when(mockProvider.getFormatName()).thenReturn("TEST");
        when(mockProvider.getFileExtensions()).thenReturn(new String[]{"test"});
        when(mockProvider.getMimeTypes()).thenReturn(new String[]{"image/test"});
        when(mockProvider.getDescription()).thenReturn("Test Format");

        assertThat(mockProvider.getFormatName()).isEqualTo("TEST");
        assertThat(mockProvider.getFileExtensions()).containsExactly("test");

        verify(mockProvider).getFormatName();
        verify(mockProvider).getFileExtensions();
    }

    @Test
    public void testPngProviderCreatesValidInputFormat() {
        PngFormatProvider provider = new PngFormatProvider();
        ImageFigure prototype = new ImageFigure();

        InputFormat inputFormat = provider.createInputFormat(prototype);

        assertThat(inputFormat)
            .as("PNG provider should create valid input format")
            .isNotNull();
    }

    @Test
    public void testJpegProviderCreatesValidInputFormat() {
        JpegFormatProvider provider = new JpegFormatProvider();
        ImageFigure prototype = new ImageFigure();

        InputFormat inputFormat = provider.createInputFormat(prototype);

        assertThat(inputFormat)
            .as("JPEG provider should create valid input format")
            .isNotNull();
    }

    // ==================== PROVIDER SPECIFIC TESTS ====================

    @Test
    public void testPngProviderProperties() {
        PngFormatProvider provider = new PngFormatProvider();
        
        assertThat(provider.getFormatName()).isEqualTo("PNG");
        assertThat(provider.getFileExtensions()).containsExactly("png");
        assertThat(provider.getMimeTypes()).containsExactly("image/png");
        assertThat(provider.getDescription()).contains("PNG");
    }

    @Test
    public void testJpegProviderProperties() {
        JpegFormatProvider provider = new JpegFormatProvider();
        
        assertThat(provider.getFormatName()).isEqualTo("JPEG");
        assertThat(provider.getFileExtensions()).containsExactly("jpg", "jpeg");
        assertThat(provider.getDescription()).contains("JPEG");
    }

    @Test
    public void testPngProviderCreatesOutputFormat() {
        PngFormatProvider provider = new PngFormatProvider();

        OutputFormat outputFormat = provider.createOutputFormat();

        assertThat(outputFormat)
            .as("PNG provider should create output format")
            .isNotNull();
    }

    @Test
    public void testJpegProviderCreatesOutputFormat() {
        JpegFormatProvider provider = new JpegFormatProvider();

        OutputFormat outputFormat = provider.createOutputFormat();

        assertThat(outputFormat)
            .as("JPEG provider should create output format")
            .isNotNull();
    }

    // ==================== JAVA ASSERTIONS FOR INVARIANTS ====================

    @Test
    public void testProvidersListInvariant() {
        List<ImageFormatProvider> providers = ImageFormatRegistry.getProviders();

        assert providers != null : "Providers list should never be null";
        assert !providers.isEmpty() : "Providers list should never be empty after initialization";
    }

    @Test
    public void testInputFormatsInvariant() {
        ImageFigure prototype = new ImageFigure();
        List<InputFormat> formats = ImageFormatRegistry.createInputFormats(prototype);

        assert formats != null : "Input formats list should never be null";

        for (InputFormat format : formats) {
            assert format != null : "Individual input format should never be null";
        }
    }

    @Test
    public void testOutputFormatsInvariant() {
        List<OutputFormat> formats = ImageFormatRegistry.createOutputFormats();

        assert formats != null : "Output formats list should never be null";

        for (OutputFormat format : formats) {
            assert format != null : "Individual output format should never be null";
        }
    }

    @Test
    public void testProviderPropertiesInvariant() {
        for (ImageFormatProvider provider : ImageFormatRegistry.getProviders()) {
            assert provider.getFormatName() != null : "Format name should never be null";
            assert !provider.getFormatName().isEmpty() : "Format name should never be empty";
            assert provider.getFileExtensions() != null : "File extensions should never be null";
            assert provider.getFileExtensions().length > 0 : "File extensions should not be empty";
        }
    }
}
