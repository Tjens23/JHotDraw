package org.jhotdraw.app.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.ApplicationModel;
import org.jhotdraw.api.app.View;
import org.junit.*;
import static org.assertj.core.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class ViewManagerTest {

    @Mock
    private Application mockApplication;

    @Mock
    private ApplicationModel mockModel;

    @Mock
    private View mockView1;

    @Mock
    private View mockView2;

    @Mock
    private PropertyChangeListener mockPropertyListener;

    private ViewManager viewManager;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewManager = new ViewManager(mockApplication, mockModel);

        // Setup mock behavior
        when(mockView1.getApplication()).thenReturn(null).thenReturn(mockApplication);
        when(mockView2.getApplication()).thenReturn(null).thenReturn(mockApplication);
    }

    @After
    public void tearDown() {
        viewManager = null;
    }

    // BASIC FUNCTIONALITY TESTS

    /**
     * Test initial state of ViewManager.
     */
    @Test
    public void testInitialState() {
        assertThat(viewManager.getViewCount()).isZero();
        assertThat(viewManager.getActiveView()).isNull();
        assertThat(viewManager.getViews()).isEmpty();
        assertThat(viewManager.views()).isEmpty();
    }

    /**
     * Test adding a single view.
     */
    @Test
    public void testAddView() {
        viewManager.addPropertyChangeListener(ViewManager.VIEW_COUNT_PROPERTY, mockPropertyListener);

        viewManager.add(mockView1);

        assertThat(viewManager.getViewCount()).isEqualTo(1);
        assertThat(viewManager.getViews()).containsExactly(mockView1);
        assertThat(viewManager.containsView(mockView1)).isTrue();

        // Verify proper initialization sequence
        verify(mockView1).setApplication(mockApplication);
        verify(mockView1).init();
        verify(mockModel).initView(mockApplication, mockView1);

        // Verify property change event
        verify(mockPropertyListener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test adding multiple views.
     */
    @Test
    public void testAddMultipleViews() {
        viewManager.add(mockView1);
        viewManager.add(mockView2);

        assertThat(viewManager.getViewCount()).isEqualTo(2);
        assertThat(viewManager.getViews()).containsExactly(mockView1, mockView2);
    }

    /**
     * Test removing a view.
     */
    @Test
    public void testRemoveView() {
        viewManager.add(mockView1);
        viewManager.addPropertyChangeListener(ViewManager.VIEW_COUNT_PROPERTY, mockPropertyListener);

        viewManager.remove(mockView1);

        assertThat(viewManager.getViewCount()).isZero();
        assertThat(viewManager.containsView(mockView1)).isFalse();

        verify(mockView1).setApplication(null);
        verify(mockPropertyListener).propertyChange(any(PropertyChangeEvent.class));
    }

    // ACTIVE VIEW TESTS

    /**
     * Test setting and getting active view.
     */
    @Test
    public void testSetActiveView() {
        viewManager.addPropertyChangeListener(ViewManager.ACTIVE_VIEW_PROPERTY, mockPropertyListener);

        viewManager.setActiveView(mockView1);

        assertThat(viewManager.getActiveView()).isEqualTo(mockView1);
        verify(mockView1).activate();
        verify(mockPropertyListener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test changing active view deactivates previous view.
     */
    @Test
    public void testChangeActiveViewDeactivatesPrevious() {
        viewManager.setActiveView(mockView1);

        viewManager.setActiveView(mockView2);

        assertThat(viewManager.getActiveView()).isEqualTo(mockView2);
        verify(mockView1).deactivate();
        verify(mockView2).activate();
    }

    /**
     * Test removing active view sets active view to null.
     */
    @Test
    public void testRemoveActiveViewSetsActiveToNull() {
        viewManager.add(mockView1);
        viewManager.setActiveView(mockView1);

        viewManager.remove(mockView1);

        assertThat(viewManager.getActiveView()).isNull();
    }

    /**
     * Test setting active view to null deactivates current view.
     */
    @Test
    public void testSetActiveViewToNull() {
        viewManager.setActiveView(mockView1);

        viewManager.setActiveView(null);

        assertThat(viewManager.getActiveView()).isNull();
        verify(mockView1).deactivate();
    }

    // EDGE CASES AND ERROR CONDITIONS

    /**
     * Test adding same view twice does nothing.
     */
    @Test
    public void testAddSameViewTwiceDoesNothing() {
        when(mockView1.getApplication()).thenReturn(mockApplication);

        viewManager.add(mockView1);
        viewManager.add(mockView1); // Second add should do nothing

        assertThat(viewManager.getViewCount()).isEqualTo(1);
        verify(mockView1, times(1)).setApplication(mockApplication);
        verify(mockView1, times(1)).init();
    }

    /**
     * Test removing view that is not managed.
     */
    @Test
    public void testRemoveUnmanagedView() {
        int initialCount = viewManager.getViewCount();

        viewManager.remove(mockView1);

        assertThat(viewManager.getViewCount()).isEqualTo(initialCount);
    }

    /**
     * Test clearing all views.
     */
    @Test
    public void testClearAllViews() {
        viewManager.add(mockView1);
        viewManager.add(mockView2);
        viewManager.setActiveView(mockView1);

        viewManager.clearAllViews();

        assertThat(viewManager.getViewCount()).isZero();
        assertThat(viewManager.getActiveView()).isNull();
        verify(mockView1).setApplication(null);
        verify(mockView2).setApplication(null);
    }

    // COLLECTION IMMUTABILITY TESTS

    /**
     * Test that getViews returns immutable list.
     */
    @Test
    public void testGetViewsReturnsImmutableList() {
        viewManager.add(mockView1);
        List<View> views = viewManager.getViews();

        assertThatThrownBy(() -> views.add(mockView2))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    /**
     * Test that views() returns immutable collection.
     */
    @Test
    public void testViewsReturnsImmutableCollection() {
        viewManager.add(mockView1);
        Collection<View> views = viewManager.views();

        assertThatThrownBy(() -> views.add(mockView2))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    // PROPERTY CHANGE TESTS

    /**
     * Test property change listener registration and removal.
     */
    @Test
    public void testPropertyChangeListenerManagement() {
        viewManager.addPropertyChangeListener(mockPropertyListener);
        viewManager.removePropertyChangeListener(mockPropertyListener);

        viewManager.setActiveView(mockView1);

        // Should not receive events after removal
        verifyNoInteractions(mockPropertyListener);
    }

    /**
     * Test property-specific listener registration.
     */
    @Test
    public void testPropertySpecificListener() {
        viewManager.addPropertyChangeListener(ViewManager.ACTIVE_VIEW_PROPERTY, mockPropertyListener);

        viewManager.add(mockView1); // Should not trigger listener
        viewManager.setActiveView(mockView1); // Should trigger listener

        verify(mockPropertyListener, times(1)).propertyChange(any(PropertyChangeEvent.class));
    }

    // JAVA ASSERTIONS FOR INVARIANTS

    /**
     * Test invariant: active view must be in the views collection or null.
     */
    @Test
    public void testActiveViewInvariant() {
        viewManager.add(mockView1);
        viewManager.setActiveView(mockView1);

        assert viewManager.getActiveView() == null || viewManager.containsView(viewManager.getActiveView())
                : "Active view must be in views collection or null";

        viewManager.remove(mockView1);
        assert viewManager.getActiveView() == null
                : "Active view should be null after removing it from collection";
    }

    /**
     * Test invariant: view count should match actual collection size.
     */
    @Test
    public void testViewCountInvariant() {
        assert viewManager.getViewCount() == viewManager.getViews().size()
                : "View count should match collection size";

        viewManager.add(mockView1);
        assert viewManager.getViewCount() == viewManager.getViews().size()
                : "View count should match collection size after add";

        viewManager.remove(mockView1);
        assert viewManager.getViewCount() == viewManager.getViews().size()
                : "View count should match collection size after remove";
    }
}
