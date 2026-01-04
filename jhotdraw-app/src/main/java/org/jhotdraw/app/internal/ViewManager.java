package org.jhotdraw.app.internal;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.ApplicationModel;
import org.jhotdraw.api.app.View;

public class ViewManager {

    public static final String ACTIVE_VIEW_PROPERTY = "activeView";
    public static final String VIEW_COUNT_PROPERTY = "viewCount";

    private final LinkedList<View> views = new LinkedList<>();
    private Collection<View> unmodifiableViews;
    private View activeView;
    private final PropertyChangeSupport propertyChangeSupport;
    private final Application application;
    private final ApplicationModel model;

    public ViewManager(Application application, ApplicationModel model) {
        this.application = application;
        this.model = model;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Sets the active view. Calls deactivate on the previously
     * active view, and then calls activate on the given view.
     *
     * @param newValue Active view, can be null.
     */
    public void setActiveView(View newValue) {
        View oldValue = activeView;
        if (activeView != null) {
            activeView.deactivate();
        }
        activeView = newValue;
        if (activeView != null) {
            activeView.activate();
        }
        firePropertyChange(ACTIVE_VIEW_PROPERTY, oldValue, newValue);
    }

    /**
     * Gets the active view.
     *
     * @return The active view can be null.
     */
    public View getActiveView() {
        return activeView;
    }

    /**
     * Adds a view to the application.
     * Follows the Single Responsibility Principle by focusing only on view management.
     */
    public void add(View v) {
        if (v.getApplication() != application) {
            int oldCount = views.size();
            views.add(v);
            v.setApplication(application);
            v.init();
            model.initView(application, v);
            firePropertyChange(VIEW_COUNT_PROPERTY, oldCount, views.size());
        }
    }

    /**
     * Removes a view from the application.
     */
    public void remove(View v) {
        if (v == getActiveView()) {
            setActiveView(null);
        }
        int oldCount = views.size();
        views.remove(v);
        v.setApplication(null);
        firePropertyChange(VIEW_COUNT_PROPERTY, oldCount, views.size());
    }

    /**
     * Gets an unmodifiable list of all views.
     */
    public List<View> getViews() {
        return Collections.unmodifiableList(views);
    }

    /**
     * Gets an unmodifiable collection of all views.
     * Uses lazy initialization pattern.
     */
    public Collection<View> views() {
        if (unmodifiableViews == null) {
            unmodifiableViews = Collections.unmodifiableCollection(views);
        }
        return unmodifiableViews;
    }

    /**
     * Gets the count of currently managed views.
     */
    public int getViewCount() {
        return views.size();
    }

    /**
     * Checks if a view is currently managed by this manager.
     */
    public boolean containsView(View view) {
        return views.contains(view);
    }

    /**
     * Clears all views - used during application shutdown.
     */
    public void clearAllViews() {
        // Create a copy to avoid concurrent modification
        List<View> viewsCopy = new LinkedList<>(views);
        for (View view : viewsCopy) {
            remove(view);
        }
        setActiveView(null);
    }

    // Property change support methods
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
