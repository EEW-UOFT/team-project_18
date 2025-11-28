package interfaceadapter.startnewgame;

import entity.CurrentGame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StartNewGameViewModel {

    public static final String TITLE_LABEL = "HomePage";
    private CurrentGame currentGame;
    private String errorMessage;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public StartNewGameViewModel() {}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("currentGame", null, this.currentGame);
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CurrentGame currentGame) {
            CurrentGame oldValue = this.currentGame;
            this.currentGame = currentGame;
            support.firePropertyChange("currentGame", oldValue, currentGame);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
