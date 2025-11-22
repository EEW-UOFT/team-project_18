package interface_adapter;

import data_access.Deck;
import data_access.DeckAPIInterface;
import use_case.StandInputBoundary;
import use_case.StandInputData;

public class StandController {

    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    // This will be called by the UI (e.g., when player presses STAND)
    public void onStand(Deck deck, int playerTotal) throws DeckAPIInterface.UnableToLoadDeck {
        StandInputData input = new StandInputData(deck, playerTotal);
        interactor.execute(input);
    }
}
