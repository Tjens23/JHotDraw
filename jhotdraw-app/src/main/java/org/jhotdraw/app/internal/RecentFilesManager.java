/*
 * @(#)RecentFilesManager.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.app.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

public class RecentFilesManager {

    private static final int MAX_RECENT_FILES_COUNT = 10;
    private final LinkedList<URI> recentURIs = new LinkedList<>();
    private final Preferences preferences;

    public RecentFilesManager(Preferences preferences) {
        this.preferences = preferences;
        loadRecentFiles();
    }

    /**
     * Loads recent files from preferences using Template Method pattern.
     */
    private void loadRecentFiles() {
        int count = preferences.getInt("recentFileCount", 0);
        for (int i = 0; i < count; i++) {
            String path = preferences.get("recentFile." + i, null);
            if (path != null) {
                try {
                    addRecentURIInternal(new URI(path));
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Saves recent files to preferences.
     */
    public void saveRecentFiles() {
        preferences.putInt("recentFileCount", recentURIs.size());
        for (int i = 0; i < recentURIs.size(); i++) {
            preferences.put("recentFile." + i, recentURIs.get(i).toString());
        }
    }

    /**
     * Adds a URI to the recent files list.
     * Implements the strategy of keeping most recent at the front.
     */
    public void addRecentURI(URI uri) {
        if (uri == null) {
            return;
        }

        // Remove if already exists to avoid duplicates
        recentURIs.remove(uri);

        // Add to front
        recentURIs.addFirst(uri);

        // Limit size
        while (recentURIs.size() > MAX_RECENT_FILES_COUNT) {
            recentURIs.removeLast();
        }

        saveRecentFiles();
    }

    /**
     * Internal method for adding without saving (used during loading).
     */
    private void addRecentURIInternal(URI uri) {
        if (uri != null && !recentURIs.contains(uri)) {
            recentURIs.add(uri);
        }
    }

    /**
     * Gets the list of recent URIs.
     * Returns a copy to prevent external modification.
     */
    public List<URI> getRecentURIs() {
        return new LinkedList<>(recentURIs);
    }

    /**
     * Clears all recent files.
     */
    public void clearRecentFiles() {
        recentURIs.clear();
        preferences.putInt("recentFileCount", 0);
        // Remove all stored recent file entries
        for (int i = 0; i < MAX_RECENT_FILES_COUNT; i++) {
            preferences.remove("recentFile." + i);
        }
    }

    /**
     * Removes a specific URI from the recent files list.
     */
    public boolean removeRecentURI(URI uri) {
        boolean removed = recentURIs.remove(uri);
        if (removed) {
            saveRecentFiles();
        }
        return removed;
    }

    /**
     * Gets the most recent URI, or null if none exists.
     */
    public URI getMostRecentURI() {
        return recentURIs.isEmpty() ? null : recentURIs.getFirst();
    }

    /**
     * Checks if the recent files list is empty.
     */
    public boolean isEmpty() {
        return recentURIs.isEmpty();
    }

    /**
     * Gets the maximum number of recent files that can be stored.
     */
    public int getMaxRecentFilesCount() {
        return MAX_RECENT_FILES_COUNT;
    }

    /**
     * Gets the current number of recent files.
     */
    public int getRecentFilesCount() {
        return recentURIs.size();
    }
}
