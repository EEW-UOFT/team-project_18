package interfaceadapter.restartgame;

import entity.CurrentGame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RestartGameViewModel {

    private CurrentGame currentGame;
    private String errorMessage;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setCurrentGame(CurrentGame currentGame) {

        CurrentGame oldValue = this.currentGame;
        this.currentGame = currentGame;
        support.firePropertyChange("currentGame", oldValue, currentGame);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("currentGame", null, this.currentGame);
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
