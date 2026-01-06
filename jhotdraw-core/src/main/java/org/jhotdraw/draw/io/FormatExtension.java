/*
 * @(#)FormatExtension.java
 *
 * Copyright (c) 2025 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.io;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class FormatExtension {

    private final String extension;

    /**
     * Creates a new format extension.
     *
     * @param extension the file extension (without leading dot)
     * @throws IllegalArgumentException if extension is null or empty
     */
    public FormatExtension(String extension) {
        if (extension == null || extension.trim().isEmpty()) {
            throw new IllegalArgumentException("Extension cannot be null or empty");
        }
        // Normalize to lowercase for consistent comparison
        this.extension = extension.trim().toLowerCase();
    }

    /**
     * Returns the normalized extension string.
     *
     * @return the extension in lowercase
     */
    public String getValue() {
        return extension;
    }

    /**
     * Checks if this extension matches the given string (case-insensitive).
     *
     * @param other the string to compare
     * @return true if they match
     */
    public boolean matches(String other) {
        if (other == null) {
            return false;
        }
        return extension.equalsIgnoreCase(other.trim());
    }

    /**
     * Creates a set of FormatExtension objects from an array of strings.
     *
     * @param extensions array of extension strings
     * @return immutable set of FormatExtension objects
     */
    public static Set<FormatExtension> createSet(String... extensions) {
        if (extensions == null || extensions.length == 0) {
            return Collections.emptySet();
        }
        Set<FormatExtension> set = new HashSet<>();
        for (String ext : extensions) {
            if (ext != null && !ext.trim().isEmpty()) {
                set.add(new FormatExtension(ext));
            }
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FormatExtension that = (FormatExtension) obj;
        return extension.equals(that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extension);
    }

    @Override
    public String toString() {
        return extension;
    }
}

