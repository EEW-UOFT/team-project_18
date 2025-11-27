package use.Case.restartgame;

import data.Access.DeckApiInterface;
import entity.CurrentGame;
import entity.User;

public class RestartGameInteractor implements RestartGameInputBoundary {

    private CurrentGame currentGame;
    private final RestartGameOutputBoundary presenter;

    public RestartGameInteractor(RestartGameOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(User currentUser) {
        try {
            currentGame = new CurrentGame(currentUser);

            currentGame.addCardPlayer(2);
            currentGame.addCardDealer(2, false);

            RestartGameOutputData outputData = new RestartGameOutputData(currentGame);
            presenter.prepareSuccessView(outputData);

        } catch (DeckApiInterface.UnableToLoadDeck e) {
            presenter.prepareFailView("Unable to fetch new deck. Try again later.");
        }
    }
}
