package interface_adapter.Stand;

import data_access.DeckApiInterface;
import use_case.Stand.StandInputBoundary;
import use_case.Stand.StandInputData;

public class StandController {

    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    // This will be called by the UI (when player presses STAND)
    public void onStand(DeckApiInterface deck, int playerTotal) {
        StandInputData input = new StandInputData(deck, playerTotal);
        interactor.execute(input);
    }
}
