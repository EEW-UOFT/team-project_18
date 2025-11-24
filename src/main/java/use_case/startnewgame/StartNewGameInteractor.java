package use_case.startnewgame;

import data_access.DeckAPIInterface;
import entity.CurrentGame;
import entity.User;

public class StartNewGameInteractor implements StartNewGameInputBoundary {

    private CurrentGame currentGame;
    private StartNewGameOutputBoundary startNewGamePresenter;

    public StartNewGameInteractor(StartNewGameOutputBoundary StartNewGamePresenter) {
        this.startNewGamePresenter = StartNewGamePresenter;
    }

    @Override
    public void execute(User currentUser) {
        try {
            currentGame = new CurrentGame(currentUser);

            currentGame.addCardPlayer(2);

            currentGame.addCardDealer(2, false);

            final StartNewGameOutputData startNewGameOutputData = new StartNewGameOutputData(currentGame);
            this.startNewGamePresenter.prepareSuccessView(startNewGameOutputData);

        } catch (DeckAPIInterface.UnableToLoadDeck e) {
            this.startNewGamePresenter.prepareFailView("Failed to Start New Game, Please Try Again");
        }
    }
}
