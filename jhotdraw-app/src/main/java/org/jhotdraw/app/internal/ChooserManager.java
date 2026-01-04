
package org.jhotdraw.app.internal;

import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.ApplicationModel;
import org.jhotdraw.api.app.View;
import org.jhotdraw.api.gui.URIChooser;
public class ChooserManager {

    private URIChooser openChooser;
    private URIChooser saveChooser;
    private URIChooser importChooser;
    private URIChooser exportChooser;

    private final Application application;
    private final ApplicationModel model;

    public ChooserManager(Application application, ApplicationModel model) {
        this.application = application;
        this.model = model;
    }

    /**
     * Gets an open chooser for the specified view or for the application.
     * Implements Lazy Initialization pattern.
     */
    public URIChooser getOpenChooser(View v) {
        if (v == null) {
            return getApplicationOpenChooser();
        } else {
            return getViewOpenChooser(v);
        }
    }

    /**
     * Gets a save chooser for the specified view or for the application.
     */
    public URIChooser getSaveChooser(View v) {
        if (v == null) {
            return getApplicationSaveChooser();
        } else {
            return getViewSaveChooser(v);
        }
    }

    /**
     * Gets an import chooser for the specified view or for the application.
     */
    public URIChooser getImportChooser(View v) {
        if (v == null) {
            return getApplicationImportChooser();
        } else {
            return getViewImportChooser(v);
        }
    }

    /**
     * Gets an export chooser for the specified view or for the application.
     */
    public URIChooser getExportChooser(View v) {
        if (v == null) {
            return getApplicationExportChooser();
        } else {
            return getViewExportChooser(v);
        }
    }

    // Application-level choosers (singletons)

    private URIChooser getApplicationOpenChooser() {
        if (openChooser == null) {
            openChooser = createAndConfigureChooser(() -> model.createOpenChooser(application, null));
        }
        return openChooser;
    }

    private URIChooser getApplicationSaveChooser() {
        if (saveChooser == null) {
            saveChooser = createAndConfigureChooser(() -> model.createSaveChooser(application, null));
        }
        return saveChooser;
    }

    private URIChooser getApplicationImportChooser() {
        if (importChooser == null) {
            importChooser = createAndConfigureChooser(() -> model.createImportChooser(application, null));
        }
        return importChooser;
    }

    private URIChooser getApplicationExportChooser() {
        if (exportChooser == null) {
            exportChooser = createAndConfigureChooser(() -> model.createExportChooser(application, null));
        }
        return exportChooser;
    }

    // View-specific choosers

    private URIChooser getViewOpenChooser(View v) {
        return getOrCreateViewChooser(v, "openChooser", () -> model.createOpenChooser(application, v));
    }

    private URIChooser getViewSaveChooser(View v) {
        return getOrCreateViewChooser(v, "saveChooser", () -> model.createSaveChooser(application, v));
    }

    private URIChooser getViewImportChooser(View v) {
        return getOrCreateViewChooser(v, "importChooser", () -> model.createImportChooser(application, v));
    }

    private URIChooser getViewExportChooser(View v) {
        return getOrCreateViewChooser(v, "exportChooser", () -> model.createExportChooser(application, v));
    }

    // Helper methods using Strategy pattern for chooser creation

    /**
     * Gets or creates a view-specific chooser using the provided factory.
     * Implements Template Method pattern for view chooser management.
     */
    private URIChooser getOrCreateViewChooser(View v, String clientPropertyKey, ChooserFactory factory) {
        URIChooser chooser = (URIChooser) v.getComponent().getClientProperty(clientPropertyKey);
        if (chooser == null) {
            chooser = factory.createChooser();
            v.getComponent().putClientProperty(clientPropertyKey, chooser);
            setupViewChooserProperties(chooser, v);
        }
        return chooser;
    }

    /**
     * Creates and configures an application-level chooser.
     */
    private URIChooser createAndConfigureChooser(ChooserFactory factory) {
        URIChooser chooser = factory.createChooser();
        chooser.getComponent().putClientProperty("application", application);
        return chooser;
    }

    /**
     * Sets up client properties for view-specific choosers.
     */
    private void setupViewChooserProperties(URIChooser chooser, View view) {
        chooser.getComponent().putClientProperty("view", view);
        chooser.getComponent().putClientProperty("application", application);
    }

    /**
     * Functional interface for chooser creation strategy.
     */
    @FunctionalInterface
    private interface ChooserFactory {
        URIChooser createChooser();
    }

    /**
     * Clears all cached choosers - useful for testing or configuration changes.
     */
    public void clearCachedChoosers() {
        openChooser = null;
        saveChooser = null;
        importChooser = null;
        exportChooser = null;
    }
}
