package interface_adapter;


import use_case.stand.StandOutputBoundary;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {

    private String activeView = "";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewManagerModel() {}

    public String getActiveView() { return activeView;}

    public void setActiveView(String activeView) {
        String oldValue = this.activeView;
        this.activeView = activeView;
        System.out.println("Setting active view to: " + activeView);
        System.out.println("Old active view was: " + oldValue);
        support.firePropertyChange("view", oldValue, activeView);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
