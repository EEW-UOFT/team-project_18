package interfaceadapter.stand;

import data.Access.DeckApiInterface;
import entity.CurrentGame;
import use.Case.stand.StandInputBoundary;

public class StandController {

    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    // This will be called by the UI (e.g., when player presses STAND)
    public void execute(CurrentGame currentGame) {
        try {
            interactor.execute(currentGame);
        } catch (DeckApiInterface.UnableToLoadDeck e) {
            System.err.println("Stand failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
