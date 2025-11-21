package interface_adapter;

import entity.Deck;
import use_case.StandInputBoundary;
import use_case.StandInputData;

public class StandController {

    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    // This will be called by the UI (e.g., when player presses STAND)
    public void onStand(Deck deck, int playerTotal) {
        StandInputData input = new StandInputData(deck, playerTotal);
        interactor.execute(input);
    }
}
