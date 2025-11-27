package interfaceadapter.stand;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import use.Case.stand.StandInputBoundary;
import use.Case.stand.StandInputData;

public class StandController {

    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    // This will be called by the UI (e.g., when player presses STAND)
    public void onStand(Deck deck, int playerTotal) throws DeckApiInterface.UnableToLoadDeck {
        final StandInputData input = new StandInputData(deck, playerTotal);
        interactor.execute(input);
    }
}
