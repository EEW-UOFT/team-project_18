package interface_adapter;

import use_case.StandInputBoundary;

public class StandController {
    private final StandInputBoundary interactor;

    public StandController(StandInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onStand(String deckId) {
        interactor.execute(deckId);
    }
}

