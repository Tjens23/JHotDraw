package org.jhotdraw.draw.io;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class FormatExtension {

    private final String extension;

    public FormatExtension(String extension) {
        if (extension == null || extension.trim().isEmpty()) {
            throw new IllegalArgumentException("Extension cannot be null or empty");
        }
        this.extension = extension.trim().toLowerCase();
    }

    public String getValue() {
        return extension;
    }

    public boolean matches(String other) {
        if (other == null) {
            return false;
        }
        return extension.equalsIgnoreCase(other.trim());
    }

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

