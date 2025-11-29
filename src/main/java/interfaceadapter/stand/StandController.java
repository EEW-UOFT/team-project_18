package interfaceadapter.stand;

import data.Access.DeckApiInterface;
import entity.CurrentGame;
import entity.User;
import use.Case.stand.StandInputBoundary;
import use.Case.stand.StandInputData;

public class StandController {

    private final StandInputBoundary interactor;
    private final User user;

    public StandController(StandInputBoundary interactor, User user) {

        this.interactor = interactor;
        this.user = user;
    }

    // This will be called by the UI (e.g., when player presses STAND)
    public void execute(CurrentGame currentGame) {

        try {
            StandInputData inputData = new StandInputData(currentGame, user);
            interactor.execute(inputData);
        } catch (DeckApiInterface.UnableToLoadDeck e) {
            System.err.println("Stand failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
