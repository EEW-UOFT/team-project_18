package interfaceadapter;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for the View Manager. Its activeview is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel {

    private String activeView = "";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setActiveView(String activeView) {
        final String oldValue = this.activeView;
        this.activeView = activeView;
        support.firePropertyChange("view", oldValue, activeView);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
